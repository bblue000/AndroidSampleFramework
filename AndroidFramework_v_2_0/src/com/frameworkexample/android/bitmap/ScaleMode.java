package com.frameworkexample.android.bitmap;

public enum ScaleMode {

	/**
	 * 如果跟指定的大小不等，就需要缩放
	 */
	AlwaysIfDiffer,
	
	/**
	 * 如果比指定的大小大，就需要缩放至指定大小
	 */
	OnlyIfLarger,
	
	/**
	 * 如果比指定的大小小，就需要缩放至指定大小
	 */
	OnlyIfSmaller;
	
}
