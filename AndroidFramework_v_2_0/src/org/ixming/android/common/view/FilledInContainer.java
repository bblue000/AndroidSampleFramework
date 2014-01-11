package org.ixming.android.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 根图层作为一个特殊的容器，允许纳入子容器或者View；
 * 
 * 特别地，所有加入此容器中的View/ViewGroup都是fill_parent的。
 * 
 * @author Yin Yong
 */
public class FilledInContainer extends ViewGroup {
	
	public FilledInContainer(Context context) {
		super(context);
	}
	
	public FilledInContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FilledInContainer(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();
		
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams lp = child.getLayoutParams();
				// change the pre-set parameters, no matter what is set previously,
				// we'd consider it filled in its parent
				lp.width = width - paddingLeft - paddingRight;
				lp.height = height - paddingTop - paddingBottom;
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(paddingLeft, paddingTop,
						width - paddingRight, height - paddingBottom);
			}
		}
	}
}
