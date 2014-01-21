package org.ixming.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DependOnWidthRelativeLayout extends RelativeLayout {
	
	private static final int DEPENDON_START = 0;
	private static final int DEPENDON_END = DEPENDON_START + 3;
	
	public static final int DEPENDON_WIDTH = DEPENDON_START;
	public static final int DEPENDON_HEIGHT = DEPENDON_START + 1;
	public static final int DEPENDON_LARGER = DEPENDON_START + 2;
	public static final int DEPENDON_SMALLER = DEPENDON_END;
	private int mDependOnMode = DEPENDON_WIDTH;
	public DependOnWidthRelativeLayout(Context context) {
		super(context);
	}
	
	public DependOnWidthRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DependOnWidthRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		switch (mDependOnMode) {
		case DEPENDON_WIDTH:
			measuredHeight = measuredWidth;
			break;
		case DEPENDON_HEIGHT:
			measuredWidth = measuredHeight;
			break;
		case DEPENDON_LARGER:
			int larger = Math.max(measuredWidth, measuredHeight);
			measuredWidth = larger;
			measuredHeight = larger;
			break;
		case DEPENDON_SMALLER:
			int smaller = Math.min(measuredWidth, measuredHeight);
			measuredWidth = smaller;
			measuredHeight = smaller;
			break;
		default:
			return ;
		}
		super.onMeasure(MeasureSpec.makeMeasureSpec(measuredWidth, widthMode),
				MeasureSpec.makeMeasureSpec(measuredHeight, heightMode));
	}
	
	public void setDependOnMode(int mode) {
		// check
		if (mode >= DEPENDON_START && mode <= DEPENDON_END) {
			if (mode != mDependOnMode) {
				mDependOnMode = mode;
				postInvalidate();
			}
		}
	}
}
