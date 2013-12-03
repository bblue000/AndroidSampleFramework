package org.ixming.network;

import org.apache.http.client.HttpClient;

/**
 * 配置HttpClient中间的内容，并提供该HttpClient对象。
 * @author Yin Yong
 * @version 1.0
 */
public interface IHttpClientFactory {

	HttpClient newHttpClient();
	
}
