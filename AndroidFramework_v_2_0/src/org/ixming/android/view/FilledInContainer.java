package org.ixming.android.view;

import org.ixming.android.view.attrs.ViewProperties;

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
	
	private ViewProperties mViewProperties;
	public FilledInContainer(Context context) {
		super(context);
		initFilledInContainer();
	}
	
	public FilledInContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FilledInContainer(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initFilledInContainer();
	}
	
	private void initFilledInContainer() {
		mViewProperties = new ViewProperties(this);
	}
	
	protected ViewProperties getViewProperties() {
		return mViewProperties;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childWidth = ViewProperties.getAsInt(mViewProperties.getTrueHorizontalSpace());
		int childHeight = ViewProperties.getAsInt(mViewProperties.getTrueVerticalSpace());
		
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams lp = child.getLayoutParams();
				// change the pre-set parameters, no matter what is set previously,
				// we'd consider it filled in its parent
				lp.width = childWidth;
				lp.height = childHeight;
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left = getPaddingLeft();
		int top = getPaddingTop();
		int right = ViewProperties.getAsInt(mViewProperties.getWidth()) - getPaddingRight();
		int bottom = ViewProperties.getAsInt(mViewProperties.getHeight()) - getPaddingBottom();
		
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				child.layout(left, top, right, bottom);
			}
		}
	}
}
