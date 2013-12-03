package org.ixming.android.network.internal;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.ixming.android.utils.FrameworkLog;
import org.ixming.network.IHttpClientFactory;

/**
 * 创建一个具有如下性质的HttpClient：
 * <p>
 * <ul>
 * <li>支持协议：HTTP,HTTPS；</li>
 * <li>HTTPS使用的是相对简单的SSL加密处理机制；</li>
 * <li>HTTP 1.1版本；</li>
 * <li>字符编码为UTF-8；</li>
 * <li>连接超时和Socket超时时间为20秒；</li>
 * </ul>
 * </p>
 * @author Yin Yong
 * @version 1.0
 */
public class DefaultHttpClientFactory implements IHttpClientFactory {

	final String TAG = DefaultHttpClientFactory.class.getSimpleName();
	
	final int TIMEOUT = 20 * 1000;

	@Override
	public HttpClient newHttpClient() {
		HttpClient httpClient = null;
		try {
			SchemeRegistry registry = new SchemeRegistry();
			// register HTTP scheme
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			
			// register HTTPS scheme
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new DefaultSSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registry.register(new Scheme("https", sf, 443));
			
			// set HTTP protocol parameters
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, TIMEOUT);
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			httpClient = new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			FrameworkLog.e(TAG, "newHttpClient Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return httpClient;
	}

}
