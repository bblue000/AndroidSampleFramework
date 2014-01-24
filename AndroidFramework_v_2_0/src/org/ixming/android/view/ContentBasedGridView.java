package org.ixming.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 根据内容高度，完全显示该GridView
 * 
 * @author Yin Yong
 */
public class ContentBasedGridView extends GridView {

	public ContentBasedGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initContentBasedGridView();
	}

	public ContentBasedGridView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.style.Widget_GridView);
	}

	public ContentBasedGridView(Context context) {
		super(context);
		initContentBasedGridView();
	}
	
	private void initContentBasedGridView() {
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec = MeasureSpec.makeMeasureSpec(ViewUtils.maxHeightOfView(), MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightSpec);
	}
	
}
