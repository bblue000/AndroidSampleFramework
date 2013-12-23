package com.frameworkexample.android.activity.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseFragmentActivity extends FragmentActivity implements
		OnClickListener {

	// 根View，外部提供的View——Activity的根View其实是内置的FrameLayout
	private View mRootView;
	/**
	 * 这是一个Activity引用
	 */
	protected Context context;
	/**
	 * 这是一个application context引用
	 */
	protected Context appContext;
	/**
	 * 这是一个Handler，由
	 */
	protected Handler handler;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 给一些变量赋值
		context = this;
		appContext = getApplicationContext();
		handler = provideActivityHandler();

		// 加载root view
		mRootView = LayoutInflater.from(this).inflate(provideLayoutResId(),
				null);
		setContentView(mRootView);

		// 细分生命周期
		prepareInitView(mRootView);
		initView(mRootView);
		initListener();
		prepareInitData(mRootView, savedInstanceState);
		initData(mRootView, savedInstanceState);
	}

	// TODO >>>>>>>>>>>>>>>>>>>>>>>>>
	// 需要子类提供的一些东西
	/**
	 * 创建一个本Activity的Handler对象，此方法在onCreate()中调用，且 在initView及initData之前。
	 * 
	 * @added 1.0
	 */
	protected abstract Handler provideActivityHandler();

	/**
	 * define the layout res of the activity
	 */
	protected abstract int provideLayoutResId();

	// TODO >>>>>>>>>>>>>>>>>>>>>>>>
	// 在onCreate中细分的步骤 start
	/**
	 * 这是为了一些弥补操作预留的
	 * 
	 * @param rootView
	 */
	void prepareInitView(View rootView) {
	};

	/**
	 * called before {@link #initData(View, android.os.Bundle)} while
	 * {@link Activity#onCreated(android.os.Bundle)} is running
	 * 
	 * @param view
	 *            root view of the activity
	 * 
	 * @see #findViewById(int)
	 */
	protected abstract void initView(View rootView);

	/**
	 * initView 之后，initData之前被调用
	 * 
	 * @added 1.0
	 */
	protected abstract void initListener();

	/**
	 * 这是为了一些弥补操作预留的
	 * 
	 * @param rootView
	 */
	void prepareInitData(View rootView, Bundle savedInstanceState) {
	};

	/**
	 * called immediately after {@link #initView(View)} while
	 * {@link Activity#onCreated(android.os.Bundle)} is running
	 * 
	 * @param view
	 *            root view of the activity
	 * @param savedInstanceState
	 *            If the activity is being re-created from a previous saved
	 *            state, this is the state.
	 */
	protected abstract void initData(View view, Bundle savedInstanceState);

	// >>>>>>>>>>>>>>>>>>>>>>>>
	// 在onCreate中细分的步骤 end

	// TODO 获取内部的View
	/**
	 * this method returns the pure root View.
	 */
	public final View getRootView() {
		return mRootView;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Activity已经有了实现。
	 * </p>
	 */
	@Override
	public View findViewById(int id) {
		return mRootView.findViewById(id);
	}

	// TODO >>>>>>>>>>>>>>>>>>>>>>>
	// 设置onClick监听事件
	/**
	 * 通过ID找到指定的View，并为之添加监听器；<br/>
	 * 该方法着重强调此View只需添加点击事件，而不会对之进行状态或者 显示的改变。
	 * 
	 * @see 推荐使用{@link org.ixming.android.inject.InjectorUtils}
	 */
	protected BaseFragmentActivity bindClickListener(int resId) {
		return bindClickListener(findViewById(resId));
	}

	/**
	 * 给指定的View添加监听器
	 * 
	 * @see 推荐使用{@link org.ixming.android.inject.InjectorUtils}
	 */
	protected BaseFragmentActivity bindClickListener(View view) {
		if (null != view) {
			view.setOnClickListener(this);
		}
		return this;
	}

	/**
	 * 移除resId指定的View的单击事件监听器
	 */
	protected BaseFragmentActivity removeClickListener(int resId) {
		return removeClickListener(findViewById(resId));
	}

	/**
	 * 移除View的单击事件监听器
	 */
	protected BaseFragmentActivity removeClickListener(View view) {
		if (null != view) {
			view.setOnClickListener(null);
		}
		return this;
	}

	@Override
	public void onClick(View v) {
	}

	// TODO >>>>>>>>>>>>>>>>>>>>>
	/**
	 * <p>
	 * base基类中已经有了默认的实现，如果有特殊的需要，请重写此方法； 该默认实现不保证符合所有的特殊情况。
	 * </p>
	 * 这是一个规范返回事件，并建议使用此方法，针对性地使用跳转Activity的动画
	 * 
	 * @return 此方法在Activity的onKeyDown方法中调用，如果你打算同步在按返回建时使用 同样的返回命令，则返回true
	 * @added 1.0
	 */
	public boolean customBack() {
		finish();
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (customBack()) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>
	// 可能需要在下面的生命周期中处理一些通用事务
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
