package com.frameworkexample.android.activity.base;

import android.view.View;

import com.frameworkexample.android.common.CommonTitleBarContainer;
import com.frameworkexample.android.common.CommonTitleBarController;

/**
 * 拥有一个统一标题栏的Activity。
 * <br/>
 * 里面的Title bar用法相当于ActionBar。
 * <br/>
 * 
 * 但是更加定制。ActionBar的限制较多，不如单个应用自己动态定制了。
 * @author Yin Yong
 */
public abstract class BaseCommonTitleBarActivity extends BaseActivity implements
		CommonTitleBarContainer {

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
	public final View getTitleBarRootView() {
		return mController.getTitleBarRootView();
	}

	@Override
	public final <T extends View> T findTitleBarViewById(int id) {
		return mController.findTitleBarViewById(id);
	}
	
	@Override
	public final CommonTitleBarController getCommonTitleBarController() {
		return mController;
	}
}
