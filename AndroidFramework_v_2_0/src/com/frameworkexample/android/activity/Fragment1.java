package com.frameworkexample.android.activity;

import org.ixming.android.res.ResTargetType;
import org.ixming.android.res.ResUtils;
import org.ixming.android.res.annotation.ResInject;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseFragment;
import com.frameworkexample.android.utils.LogUtils;

public class Fragment1 extends BaseFragment {

	@ResInject(id=android.R.integer.config_longAnimTime, type=ResTargetType.Integer)
	private int res1;
	
	@ResInject(id=R.string.hello_world, type=ResTargetType.String)
	private String res2;
	
	@ResInject(id=R.array.testStrArr, type=ResTargetType.StringArray)
	private String[] res3;
	
	@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Bitmap)
	private Bitmap res4;
	
	@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Drawable)
	private Drawable res5;
	
	@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Drawable)
	private String res6;
	
	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		view.setBackgroundColor(0xFFFFFF00);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData(View view, Bundle savedInstanceState) {
		ResUtils.inject(appContext, this);
		
		LogUtils.i("yytest", "initData res1 = " + res1 );
		LogUtils.i("yytest", "initData res2 = " + res2 );
		LogUtils.i("yytest", "initData res3 = " + res3 );
		LogUtils.i("yytest", "initData res4 = " + res4 );
		LogUtils.i("yytest", "initData res5 = " + res5 );
		LogUtils.i("yytest", "initData res6 = " + res6 );
	}

	@Override
	protected Handler createActivityHandler() {

		return null;
	}

	@Override
	public void onClick(View v) {
	}

}
