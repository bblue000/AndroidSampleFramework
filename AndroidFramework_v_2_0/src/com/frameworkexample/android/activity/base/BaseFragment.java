package com.frameworkexample.android.activity.base;

import org.ixming.android.inject.InjectConfigure;
import org.ixming.android.inject.InjectorUtils;

import com.frameworkexample.android.utils.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener{

	// root view是否已经创建，如果没有创建，而想使用应该创建后才能使用的方法时，将抛出异常
	private boolean mIsRootViewCreated = false;
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
	 * 持有该Fragment的FragmentActivity的引用
	 */
	protected FragmentActivity fragmentActivity;
	/**
	 * 这是一个Handler，由
	 */
	protected Handler handler;
	
	/**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * 
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * 
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 不推荐再重写此方法.所以加上了final<br/><br/>
	 * Not recommend that you override this method.<br/><br/>
	 * use {@link #onViewCreated(View, Bundle)} to do some thing
	 * @see {@link #onViewCreated(View, Bundle)}
	 */
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.i("BaseFragment","execute onCreateView!!! ");
		// 为了实现findViewById
		mRootView = inflater.inflate(provideLayoutResId(), container, false);
		// 保证RootView加载完成
		mIsRootViewCreated = true;
		return mRootView;
	}
	
	/**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	}
	
	/**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
	@Override
	public final void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 给一些变量赋值
		context = getActivity();
		appContext = context.getApplicationContext();
		fragmentActivity = getActivity();
		handler = provideActivityHandler();

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
	 * define the layout res of the activity
	 */
	protected abstract int provideLayoutResId();
	
	/**
	 * 创建一个本Activity的Handler对象，此方法在onCreate()中调用，且 在initView及initData之前。
	 * <p>
	 * 该方法的目的是分离代码，让需要Handler的Activity中的Handler的声明集中。
	 * </p>
	 * @added 1.0
	 */
	protected abstract Handler provideActivityHandler();

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
		ensureRootViewCreated();
		return mRootView;
	}

	@SuppressWarnings("unchecked")
	public final <T extends View>T findViewById(int id) {
		ensureRootViewCreated();
		return (T) mRootView.findViewById(id);
	}
	
	// utilities for injecting
	/**
	 * used by {@link #injectSelf()};
	 * <p/>
	 * default return null.
	 */
	protected InjectConfigure provideActivityInjectConfigure() {
		return null;
	}
	protected void injectSelf() {
		InjectConfigure provided = provideActivityInjectConfigure();
		if (null != provided) {
			injectSelf(provided);
		} else {
			InjectorUtils.defaultInstance().inject(this, getRootView());
		}
	}

	protected void injectSelf(InjectConfigure configure) {
		InjectorUtils.instanceBuildFrom(configure).inject(this, getRootView());
	}
	
	/**
	 * @param target 需要注入的对象
	 * @param rootView 所在的View， maybe null
	 */
	protected void injectTarget(Object target, View rootView) {
		InjectorUtils.defaultInstance().inject(target, rootView);
	}
	
	/**
	 * @param target 需要注入的对象
	 * @param rootView 所在的View，maybe null
	 * @param configure 注入的配置，maybe null
	 */
	protected void injectTarget(Object target, View rootView, InjectConfigure configure) {
		InjectorUtils.instanceBuildFrom(configure).inject(target, rootView);
	}
	
	
	// TODO >>>>>>>>>>>>>>>>>>>>>>>
	// 设置onClick监听事件
	/**
	 * 通过ID找到指定的View，并为之添加监听器；<br/>
	 * 该方法着重强调此View只需添加点击事件，而不会对之进行状态或者 显示的改变。
	 * 
	 * @see 推荐使用{@link org.ixming.android.inject.InjectorUtils}
	 */
	protected BaseFragment bindClickListener(int resId) {
		return bindClickListener(findViewById(resId));
	}

	/**
	 * 给指定的View添加监听器
	 * 
	 * @see 推荐使用{@link org.ixming.android.inject.InjectorUtils}
	 */
	protected BaseFragment bindClickListener(View view) {
		if (null != view) {
			view.setOnClickListener(this);
		}
		return this;
	}

	/**
	 * 移除resId指定的View的单击事件监听器
	 */
	protected BaseFragment removeClickListener(int resId) {
		return removeClickListener(findViewById(resId));
	}

	/**
	 * 移除View的单击事件监听器
	 */
	protected BaseFragment removeClickListener(View view) {
		if (null != view) {
			view.setOnClickListener(null);
		}
		return this;
	}

	@Override
	public void onClick(View v) {
	}
	
	public boolean onBackPressed() {
		return false;
	}
	
	protected final void ensureRootViewCreated() {
		if (!mIsRootViewCreated) {
			throw new IllegalStateException("root view hasn't been created yet");
		}
	}
	
	protected final Context getApplicationContext() {
		return appContext;
	}
	
	//TODO >>>>>>>>>>>>>>>>>>>>>>>>>>
	// 生命周期相关
	@Override
	public void onDetach() {
		LogUtils.i("BaseFragment","execute onDetach!!! ");	
		super.onDetach();
	}
	
	@Override
	public void onDestroyView() {
		LogUtils.i("BaseFragment","execute onDestroyView!!! ");	
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		LogUtils.i("BaseFragment","execute onDestroy!!! ");	
		super.onDestroy();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		LogUtils.i("BaseFragment","execute onHiddenChanged!!! hidden ? " + hidden);	
		super.onHiddenChanged(hidden);
	}
	
	@Override
	public void onResume() {
		LogUtils.d("BaseFragment","execute onResume!!! ");	
		super.onResume();
	}
	
	@Override
	public void onPause() {
		LogUtils.d("BaseFragment","execute onPause!!! ");	
		super.onPause();
	}
}