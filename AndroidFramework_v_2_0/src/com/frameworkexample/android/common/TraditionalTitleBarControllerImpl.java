package com.frameworkexample.android.common;

import org.ixming.android.inject.InjectConfigure;
import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.android.view.ViewUtils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.frameworkexample.android.R;
import com.frameworkexample.android.view.TraditionalTitleBar;

class TraditionalTitleBarControllerImpl extends TraditionalTitleBarController {

	private boolean mHasCommonTitleBar;
	@ViewInject(id = R.id.common_titlebar)
	private TraditionalTitleBar mRootView;
	private TextView mLeftView;
	private TextView mCenterView;
	private TextView mRightView;
	public TraditionalTitleBarControllerImpl(View wrappedView) {
		InjectorUtils.instanceBuildFrom(new InjectConfigure().setToAll(false).injectViews(true))
			.inject(this, wrappedView);
		mHasCommonTitleBar = (null != mRootView);
		checkRootView();
		
		mLeftView = mRootView.getLeftView();
		mCenterView = mRootView.getCenterView();
		mRightView = mRootView.getRightView();
	}
	
	private void checkRootView() {
		if (!mHasCommonTitleBar) {
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
	public TraditionalTitleBarControllerImpl setText(Position pos, String text) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		removeSideDrawables(tv);
		tv.setText(text);
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl setText(Position pos, int textRes) {
		return setText(pos, getString(textRes));
	}

	@Override
	public TraditionalTitleBarControllerImpl setIcon(Position pos, Drawable drawable) {
		TextView tv = getView(pos);
		showView(tv);
		removeText(tv);
		removeSideDrawables(tv);
		tv.setBackgroundDrawable(drawable);
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl setIcon(Position pos, int imageRes) {
		TextView tv = getView(pos);
		showView(tv);
		removeText(tv);
		removeSideDrawables(tv);
		tv.setBackgroundResource(imageRes);
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl setTextAndIcon(Position pos, String text,
			Drawable left, Drawable top, Drawable right, Drawable bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(text);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl setTextAndIcon(Position pos, int textRes,
			Drawable left, Drawable top, Drawable right, Drawable bottom) {
		return setTextAndIcon(pos, getString(textRes), left, top, right, bottom);
	}
	
	@Override
	public TraditionalTitleBarControllerImpl setTextAndIcon(Position pos, String text,
			int left, int top, int right, int bottom) {
		TextView tv = getView(pos);
		showView(tv);
		removeBackground(tv);
		tv.setText(text);
		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl setTextAndIcon(Position pos, int textRes,
			int left, int top, int right, int bottom) {
		return setTextAndIcon(pos, getString(textRes), left, top, right, bottom);
	}
	
	@Override
	public TraditionalTitleBarController setTextAndBackground(Position pos,
			String text, int bg) {
		TextView tv = getView(pos);
		showView(tv);
		removeSideDrawables(tv);
		tv.setText(text);
		tv.setBackgroundResource(bg);
		return this;
	}

	@Override
	public TraditionalTitleBarController setTextAndBackground(Position pos,
			String text, Drawable bg) {
		TextView tv = getView(pos);
		showView(tv);
		removeSideDrawables(tv);
		tv.setText(text);
		tv.setBackgroundDrawable(bg);
		return this;
	}

	@Override
	public TraditionalTitleBarController setTextAndBackground(Position pos,
			int textRes, int bg) {
		return setTextAndBackground(pos, getString(textRes), bg);
	}

	@Override
	public TraditionalTitleBarController setTextAndBackground(Position pos,
			int textRes, Drawable bg) {
		return setTextAndBackground(pos, getString(textRes), bg);
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
	public TraditionalTitleBarControllerImpl getCommonTitleBarController() {
		return this;
	}

	@Override
	public TraditionalTitleBarControllerImpl showView(Position pos) {
		showView(getView(pos));
		return this;
	}
	
	@Override
	public TraditionalTitleBarController hideView(Position pos) {
		return hideView(pos, true);
	}

	@Override
	public TraditionalTitleBarControllerImpl hideView(Position pos, boolean removeContent) {
		TextView tv = getView(pos);
		hideView(tv, removeContent);
		return this;
	}

	@Override
	public TraditionalTitleBarController hideAll() {
		return hideAll(true);
	}
	
	@Override
	public TraditionalTitleBarController hideAll(boolean removeContent) {
		hideView(mLeftView, removeContent);
		hideView(mCenterView, removeContent);
		hideView(mRightView, removeContent);
		return this;
	}
	
	@Override
	public TraditionalTitleBarController bindClickListener(Position pos,
			View.OnClickListener listener) {
		TextView tv = getView(pos);
		tv.setOnClickListener(listener);
		return this;
	}

	@Override
	public TraditionalTitleBarController removeClickListener(Position pos) {
		TextView tv = getView(pos);
		tv.setOnClickListener(null);
		return this;
	}
	
	private void showView(TextView tv) {
		ViewUtils.setViewVisible(tv);
	}
	
	private void hideView(TextView tv, boolean removeContent) {
		ViewUtils.setViewInvisible(tv);
		if (removeContent) {
			clearContent(tv);
		}
	}
	
	private String getString(int resId) {
		checkRootView();
		return mRootView.getResources().getString(resId);
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
		tv.setBackgroundDrawable(null);
	}
	
	private void removeSideDrawables(TextView tv) {
		tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
	}

}
