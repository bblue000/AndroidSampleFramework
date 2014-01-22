package org.ixming.android.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * used as a tab indicator.
 * <p/>
 * 这样的TextView在使用时，它的Drawable会根据TextView的大小而缩放。
 * <p/>
 * 且Drawable的宽高是相等的
 * @author Yin Yong
 */
public class TabTextView extends OneSideIconTextView {
	
	public TabTextView(Context context) {
		super(context);
	}
	
	public TabTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected boolean iconWithSameWidthAndHeight() {
		return true;
	}
	
}

