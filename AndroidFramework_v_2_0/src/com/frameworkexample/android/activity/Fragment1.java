package com.frameworkexample.android.activity;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.ResTargetType;
import org.ixming.android.inject.annotation.ResInject;
import org.ixming.android.inject.annotation.ViewInject;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

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
	
//	@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Drawable)
//	@ResInject(id=R.string.hello_world, type=ResTargetType.Drawable)
//	@ResInject(id=R.string.hello_world, type=ResTargetType.Xml)
//	@ResInject(id=R.array.testStrArr, type=ResTargetType.Drawable)
	private String res6;
	
	
//	@ThemedResInject(name="ic_launcher", type=ResTargetType.Drawable)
	private Drawable res7;
//	@ThemedResInject(name="testStrArr", type=ResTargetType.IntArray)
	private int[] res8;
	
//	@ResInject(id=R.array.testStrArr, type=ResTargetType.String)
	private String[] res9;
	
	@ViewInject(id=R.id.tv1)
	private View view1;
	@ViewInject(id=R.id.tv2)
	private View view2;
	@ViewInject(id=R.id.tv2, parentId=R.id.rlt1)
	private View view3;
	@ViewInject(id=R.id.rlt1)
	private View view4;
//	@ViewInject(id=R.id.tv2)
	private RelativeLayout view5;
	@ViewInject(id=R.id.tv2, parentId=R.id.tv2)
	private View view6;
	
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
		InjectorUtils.defaultInstance().inject(this, getRootView());
		
		test1();
		
		test2();
		
		LogUtils.i("yytest", "initData res7 = " + res7 );
		LogUtils.i("yytest", "initData res8 = " + res8 );
		LogUtils.i("yytest", "initData res9 = " + res9 );
		LogUtils.i("yytest", "initData getIntArray = " + getActivity().getResources().getIntArray(R.array.testStrArr)[0] );
		LogUtils.i("yytest", "initData getMemoryClass = " + ((ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() );
	}
	
	private void test1() {
		LogUtils.i("yytest", "initData res1 = " + res1 );
		LogUtils.i("yytest", "initData res2 = " + res2 );
		LogUtils.i("yytest", "initData res3 = " + res3 );
		LogUtils.i("yytest", "initData res4 = " + res4 );
		LogUtils.i("yytest", "initData res5 = " + res5 );
		LogUtils.i("yytest", "initData res6 = " + res6 );
	}
	
	private void test2() {
		LogUtils.i("yytest", "initData view1 = " + view1 );
		LogUtils.i("yytest", "initData view2 = " + view2 );
		LogUtils.i("yytest", "initData view3 = " + view3 );
		LogUtils.i("yytest", "initData view4 = " + view4 );
		LogUtils.i("yytest", "initData view5 = " + view5 );
		LogUtils.i("yytest", "initData view6 = " + view6 );
	}

	@Override
	protected Handler createActivityHandler() {

		return null;
	}

	@Override
	public void onClick(View v) {
	}

}
