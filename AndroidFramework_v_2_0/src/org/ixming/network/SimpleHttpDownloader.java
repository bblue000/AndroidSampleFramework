package org.ixming.network;

import java.io.File;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.ixming.io.IOUtils;

public class SimpleHttpDownloader  {

	private SimpleHttpDownloader() { }
	
	public static boolean download(HttpClient httpClient, String url, File savePath) throws Exception {
		HttpGet request = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		InputStream in = null;
		try {
			request = new HttpGet(url);
			response = httpClient.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if (null != statusLine) {
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					entity = response.getEntity();
					in = entity.getContent();
					return IOUtils.inputStreamToFile(in, savePath);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception ignore) { }
			try {
				entity.consumeContent();
			} catch (Exception ignore) { }
			try {
				request.abort();
			} catch (Exception ignore) { }
		}
		return false;
	}

}
