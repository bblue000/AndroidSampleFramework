package com.frameworkexample.android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseActivity;

public class TestActivity extends BaseActivity {

	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		view.setBackgroundColor(Color.RED);
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
