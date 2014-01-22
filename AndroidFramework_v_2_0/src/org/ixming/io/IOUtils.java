package org.ixming.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.ixming.android.utils.FrameworkLog;

public class IOUtils {

	private final static String TAG = IOUtils.class.getSimpleName();
	
	private IOUtils() { }
	
	public static boolean inputStreamToFile(InputStream in, File file) throws Exception {
		return inputStreamToFile(in, file, false);
	}
	
	public static boolean inputStreamAppendToFile(InputStream in, File file) throws Exception {
		return inputStreamToFile(in, file, true);
	}
	
	private static boolean inputStreamToFile(InputStream in, File file, boolean append) throws Exception {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == file) {
			throw new NullPointerException("file is null!");
		}
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IllegalArgumentException("the target file exits, and it is a folder!");
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, append);
			byte[] buf = new byte[512];
			int len = -1;
			while (-1 != (len = in.read(buf))) {
				out.write(buf, 0, len);
			}
			return true;
		} catch (Exception e) {
			FrameworkLog.e(TAG, "inputStreamToFile Exception : " + e.getMessage());
			throw e;
		} finally {
			try {
				out.close();
			} catch (Exception ignore) { }
			try {
				in.close();
			} catch (Exception ignore) { }
		}
	}
	
	public static String inputStreamToString(InputStream in) throws Exception {
		return inputStreamToString(in, false);
	}
	
	public static String inputStreamToString(InputStream in, boolean remainLine) throws Exception {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		BufferedReader reader = null;
		try {
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			while (null != (buf = reader.readLine())) {
				sb.append(buf);
				if (remainLine) {
					sb.append("\n");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "inputStreamToString Exception : " + e.getMessage());
			throw e;
		} finally {
			try {
				reader.close();
			} catch (Exception ignore) { }
			try {
				in.close();
			} catch (Exception ignore) { }
		}
	}
	
}
