package com.frameworkexample.android.control.manager;

import com.frameworkexample.android.network.ReqBean;
import com.frameworkexample.android.network.SendRequestUtil;
import com.frameworkexample.android.network.listener.OnLoadListener;
import com.frameworkexample.android.utils.LogUtils;

import android.content.Context;
import android.os.Handler;

public class MainManager extends BaseManager {

	public MainManager(Context context, Handler handler) {
		super(context, handler);
	}

	public void doSomething() {
		SendRequestUtil.sendTest(getApplicationContext(), getHandler(), onLoad);
	}
	
	OnLoadListener onLoad = new OnLoadListener() {
		@Override
		public void onSuccess(Object obj, ReqBean reqMode) {
			LogUtils.d("yytest", "onLoad invoked!");
			getHandler().sendEmptyMessage(0);
		}
		
		@Override
		public void onError(Object obj, ReqBean reqMode) {
			
		}
		
		protected void finalize() throws Throwable {
			LogUtils.w("yytest", "OnLoadListener finalize!");
		}
	};
	
	protected void finalize() throws Throwable {
		LogUtils.w("yytest", "manager finalize!");
	}
	
	
}
