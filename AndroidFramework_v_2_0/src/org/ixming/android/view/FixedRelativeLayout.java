package org.ixming.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 可以设置RelativeLayout的最大宽度和高度值。
 * <p>在xml配置文件中，直接设置android:maxWidth和android:maxHeight属性即可。</p>
 * @author Yin Yong
 * @version 1.0
 */
public class FixedRelativeLayout extends RelativeLayout {
	
	private final int MAX_UNDEFINED = Integer.MIN_VALUE;
	private int mMaxWidth = MAX_UNDEFINED;
	private int mMaxHeight = MAX_UNDEFINED;
	public FixedRelativeLayout(Context context) {
		super(context);
	}

	public FixedRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public FixedRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				new int[] { android.R.attr.maxWidth, android.R.attr.maxHeight });
		int maxWidth = a.getDimensionPixelOffset(0, -1);
		if (maxWidth > 0) {
			mMaxWidth = maxWidth;
		}
		int maxHeight = a.getDimensionPixelOffset(1, -1);
		if (maxHeight > 0) {
			mMaxHeight = maxHeight;
		}
		a.recycle();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		if (mMaxWidth != MAX_UNDEFINED) {
			if (width > mMaxWidth) {
				width = mMaxWidth; 
				modeWidth = MeasureSpec.EXACTLY;
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, modeWidth);
			}
		}
		
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		if (mMaxHeight != MAX_UNDEFINED) {
			if (height > mMaxHeight) {
				height = mMaxHeight; 
				modeHeight = MeasureSpec.EXACTLY;
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, modeHeight);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutWidth = r - l;
		if (mMaxWidth != MAX_UNDEFINED) {
			if (layoutWidth > mMaxWidth) {
				r -= (layoutWidth - mMaxWidth); 
			}
		}
		
		int layoutHeight = b - t;
		if (mMaxHeight != MAX_UNDEFINED) {
			if (layoutHeight > mMaxHeight) {
				b -= (layoutHeight - mMaxHeight); 
			}
		}
		super.onLayout(changed, l, t, r, b);
	}
	
	/**
	 * @param height in pixels
	 * @added 1.0
	 */
	public void setMaxHeight(int height) {
		if (height < 0) {
			mMaxHeight = MAX_UNDEFINED;
		} else {
			mMaxHeight = height;
		}
		requestLayout();
	}
	
	/**
	 * @return height in pixels
	 * @added 1.0
	 */
	public int getMaxHeight() {
		return mMaxHeight;
	}
	
	/**
	 * @param width in pixels
	 * @added 1.0
	 */
	public void setMaxWidth(int width) {
		if (width < 0) {
			mMaxWidth = MAX_UNDEFINED;
		} else {
			mMaxWidth = width;
		}
		requestLayout();
	}
	
	/**
	 * @return width in pixels
	 * @added 1.0
	 */
	public int getMaxWidth() {
		return mMaxWidth;
	}
}
