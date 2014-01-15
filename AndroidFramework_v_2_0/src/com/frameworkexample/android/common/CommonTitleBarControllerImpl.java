package com.frameworkexample.android.common;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.android.utils.AndroidUtils;

import com.frameworkexample.android.R;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

class CommonTitleBarControllerImpl extends CommonTitleBarController {

	private boolean mHasCommonTitleBar;
	@ViewInject(id=R.id.common_titlebar)
	private View mRootView;
	@ViewInject(id=R.id.common_titlebar_left, parentId=R.id.common_titlebar)
	private TextView mLeftView;
	@ViewInject(id=R.id.common_titlebar_center, parentId=R.id.common_titlebar)
	private TextView mCenterView;
	@ViewInject(id=R.id.common_titlebar_right, parentId=R.id.common_titlebar)
	private TextView mRightView;
	public CommonTitleBarControllerImpl(View wrappedView) {
		InjectorUtils.defaultInstance().inject(this, wrappedView);
		mHasCommonTitleBar = (null != mRootView);
		checkRootView();
	}
	
	private void checkRootView() {
		if (null == mRootView) {
			throw new RuntimeException("there no title bar in the content view!");
		}
	}
	
	private void checkPosition(Position pos) {
		if (null == pos) {
			throw new NullPointerException("pos is null! please define one!");
		}
	}

	@Override
	public boolean hasCommonTitleBar() {
		return mHasCommonTitleBar;
	}

	@Override
	public View getTitleBarRootView() {
		checkRootView();
		return mRootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends View> T findTitleBarViewById(int id) {
		checkRootView();
		return (T) mRootView.findViewById(id);
	}

	@Override
	public CommonTitleBarControllerImpl setText(Position pos, String text) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		removeSideDrawables(tv);
		tv.setText(text);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setText(Position pos, int textRes) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		removeSideDrawables(tv);
		tv.setText(textRes);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setIcon(Position pos, Drawable drawable) {
		TextView tv = getView(pos);
		showView(tv);
		removeText(tv);
		removeSideDrawables(tv);
		tv.setBackgroundDrawable(drawable);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setIcon(Position pos, int imageRes) {
		TextView tv = getView(pos);
		showView(tv);
		removeText(tv);
		removeSideDrawables(tv);
		tv.setBackgroundResource(imageRes);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setTextAndIcon(Position pos, String text,
			Drawable left, Drawable top, Drawable right, Drawable bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(text);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setTextAndIcon(Position pos, int textRes,
			Drawable left, Drawable top, Drawable right, Drawable bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(textRes);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}
	
	@Override
	public CommonTitleBarControllerImpl setTextAndIcon(Position pos, String text,
			int left, int top, int right, int bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(text);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl setTextAndIcon(Position pos, int textRes,
			int left, int top, int right, int bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(textRes);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	@Override
	public TextView getView(Position pos) {
		checkRootView();
		checkPosition(pos);
		switch (pos) {
		case Center:
			return mCenterView;
		case Left:
			return mLeftView;
		case Right:
			return mRightView;
		}
		return null;
	}

	@Override
	public CommonTitleBarControllerImpl getCommonTitleBarController() {
		return this;
	}

	@Override
	public CommonTitleBarControllerImpl showView(Position pos) {
		showView(getView(pos));
		return this;
	}
	
	@Override
	public CommonTitleBarController hideView(Position pos) {
		return hideView(pos, true);
	}

	@Override
	public CommonTitleBarControllerImpl hideView(Position pos, boolean removeContent) {
		TextView tv = getView(pos);
		hideView(tv, removeContent);
		return this;
	}

	@Override
	public CommonTitleBarController hideAll() {
		return hideAll(true);
	}
	
	@Override
	public CommonTitleBarController hideAll(boolean removeContent) {
		hideView(mLeftView, removeContent);
		hideView(mCenterView, removeContent);
		hideView(mRightView, removeContent);
		return this;
	}
	
	private void showView(TextView tv) {
		AndroidUtils.setViewVisibility(tv, View.VISIBLE);
	}
	
	private void hideView(TextView tv, boolean removeContent) {
		AndroidUtils.setViewVisibility(tv, View.INVISIBLE);
		if (removeContent) {
			clearContent(tv);
		}
	}
	
	private void clearContent(TextView tv) {
		removeText(tv);
		removeBackground(tv);
		removeSideDrawables(tv);
	}
	
	private void removeText(TextView tv) {
		tv.setText(null);
	}
	
	private void removeBackground(TextView tv) {
		tv.setBackgroundResource(0);
	}
	
	private void removeSideDrawables(TextView tv) {
		tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
	}

}
