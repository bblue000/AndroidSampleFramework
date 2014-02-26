package com.frameworkexample.android.view;

import org.ixming.android.view.OneSideIconTextView;
import org.ixming.android.view.attrs.ViewProperties;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.frameworkexample.android.R;

/**
 * 一些配置
 * 
 * @author Yin Yong
 */
class TraditionalTitleBarConfig {
	
	private Context mContext;
	float mDensity;
	
	private final int mSideViewSideSpace;
	private final int mCenterViewSideSpace;
	
	public TraditionalTitleBarConfig(Context context) {
		mContext = context;
		mDensity = mContext.getResources().getDisplayMetrics().density;
		
		mSideViewSideSpace = mContext.getResources().getDimensionPixelOffset(
				R.dimen.tradi_titlebar_side_margin);
		mCenterViewSideSpace = mContext.getResources().getDimensionPixelOffset(
				R.dimen.tradi_titlebar_center_hmargin);
	}
	
	public void initCenterView(OneSideIconTextView centerView) {
		centerView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		centerView.setGravity(Gravity.CENTER);
		
		centerView.setSingleLine(true);
		centerView.setEllipsize(TruncateAt.END);
		
		centerView.setTextAppearance(mContext, R.style.TitleBar_Traditional_Text_Center);
		
		centerView.setCompoundDrawablePadding(getCenterDrawablePadding());
	}
	
	public void initLeftView(OneSideIconTextView leftView) {
		leftView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		leftView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		
		leftView.setSingleLine(true);
		
		leftView.setTextAppearance(mContext, R.style.TitleBar_Traditional_Text_Sides);

		leftView.setCompoundDrawablePadding(getSidesDrawablePadding());
	}
	
	public void initRightView(OneSideIconTextView rightView) {
		rightView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		rightView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		
		rightView.setSingleLine(true);
		
		rightView.setTextAppearance(mContext, R.style.TitleBar_Traditional_Text_Sides);

		rightView.setCompoundDrawablePadding(getSidesDrawablePadding());
	}
	
	public int getSideViewSideSpace() {
		return mSideViewSideSpace;
	}
	
	public int getCenterViewSidesSpace() {
		return mCenterViewSideSpace;
	}
	
	public int getCenterDrawablePadding() {
		return mContext.getResources().getDimensionPixelOffset(R.dimen.tradi_titlebar_drawable_padding);
	}
	
	public int getSidesDrawablePadding() {
		return mContext.getResources().getDimensionPixelOffset(R.dimen.tradi_titlebar_drawable_padding);
	}
}

public class TraditionalTitleBar extends ViewGroup {

	private TraditionalTitleBarConfig mConfig;
	
	private OneSideIconTextView mCenterView;
	private OneSideIconTextView mLeftView;
	private OneSideIconTextView mRightView;
	
	public TraditionalTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initTraditionalTitleBar();
	}

	public TraditionalTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTraditionalTitleBar();
	}

	public TraditionalTitleBar(Context context) {
		super(context);
		initTraditionalTitleBar();
	}
	
	private void initTraditionalTitleBar() {
		mConfig = new TraditionalTitleBarConfig(getContext());
		
		mCenterView = new OneSideIconTextView(getContext());
		mConfig.initCenterView(mCenterView);
		super.addView(mCenterView);
		
		
		mLeftView = new OneSideIconTextView(getContext());
		mConfig.initLeftView(mLeftView);
		super.addView(mLeftView);
		
		
		mRightView = new OneSideIconTextView(getContext());
		mConfig.initLeftView(mRightView);
		super.addView(mRightView);
		
		mCenterView.setText("Center View");
		mLeftView.setText("Left View");
		mRightView.setText("Right View");
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int childWidth = Math.max(0, widthSize - getPaddingLeft() - getPaddingRight()
				- mConfig.getSideViewSideSpace() * 2);
		int childHeight = Math.max(0, heightSize - getPaddingTop() - getPaddingBottom());
		
		measureChildren(MeasureSpec.makeMeasureSpec(childWidth, widthMode),
				MeasureSpec.makeMeasureSpec(childHeight, heightMode));
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int lAbs = mLeftView.getMeasuredWidth();
		int rAbs = mRightView.getMeasuredWidth();
		
		boolean isChanged = false;
		if (lAbs != rAbs) {
			int sideMaxWidth = Math.max(lAbs, rAbs);
			LayoutParams lp = mCenterView.getLayoutParams();
			lp.width = Math.max(0, childWidth - sideMaxWidth * 2 - mConfig.getCenterViewSidesSpace() * 2);
			isChanged = true;
		}
		
		if (isChanged) {
			measureChildren(MeasureSpec.makeMeasureSpec(childWidth, widthMode),
					MeasureSpec.makeMeasureSpec(childHeight, heightMode));
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//TODO 高度方面 ，采取竖直居中
		
		int left = getPaddingLeft();
		int top = getPaddingTop();
		int right = Math.max(0, getMeasuredWidth() - getPaddingRight());
		//int bottom = Math.max(0, getMeasuredHeight() - getPaddingBottom());
		
		
		int leftViewTop = top + ViewProperties.getAsInt(getMeasuredHeight() / 2F
				- mLeftView.getMeasuredHeight() / 2F);
		int leftViewBottom = leftViewTop + mLeftView.getMeasuredHeight();
		int leftViewLeft = left + mConfig.getSideViewSideSpace();
		int leftViewRight = Math.min(right - mConfig.getSideViewSideSpace(),
				left + mConfig.getSideViewSideSpace() + mLeftView.getMeasuredWidth());
		mLeftView.layout(leftViewLeft, leftViewTop, leftViewRight, leftViewBottom);
		
		
		
		int rightViewTop = top + ViewProperties.getAsInt(getMeasuredHeight() / 2F
				- mRightView.getMeasuredHeight() / 2F);
		int rightViewBottom = rightViewTop + mRightView.getMeasuredHeight();
		int rightViewRight = Math.max(0, right - mConfig.getSideViewSideSpace());
		int rightViewLeft = Math.max(left + mConfig.getSideViewSideSpace(),
				rightViewRight - mRightView.getMeasuredWidth());
		mRightView.layout(rightViewLeft, rightViewTop, rightViewRight, rightViewBottom);
		
		
		//TODO center view
		int centerViewTop = top + ViewProperties.getAsInt(getMeasuredHeight() / 2F
				- mCenterView.getMeasuredHeight() / 2F);
		int centerViewBottom = centerViewTop + mCenterView.getMeasuredHeight();
		int sideMaxWidth = Math.max(mLeftView.getWidth(), mRightView.getWidth());
		int centerViewLeft = leftViewLeft + sideMaxWidth + mConfig.getCenterViewSidesSpace();
		int centerViewRight = Math.max(centerViewLeft,
				Math.max(0, rightViewRight - sideMaxWidth - mConfig.getCenterViewSidesSpace()));
		mCenterView.layout(centerViewLeft, centerViewTop, centerViewRight, centerViewBottom);
	}
	
	public TextView getCenterView() {
		return mCenterView;
	}
	
	public TextView getLeftView() {
		return mLeftView;
	}
	
	public TextView getRightView() {
		return mRightView;
	}
}
