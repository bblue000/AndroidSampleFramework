package com.frameworkexample.android.common;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.android.utils.AndroidUtils;

import com.frameworkexample.android.R;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

abstract class CommonTitleBarControllerImpl1 extends CommonTitleBarController1 {

//	private boolean mHasCommonTitleBar;
//	@ViewInject(id=R.id.common_titlebar)
//	private View mRootView;
//	@ViewInject(id=R.id.common_titlebar_left, parentId=R.id.common_titlebar)
//	private TextView mLeftView;
//	@ViewInject(id=R.id.common_titlebar_center, parentId=R.id.common_titlebar)
//	private TextView mCenterView;
//	@ViewInject(id=R.id.common_titlebar_right, parentId=R.id.common_titlebar)
//	private TextView mRightView;
//	public CommonTitleBarControllerImpl1(View wrappedView) {
//		InjectorUtils.defaultInstance().inject(this, wrappedView);
//		mHasCommonTitleBar = (null != mRootView);
//		checkRootView();
//	}
//	
//	private void checkRootView() {
//		if (null == mRootView) {
//			throw new RuntimeException("there no title bar in the content view!");
//		}
//	}
//	
//	private void checkPosition(Position pos) {
//		if (null == pos) {
//			throw new NullPointerException("pos is null! please define one!");
//		}
//	}
//
//	@Override
//	public boolean hasCommonTitleBar() {
//		return mHasCommonTitleBar;
//	}
//
//	@Override
//	public View getTitleBarRootView() {
//		checkRootView();
//		return mRootView;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T extends View> T findTitleBarViewById(int id) {
//		checkRootView();
//		return (T) mRootView.findViewById(id);
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setText(Position pos, String text) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		removeSideDrawables(tv);
//		tv.setText(text);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setText(Position pos, int textRes) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		removeSideDrawables(tv);
//		tv.setText(textRes);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setIcon(Position pos, Drawable drawable) {
//		TextView tv = getView(pos);
//		removeText(tv);
//		removeSideDrawables(tv);
//		tv.setBackgroundDrawable(drawable);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setIcon(Position pos, int imageRes) {
//		TextView tv = getView(pos);
//		removeText(tv);
//		removeSideDrawables(tv);
//		tv.setBackgroundResource(imageRes);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setTextAndIcon(Position pos, String text,
//			Drawable left, Drawable top, Drawable right, Drawable bottom) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		tv.setText(text);
//		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setTextAndIcon(Position pos, int textRes,
//			Drawable left, Drawable top, Drawable right, Drawable bottom) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		tv.setText(textRes);
//		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
//		return this;
//	}
//	
//	@Override
//	public CommonTitleBarControllerImpl1 setTextAndIcon(Position pos, String text,
//			int left, int top, int right, int bottom) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		tv.setText(text);
//		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 setTextAndIcon(Position pos, int textRes,
//			int left, int top, int right, int bottom) {
//		TextView tv = getView(pos);
//		removeBackground(tv);
//		tv.setText(textRes);
//		tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
//		return this;
//	}
//
//	
//	@Override
//	public TextView getView(Position pos) {
//		checkRootView();
//		checkPosition(pos);
//		switch (pos) {
//		case Center:
//			return mCenterView;
//		case Left:
//			return mLeftView;
//		case Right:
//			return mRightView;
//		}
//		return null;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 getCommonTitleBarController() {
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 showView(Position pos) {
//		AndroidUtils.setViewVisibility(getView(pos), View.VISIBLE);
//		return this;
//	}
//
//	@Override
//	public CommonTitleBarControllerImpl1 hideView(Position pos, boolean removeContent) {
//		TextView tv = getView(pos);
//		AndroidUtils.setViewVisibility(tv, View.INVISIBLE);
//		if (removeContent) {
//			removeText(tv);
//			removeBackground(tv);
//			removeSideDrawables(tv);
//		}
//		return this;
//	}
//	
//	private void removeBackground(TextView tv) {
//		tv.setBackgroundResource(0);
//	}
//	
//	private void removeText(TextView tv) {
//		tv.setText(null);
//	}
//	
//	private void removeSideDrawables(TextView tv) {
//		tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//	}

}
