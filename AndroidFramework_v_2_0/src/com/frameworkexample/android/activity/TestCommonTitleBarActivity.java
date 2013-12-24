package com.frameworkexample.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseCommonTitleBarActivity;
import com.frameworkexample.android.common.CommonTitleBarController.Position;

public class TestCommonTitleBarActivity extends BaseCommonTitleBarActivity {

	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView(View rootView) {
		getCommonTitleBarController().setIcon(Position.Center, R.drawable.ic_launcher);
	}

	@Override
	protected void initListener() {
	}

	@Override
	protected void initData(View view, Bundle savedInstanceState) {
	}

	@Override
	protected Handler provideActivityHandler() {
		return null;
	}
}
