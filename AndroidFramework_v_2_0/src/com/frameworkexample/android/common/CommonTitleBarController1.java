package com.frameworkexample.android.common;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

public abstract class CommonTitleBarController1 implements CommonTitleBarContainer{

//	public static CommonTitleBarController1 from(View wrappedView) {
//		return new CommonTitleBarControllerImpl1(wrappedView);
//	}
//	
//	/**
//	 * title bar中能够指定的View
//	 * @author Yin Yong
//	 */
//	public static enum Position {
//		Center,
//		Left,
//		Right
//	}
//	
//	// 第一种是标题居中的形式，两边各有一个空位
//	//TODO 只设置文本
//	/**
//	 * 只设置文本
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param text 提供设置的文本
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setText(Position pos, String text);
//	/**
//	 * 只设置文本
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param textRes 提供设置的文本的resId
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setText(Position pos, int textRes);
//	
//	//TODO 只设置Icon
//	/**
//	 * 只设置Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param drawable 提供设置的图片
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setIcon(Position pos, Drawable drawable);
//	/**
//	 * 只设置Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param imageRes 提供设置的图片的resId
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setIcon(Position pos, int imageRes);
//	
//	//TODO 设置文本和Icon
//	/**
//	 * 设置文本和Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param text 指定文本
//	 * @param left 提供设置的图片
//	 * @param top 提供设置的图片
//	 * @param right 提供设置的图片
//	 * @param bottom 提供设置的图片
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setTextAndIcon(Position pos, String text,
//			Drawable left, Drawable top, Drawable right, Drawable bottom);
//	/**
//	 * 设置文本和Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param textRes 指定文本的resId
//	 * @param left 提供设置的图片
//	 * @param top 提供设置的图片
//	 * @param right 提供设置的图片
//	 * @param bottom 提供设置的图片
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setTextAndIcon(Position pos, int textRes,
//			Drawable left, Drawable top, Drawable right, Drawable bottom);
//	/**
//	 * 设置文本和Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param text 指定文本
//	 * @param left 提供设置的图片的resId
//	 * @param top 提供设置的图片的resId
//	 * @param right 提供设置的图片的resId
//	 * @param bottom 提供设置的图片的resId
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setTextAndIcon(Position pos, String text,
//			int left, int top, int right, int bottom);
//	/**
//	 * 设置文本和Icon
//	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
//	 * @param textRes 指定文本的resId
//	 * @param left 提供设置的图片的resId
//	 * @param top 提供设置的图片的resId
//	 * @param right 提供设置的图片的resId
//	 * @param bottom 提供设置的图片的resId
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 setTextAndIcon(Position pos, int textRes,
//			int left, int top, int right, int bottom);
//	
//	//TODO 获取View
//	/**
//	 * 根据Position指定的位置获取相应的TextView
//	 * @param pos 指定位置
//	 * @return 获取的View
//	 */
//	public abstract TextView getView(Position pos);
//	
//	// show or hide
//	/**
//	 * 显示相应位置的View
//	 * @param pos 指定位置
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 showView(Position pos);
//	/**
//	 * 隐藏相应位置的View
//	 * @param pos 指定位置
//	 * @param removeContent 是否要清除掉指定View上现在显示的信息
//	 * @return 方便链式处理，返回当前对象
//	 */
//	public abstract CommonTitleBarController1 hideView(Position pos, boolean removeContent);
}
