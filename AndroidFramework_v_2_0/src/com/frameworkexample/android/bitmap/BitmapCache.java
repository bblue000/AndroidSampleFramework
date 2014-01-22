package com.frameworkexample.android.bitmap;

import com.frameworkexample.android.utils.MD5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

public class BitmapCache {
	
	private static final IBitmapDownloader mDefBitmapDownloader = null;
	
	// 应用中使用的Bitmap配置
	private static final Config mDefConfig = Config.RGB_565;
	// 加载图片时，提供的默认条件
	private static final Options newDefaultLoadOptions() {
		Options op = new Options();
		op.inPreferredConfig = mDefConfig;
		op.inPurgeable = true;
		op.inInputShareable = true;
		return op;
	}
	// 转换url为物理存储时相应的文件名
	private static final String transferFileName(String url) {
		return MD5.digest2Str(url);
	}
	
//	public static Bitmap fromResource(Context context, int res) {
//	}
//	
//	public static Bitmap fromAsset(Context context, int res) {
//		
//	}
//	
//	public static Bitmap fromUrl(Context context, String url) {
//		
//	}
}
