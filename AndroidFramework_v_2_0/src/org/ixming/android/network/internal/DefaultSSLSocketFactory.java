package org.ixming.android.network.internal;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.ixming.android.utils.FrameworkLog;

/**
 * 默认的、通用的SSLSocketFactory，用于网络请求中参数设置。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public class DefaultSSLSocketFactory extends SSLSocketFactory {
	final String TAG = DefaultSSLSocketFactory.class.getSimpleName();
	SSLContext sslContext = null;

	public DefaultSSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		sslContext = SSLContext.getInstance("TLS");
		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {

			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {

			}

			public X509Certificate[] getAcceptedIssuers() {

				return null;

			}
		};
		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		FrameworkLog.w(TAG, "socket--->" + socket);
		FrameworkLog.w(TAG, "host--->" + host);
		FrameworkLog.w(TAG, "port--->" + port);
		FrameworkLog.w(TAG, "autoClose--->" + autoClose);
		if (port == -1) {
			port = 443;
		}
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}
