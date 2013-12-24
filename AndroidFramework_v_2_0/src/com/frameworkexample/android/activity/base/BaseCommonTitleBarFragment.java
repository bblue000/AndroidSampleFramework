package com.frameworkexample.android.activity.base;

import android.view.View;

import com.frameworkexample.android.common.CommonTitleBarContainer;
import com.frameworkexample.android.common.CommonTitleBarController;


public abstract class BaseCommonTitleBarFragment extends BaseFragment
implements CommonTitleBarContainer {

	private CommonTitleBarController mController;
	@Override
	void prepareInitView(View rootView) {
		mController = CommonTitleBarController.from(rootView);
	}
	@Override
	public final boolean hasCommonTitleBar() {
		return mController.hasCommonTitleBar();
	}

	@Override
	public View getTitleBarRootView() {
		return mController.getTitleBarRootView();
	}

	@Override
	public <T extends View> T findTitleBarViewById(int id) {
		return mController.findTitleBarViewById(id);
	}
	
	@Override
	public CommonTitleBarController getCommonTitleBarController() {
		return mController;
	}
	
}
