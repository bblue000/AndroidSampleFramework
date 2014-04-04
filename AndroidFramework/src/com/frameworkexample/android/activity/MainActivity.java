package com.frameworkexample.android.activity;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.frameworkexample.android.R;
import com.frameworkexample.android.activity.base.BaseActivity;
import com.frameworkexample.android.control.manager.MainManager;
import com.frameworkexample.android.utils.LogUtils;

public class MainActivity extends BaseActivity {

	public static WeakReference<MainActivity> instance;
	
	private MainManager manager;
	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		instance = new WeakReference<MainActivity>(this);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData(View view, Bundle savedInstanceState) {
		manager = new MainManager(this, handler);
		manager.doSomething();
		finish();
		startActivity(new Intent(MainActivity.this, MainTabActivity.class));
//		System.gc();
//		while(true) {
//			Log.i("yytest", "loop");
//		}
	}

	@Override
	public Handler provideActivityHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				default:
					startActivity(new Intent(MainActivity.this, TestActivity.class));
					break;
				}
			}
			
			@Override
			protected void finalize() throws Throwable {
				LogUtils.d("yytest", "before Handler finalize A instance = " + MainActivity.instance.get());
				LogUtils.w("yytest", "Handler finalize!");
			}
		};
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		System.gc();
//		LogUtils.d("yytest", "A finish A instance = " + MainActivity.instance.get());
		LogUtils.e("yytest", "Activity onDestory!");
	}

}
