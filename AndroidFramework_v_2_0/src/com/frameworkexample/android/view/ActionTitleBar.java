package com.frameworkexample.android.view;

import java.util.ArrayList;

import org.ixming.android.view.OneSideIconTextView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 一些配置
 * 
 * @author Yin Yong
 */
class ActionTitleBarConfig {
	
	private Context mContext;
	float mDensity;
	
	public ActionTitleBarConfig(Context context) {
		mContext = context;
		mDensity = mContext.getResources().getDisplayMetrics().density;
	}
	
	public void initActionView(OneSideIconTextView actionView) {
		actionView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		actionView.setGravity(Gravity.CENTER_VERTICAL);
		actionView.setSingleLine(true);
	}
	
}

public class ActionTitleBar extends RelativeLayout {

	static final String TAG = ActionTitleBar.class.getSimpleName();
	
	private static int VIEW_CACHE_SIZE = 5;
	private static ArrayList<View> sCacheView = new ArrayList<View>(VIEW_CACHE_SIZE);
	private static View getFromViewCache() {
		if (!sCacheView.isEmpty()) {
			return sCacheView.remove(0);
		}
		return null;
	}
	
	private static void saveToViewCache(View view) {
		if (sCacheView.size() < VIEW_CACHE_SIZE) {
			sCacheView.add(view);
		}
	}
	
	private final int ID_INVALID = -1;
	private final int ID_LEFTVIEW = 0xF1000001;
	private final int ID_ACTIONCONTAINER = 0xF2000001;
	private final int ID_ACTIONLAYOUT = 0xF2100001;
	
	private ActionTitleBarConfig mConfig;
	private OneSideIconTextView mLeftView;
	private HorizontalScrollView mActionContainer;
	private LinearLayout mActionLayout;
	private int mSeperatorViewLayoutId = ID_INVALID;
	private LayoutInflater mInflater;
	
	public ActionTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initActionTitleBar();
	}

	public ActionTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initActionTitleBar();
	}

	public ActionTitleBar(Context context) {
		super(context);
		initActionTitleBar();
	}

	private void initActionTitleBar() {
		mConfig = new ActionTitleBarConfig(getContext());
		mInflater = LayoutInflater.from(getContext());
		
		mLeftView = new OneSideIconTextView(getContext());
		mLeftView.setId(ID_LEFTVIEW);
		RelativeLayout.LayoutParams mainViewLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		mainViewLP.addRule(CENTER_VERTICAL);
		mainViewLP.addRule(ALIGN_PARENT_LEFT);
		mLeftView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		super.addView(mLeftView, mainViewLP);
		
		
		mActionContainer = new HorizontalScrollView(getContext());
		mActionContainer.setId(ID_ACTIONCONTAINER);
		RelativeLayout.LayoutParams actionContainerLP = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		mainViewLP.addRule(CENTER_VERTICAL);
		mainViewLP.addRule(ALIGN_PARENT_RIGHT);
		mainViewLP.addRule(RIGHT_OF, ID_LEFTVIEW);
		super.addView(mActionContainer, actionContainerLP);
		
		
		mActionLayout = new LinearLayout(getContext());
		mActionLayout.setId(ID_ACTIONLAYOUT);
		ViewGroup.LayoutParams actionLayoutLP = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mActionLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		mActionContainer.addView(mActionLayout, actionLayoutLP);
	}
	
	public void setActionSeperatorView(int layoutId) {
		if (layoutId <= 0) {
			mSeperatorViewLayoutId = ID_INVALID;
		} else {
			mSeperatorViewLayoutId = layoutId;
		}
	}
	
	private View newActionSeperatorView() {
		if (mSeperatorViewLayoutId == ID_INVALID) {
			return null;
		}
		return mInflater.inflate(mSeperatorViewLayoutId, mActionLayout, false);
	}
	
	public TextView getLeftView() {
		return mLeftView;
	}
	
	public void removeAction(int id) {
		View actionView = mActionLayout.findViewById(id);
		if (null != actionView) {
			View seperatorView = mActionLayout.findViewWithTag(actionView.getId());
			if (null != seperatorView) {
				mActionLayout.removeView(seperatorView);
			}
			mActionLayout.removeView(actionView);
			saveToViewCache(actionView);
		}
	}
	
	public TextView addAction(int id) {
		final int oldChildCount = mActionLayout.getChildCount();
		TextView view = newActionView(id);
		if (oldChildCount > 0) {
			View seperatorView = newActionSeperatorView();
			seperatorView.setTag(id);
			mActionLayout.addView(seperatorView);
		}
		mActionLayout.addView(view);
		return view;
	}
	
	private TextView newActionView(int id) {
		OneSideIconTextView view = (OneSideIconTextView) getFromViewCache();
		if (null == view) {
			view = new OneSideIconTextView(getContext());
		}
		view.setId(id);
		mConfig.initActionView(view);
		return view;
	}
}
