package com.frameworkexample.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseFragment;

public class Fragment3 extends BaseFragment {

	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		view.setBackgroundColor(0xFF00FFFF);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData(View view, Bundle savedInstanceState) {
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected Handler provideActivityHandler() {

		return null;
	}

}
