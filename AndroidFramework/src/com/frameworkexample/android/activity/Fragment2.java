package com.frameworkexample.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseFragment;

public class Fragment2 extends BaseFragment {

	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		view.setBackgroundColor(0xFFFF00FF);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData(View view, Bundle savedInstanceState) {
	}

	@Override
	public Handler provideActivityHandler() {

		return null;
	}

	@Override
	public void onClick(View v) {
	}

}
