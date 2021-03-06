package com.frameworkexample.android.common;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * 这个CommonTitleBarController是Android应用中较为普遍使用的，
 * 顶部分为三栏，三栏都是只有文本或者图片，或者文本加图片。
 * <p/>
 * 如果顶部的操作过于复杂多样，可以考虑使用Android的ActionBar。
 * @author Yin Yong
 */
public abstract class TraditionalTitleBarController extends CommonTitleBarController {

	// 第一种是标题居中的形式，两边各有一个空位
	//TODO 只设置文本
	/**
	 * 只设置文本
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param text 提供设置的文本
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setText(Position pos, String text);
	/**
	 * 只设置文本
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 提供设置的文本的resId
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setText(Position pos, int textRes);
	
	//TODO 只设置Icon
	/**
	 * 只设置Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param drawable 提供设置的图片
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setIcon(Position pos, Drawable drawable);
	/**
	 * 只设置Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param imageRes 提供设置的图片的resId
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setIcon(Position pos, int imageRes);
	
	//TODO 设置文本和Icon
	/**
	 * 设置文本和Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param text 指定文本
	 * @param left 提供设置的图片
	 * @param top 提供设置的图片
	 * @param right 提供设置的图片
	 * @param bottom 提供设置的图片
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndIcon(Position pos, String text,
			Drawable left, Drawable top, Drawable right, Drawable bottom);
	/**
	 * 设置文本和Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 指定文本的resId
	 * @param left 提供设置的图片
	 * @param top 提供设置的图片
	 * @param right 提供设置的图片
	 * @param bottom 提供设置的图片
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndIcon(Position pos, int textRes,
			Drawable left, Drawable top, Drawable right, Drawable bottom);
	/**
	 * 设置文本和Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param text 指定文本
	 * @param left 提供设置的图片的resId
	 * @param top 提供设置的图片的resId
	 * @param right 提供设置的图片的resId
	 * @param bottom 提供设置的图片的resId
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndIcon(Position pos, String text,
			int left, int top, int right, int bottom);
	/**
	 * 设置文本和Icon
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 指定文本的resId
	 * @param left 提供设置的图片的resId
	 * @param top 提供设置的图片的resId
	 * @param right 提供设置的图片的resId
	 * @param bottom 提供设置的图片的resId
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndIcon(Position pos, int textRes,
			int left, int top, int right, int bottom);
	
	/**
	 * 设置文本和背景
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param text 提供设置的文本
	 * @param bg 提供设置的背景
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndBackground(Position pos, String text, int bg);
	/**
	 * 设置文本和背景
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 提供设置的文本
	 * @param bg 提供设置的文本
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndBackground(Position pos, String text, Drawable bg);
	
	/**
	 * 设置文本和背景
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 提供设置的文本
	 * @param bg 提供设置的背景
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndBackground(Position pos, int textRes, int bg);
	/**
	 * 设置文本和背景
	 * @param pos 为哪个位置设置，可以使用{@link Position} 指定
	 * @param textRes 提供设置的文本
	 * @param bg 提供设置的文本
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController setTextAndBackground(Position pos, int textRes, Drawable bg);
	
	//TODO 获取View
	/**
	 * 根据Position指定的位置获取相应的TextView
	 * @param pos 指定位置
	 * @return 获取的View
	 */
	public abstract TextView getView(Position pos);
	
	// show or hide
	/**
	 * 显示相应位置的View
	 * @param pos 指定位置
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController showView(Position pos);
	
	/**
	 * 隐藏相应位置的View，等同于调用hideView(Position, true)
	 * @param pos 指定位置
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController hideView(Position pos);
	/**
	 * 隐藏相应位置的View
	 * @param pos 指定位置
	 * @param removeContent 是否要清除掉指定View上现在显示的信息
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController hideView(Position pos, boolean removeContent);
	
	/**
	 * 隐藏所有，等同于调用hideAll(true)
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController hideAll();
	/**
	 * 隐藏所有
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController hideAll(boolean removeContent);
	
	//TODO click listener
	/**
	 * 给相应位置的控件绑定监听器
	 * @param listener 监听器
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController bindClickListener(Position pos, View.OnClickListener listener);
	
	/**
	 * 移除监听器
	 * @return 方便链式处理，返回当前对象
	 */
	public abstract TraditionalTitleBarController removeClickListener(Position pos);
}
