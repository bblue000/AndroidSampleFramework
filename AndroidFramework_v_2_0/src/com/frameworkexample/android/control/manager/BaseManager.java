package com.frameworkexample.android.control.manager;

import org.ixming.android.utils.AndroidUtils;
import org.ixming.android.utils.ToastUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Activity中，一些复杂的数据请求，逻辑运算等在此处进行，让Activity较为简单地进行界面的更新操作. <br/>
 * <br/>
 * 即Activity调用其相应的Manager类进行控制、动作相关的操作。
 * 
 * @author Yin Yong
 * @version 1.0
 */
public abstract class BaseManager {
	private Activity mActivity;
	private Context mAppContext;
	private Handler mHandler;

	public BaseManager(Activity context, Handler handler) {
		if (null == context) {
			throw new NullPointerException("context is null");
		}
		this.mActivity = context;
		this.mAppContext = context.getApplicationContext();
		this.mHandler = handler;
	}

	protected final Activity getActivity() {
		return this.mActivity;
	}
	
	protected final Context getApplicationContext() {
		return this.mAppContext;
	}

	protected final Handler getHandler() {
		return mHandler;
	}

	// >>>>>>>>>>>>>>>>>>>>>>
	// convenient for show progress bar
	private ProgressDialog progressDialog;
	private final HideProgressDialogRun mHideProgressDialogRun = new HideProgressDialogRun();
	
	private class HideProgressDialogRun implements Runnable {
		private boolean mShouldFinalize;
		public void setShoundFinalize(boolean shoundFinalize) {
			mShouldFinalize = shoundFinalize;
		}
		
		@Override
		public void run() {
			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (mShouldFinalize) {
				progressDialog = null;
			}
		}
		
	}
	/**
     * 获取加载框
     */
    public void showLoadingDialog(String msg) {
    	if (null == progressDialog) {
    		progressDialog = new ProgressDialog(getActivity());
    	}
    	if (msg == null || msg.trim().equalsIgnoreCase("")) {
    		progressDialog.setMessage(null);
        }
		progressDialog.setMessage(msg);
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
        // pd.setCancelable(false);
    }
    
    public void hideLoadingDialog(boolean shoundFinalize) {
    	mHideProgressDialogRun.setShoundFinalize(shoundFinalize);
    	if (AndroidUtils.isMainThread()) {
    		mHideProgressDialogRun.run();
    	} else {
    		mHandler.post(mHideProgressDialogRun);
    	}
    }
	
	
	// >>>>>>>>>>>>>>>>>>>>>>
	// convenient for show toast
	public final void toastShow(final String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		ToastUtils.showToast(mAppContext, mHandler, msg);
	}

	public final void toastShow(final int resId) {
		toastShow(mAppContext.getString(resId));
	}
}
