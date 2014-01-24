package com.frameworkexample.android.bitmap;

import com.frameworkexample.android.utils.LogUtils;

import android.graphics.Bitmap;

public class SimpleBitmapScaler {

	private static final String TAG = SimpleBitmapScaler.class.getSimpleName();
	
	private SimpleBitmapScaler() { }
	
	static ScaleMode checkModeValue(ScaleMode mode) {
		if (null == mode) {
			return ScaleMode.AlwaysIfDiffer;
		}
		return mode;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//TODO 如果已经存在了原Bitmap
	/**
	 * 缩放src到指定大小（内存大小）
	 * @param src 原Bitmap
	 * @param size 指定大小
	 * @param recycleSrc 是否需要回收原Bitmap
	 * @return 处理后的Bitmap
	 */
	public static Bitmap scaleBitmapToMemSize(Bitmap src, long size,
			ScaleMode mode, boolean recycleSrc) {
		try {
			float bmSize = BitmapUtils.calculateBitmapMemSize(src);
			mode = checkModeValue(mode);
			switch (mode) {
			case AlwaysIfDiffer:
				if (bmSize == size) {
					return src;
				}
				break;
			case OnlyIfLarger:
				if (bmSize <= size) {
					return src;
				}
				break;
			case OnlyIfSmaller:
				if (bmSize >= size) {
					return src;
				}
				break;
			}
			float scale = (float) Math.sqrt(size / bmSize);
			return scaleBitmapToSize(src, scale, recycleSrc);
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToMemSize Exception: " + e.getMessage());
			return src;
		}
	}
	
	/**
	 * 缩放src到指定大小（宽高）
	 * @param src 原Bitmap
	 * @param scale 指定宽高缩放比率
	 * @param height 指定高
	 * @param recycleSrc 是否需要回收原Bitmap
	 * @return 处理后的Bitmap
	 */
	public static Bitmap scaleBitmapToSize(Bitmap src, float scale, boolean recycleSrc) {
		try {
			float width = src.getWidth();
			float height = src.getHeight();
			int scaledWidth = (int) (width * scale + 0.5F);
			int scaledHeight = (int) (height * scale + 0.5F);
			return scaleBitmapToSize(src, scaledWidth, scaledHeight, recycleSrc);
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize<size> Exception: " + e.getMessage());
			return src;
		}
	}
	
	/**
	 * 缩放src到指定大小（宽高）
	 * @param bitmap 
	 * @param width 指定宽
	 * @param height 指定高
	 * @param recycleSrc 是否需要回收原Bitmap
	 * @return 处理后的Bitmap
	 */
	public static Bitmap scaleBitmapToSize(Bitmap src, int width, int height, boolean recycleSrc) {
		if (null == src) {
			return src;
		}
		if (width <= 0 || height <= 0) {
			return null;
		}
		try {
			Bitmap target = Bitmap.createScaledBitmap(src, width, height, true);
			// 当宽高和原先一致时，会直接返回当前src，此时，不应当recycle
			if (src != target && recycleSrc) {
				src.recycle();
			}
			return target;
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize<w, h> Exception: " + e.getMessage());
			return src;
		}
	}
	
	public static byte[] scaleBitmapIfNeededToMemSize(Bitmap src, long size, boolean needRecycle) {
		try {
			Bitmap resizedSrc = scaleBitmapToMemSize(src, size, needRecycle);
			return BitmapUtils.bmpToByteArray(resizedSrc, (resizedSrc != src));
		} catch (Throwable e) {
			LogUtils.e(TAG, "scaleBitmapToSize<size> Exception: " + e.getMessage());
			return (byte[]) null;
		}
	}
	
	public static byte[] scaleBitmapIfNeededToSize(Bitmap src, int width, int height, boolean needRecycle) {
		Bitmap resizedSrc = scaleBitmapToSize(src, width, height, needRecycle);
		return BitmapUtils.bmpToByteArray(resizedSrc, (resizedSrc != src));
	}
	
}
