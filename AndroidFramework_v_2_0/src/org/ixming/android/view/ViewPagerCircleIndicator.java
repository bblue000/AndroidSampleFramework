package org.ixming.android.view;

import org.ixming.android.view.attrs.ViewProperties;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerCircleIndicator extends View {

	private static final int DEFAULT_DIAMETER = 10; // dip
	private static final int DEFAULT_GAP = 10; // dip
	private static final int DEFAULT_GAP_STROKE_WIDTH = 1; // dip

	private static final int DEFAUTL_NORMAL_COLOR = Color.DKGRAY;
	private static final int DEFAUTL_SELECTED_COLOR = Color.GRAY;
	
	private CustomViewPager mViewPager;
	private PageListener mPageListener = new PageListener();
	private float mDensity;
	private OvalShape mNormalShape = new OvalShape();
	private ShapeDrawable mNormalDrawable = new ShapeDrawable();
	
	private OvalShape mSelectedShape = new OvalShape();
	private ShapeDrawable mSelectedDrawable = new ShapeDrawable();
	
	private Paint mGapPaint;

	private int mHorizontalGap;
	
	private boolean mIsConsideredScrolling = false;
	private int mCurrentPosition;
	private float mPositionOffsetRatio;
	
	
	public ViewPagerCircleIndicator(Context context) {
		super(context);
		init();
	}

	public ViewPagerCircleIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewPagerCircleIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mDensity = getContext().getResources().getDisplayMetrics().density;
		float normalDiameter = DEFAULT_DIAMETER * mDensity;
		int half = ViewProperties.getAsInt(normalDiameter / 2.0F);
		
		mNormalDrawable.setShape(mNormalShape);
		mNormalDrawable.setBounds(-half, -half, half, half);
		mNormalDrawable.getPaint().setStyle(Style.FILL);
		mNormalDrawable.getPaint().setColor(DEFAUTL_NORMAL_COLOR);
		
		mSelectedDrawable.setShape(mSelectedShape);
		mSelectedDrawable.setBounds(-half, -half, half, half);
		mSelectedDrawable.getPaint().setStyle(Style.FILL);
		mSelectedDrawable.getPaint().setColor(DEFAUTL_SELECTED_COLOR);
		
		mHorizontalGap = ViewProperties.getAsInt(DEFAULT_GAP * mDensity);
		
		mGapPaint = new Paint();
		mGapPaint.setStyle(Style.STROKE);
		mGapPaint.setColor(Color.TRANSPARENT);
		mGapPaint.setStrokeWidth(DEFAULT_GAP_STROKE_WIDTH * mDensity);
	}
	
	/**
	 * 设置没有选中状态下的直径
	 * <p/>
	 * 默认为10dip
	 * @param diameter 直径 in pixels
	 */
	public void setNormalSize(float diameter) {
		int half = ViewProperties.getAsInt(diameter / 2.0F);
		mNormalDrawable.setBounds(-half, -half, half, half);
		postInvalidate();
	}
	
	/**
	 * 设置选中状态下的直径
	 * <p/>
	 * 默认为10dip
	 * @param diameter 直径 in pixels
	 */
	public void setSelectedSize(float diameter) {
		int half = ViewProperties.getAsInt(diameter / 2.0F);
		mSelectedDrawable.setBounds(-half, -half, half, half);
		postInvalidate();
	}
	
	public void setNormalColor(int color) {
		mNormalDrawable.getPaint().setColor(color);
		postInvalidate();
	}
	
	public void setSelectedColor(int color) {
		mSelectedDrawable.getPaint().setColor(color);
		postInvalidate();
	}
	
	/**
	 * 设置两个顶点之间的距离
	 * <p/>
	 * 默认为10dip
	 * @param gap 距离 in pixels
	 */
	public void setHorizontalGap(int gap) {
		if (gap <= 0) {
			return ;
		}
		mHorizontalGap = gap;
		postInvalidate();
	}
	
	public int getHorizontalGap() {
		return mHorizontalGap;
	}
	
	public void setHorizontalGapLineColor(int color) {
		mGapPaint.setColor(color);
		postInvalidate();
	}
	
	public int getHorizontalGapLineColor() {
		return mGapPaint.getColor();
	}
	
	public void setHorizontalGapLineStrokeWidth(float width) {
		mGapPaint.setStrokeWidth(width);
		postInvalidate();
	}
	
	public float getHorizontalGapLineStrokeWidth() {
		return mGapPaint.getStrokeWidth();
	}

	public void setViewPager(CustomViewPager viewPager) {
		if (null != mViewPager) {
			mViewPager.removeOnPageChangeListener(mPageListener);
		}
		mViewPager = viewPager;
		if (null != mViewPager) {
			mViewPager.addOnPageChangeListener(mPageListener);
		}
		mCurrentPosition = getViewPageCurrentItem();
		mPositionOffsetRatio = 0.0F;
		requestLayout();
		postInvalidate();
		// on view pager changed
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int width;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = Math.max(0, getDesiredWidth());
			if (widthMode == MeasureSpec.AT_MOST) {
	            width = Math.min(widthSize, width);
	        }
		}
		int height;
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = Math.max(0, getDesiredHeight());
			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(heightSize, height);
	        }
		}
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private boolean hasItems() {
		return getViewPageCount() > 0;
	}
	
	private int getViewPageCount() {
		return (null != mViewPager && null != mViewPager.getAdapter()) ?
				mViewPager.getAdapter().getCount() : 0;
	}
	
	private int getViewPageCurrentItem() {
		return hasItems() ? mViewPager.getCurrentItem() : -1;
	}
	
	private boolean isConsideredScrolling() {
		return mIsConsideredScrolling;
	}
	
	private int calIndicatorLargerWidth() {
		return Math.max(0, Math.max(getNormalWidth(), getSelectedWidth()));
	}
	
	private int calIndicatorLargerHeight() {
		return Math.max(0, Math.max(getNormalHeight(), getSelectedHeight()));
	}
	
	private int getNormalWidth() {
		return Math.max(0, mNormalDrawable.getBounds().width());
	}
	
	private int getNormalHeight() {
		return Math.max(0, mNormalDrawable.getBounds().height());
	}
	
	private int getSelectedWidth() {
		return Math.max(0, mSelectedDrawable.getBounds().width());
	}
	
	private int getSelectedHeight() {
		return Math.max(0, mSelectedDrawable.getBounds().height());
	}
	
	private int getNormalNeighborHorizontalOffset() {
		return Math.max(0, getNormalWidth() + getHorizontalGap());
	}
	
	private int getDesiredWidth() {
		if (hasItems()) {
			int pageCount = getViewPageCount();
			return getPaddingLeft() // padding left
					+ pageCount * getNormalWidth() // indicators' width
					+ (pageCount - 1) * getHorizontalGap() // gap
					+ Math.max(0, getSelectedWidth() - getNormalWidth()) // if selected bound is larger
					+ getPaddingRight();
		}
		return -1;
	}
	
	private int getDesiredHeight() {
		if (hasItems()) {
			return getPaddingTop() // padding top
						+ calIndicatorLargerHeight() // indicators' height
						+ getPaddingBottom(); // padding bottom
		}
		return -1;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (!hasItems()) {
			return ;
		}
		final int count = getViewPageCount();
		final int baseLeft = ViewProperties.getAsInt((getWidth() - getDesiredWidth()) / 2.0F
				+ Math.max(0.0F, calIndicatorLargerWidth() / 2F));
				// ViewProperties.getAsInt(Math.max(0.0F, calIndicatorLargerWidth() / 2F));
		final int baseLine = ViewProperties.getAsInt(getPaddingTop() + getHeight() / 2F);
		
		canvas.translate(baseLeft, baseLine);
		canvas.save();
		final int neighborOffset = getNormalNeighborHorizontalOffset();
		for (int i = 0; i < count; i++) {
			if (i < count - 1) {
				canvas.drawLine(0, 0, neighborOffset, 0, mGapPaint);
			}
			if (isConsideredScrolling() || getViewPageCurrentItem() != i) {
				mNormalDrawable.draw(canvas);
			}
			canvas.translate(neighborOffset, 0);
		}
		canvas.restore();
		// draw selected
		canvas.translate(neighborOffset * (mCurrentPosition + mPositionOffsetRatio), 0);
		mSelectedDrawable.draw(canvas);
	}
	
	private class PageListener extends DataSetObserver
	implements ViewPager.OnPageChangeListener {
		private int mScrollState;

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			mIsConsideredScrolling = true;
			mCurrentPosition = position;
			mPositionOffsetRatio = positionOffset;
			invalidate();
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			mScrollState = state;
			if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
				mIsConsideredScrolling = false;
				mCurrentPosition = getViewPageCurrentItem();
				mPositionOffsetRatio = 0.0F;
				invalidate();
			}
		}

		@Override
		public void onPageSelected(int position) {
			// Only update the text here if we're not dragging or settling.
		}
	}
}
