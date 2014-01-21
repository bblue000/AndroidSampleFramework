package org.ixming.android.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

public class BannerGallery extends Gallery {
	
	final String TAG = BannerGallery.class.getSimpleName();
	
	public static abstract class BaseBannerAdapter<T> extends BaseAdapter {
		private final int mCount = 1 << 16;
		private List<T> mContentList = new ArrayList<T>();
		@Override
		public final int getCount() {
			return mContentList.size() <= 1 ? mContentList.size() : mCount;
		}
		
		public void setBanners(Collection<? extends T> c) {
			mContentList.clear();
			if (!c.isEmpty()) {
				mContentList.addAll(c);
			}
			notifyDataSetInvalidated();
			notifyDataSetChanged();
		}
		
		public <Tt extends T>void addBanner(Tt banner) {
			if (null != banner) {
				mContentList.add(banner);
				notifyDataSetChanged();
			}
		}
		
		public void clearBanners() {
			setBanners(null);
		}
		
		public int getDataCount() {
			return mContentList.size();
		}
		
		public int transferPosition(int position) {
			if (isEmpty()) {
				return -1;
			}
			return position % getDataCount();
		}

		@Override
		public final T getItem(int position) {
			return mContentList.get(transferPosition(position));
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public final View getView(int position, View convertView, ViewGroup parent) {
			int truePos = transferPosition(position);
			T data = getItem(position);
			return getView(truePos, data, convertView, parent);
		}
		
		public abstract View getView(int position, T data, View convertView, ViewGroup parent);
		
	}
	
	private float mLastX;
	private float mLastY;
	private BaseBannerAdapter<?> mAdapter;
	private DataSetObserver mDataSetObserver = new DataSetObserver();
	private final int FIRST_ITEM_MULTIPLIER = 100;
	private final int LAST_ITEM_MULTIPLIER = 1000;
	private final int LAST_ITEM_ATLIST = 5;
	/**
	 * banner interval
	 */
	private final long INTERVAL = 5000L;
	private Handler mHandler = new Handler();
	private Runnable mIntervalRun = new Runnable() {
		@Override
		public void run() {
			if (moveNext0()) {
				mHandler.postDelayed(mIntervalRun, INTERVAL);
			}
		}
	};
	
	private final HashSet<OnItemSelectedListener> mOnItemSelectedListenerSet
		= new HashSet<AdapterView.OnItemSelectedListener>();
	
	public BannerGallery(Context context) {
		super(context);
		init();
	}
	
	public BannerGallery(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public BannerGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// 没有音响效果
		setSoundEffectsEnabled(false);
		setUnselectedAlpha(0.9F);
		
		super.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
	}
	
	public void setBannerAdapter(BaseBannerAdapter<?> adapter) {
		if (null != mAdapter) {
			mAdapter.unregisterDataSetObserver(mDataSetObserver);
		}
		mAdapter = (BaseBannerAdapter<?>) adapter;
		super.setAdapter(mAdapter);
		if (null != mAdapter) {
			mAdapter.registerDataSetObserver(mDataSetObserver);
		}
		invalidateAdapterData();
	}
	
	@Override
	public BaseBannerAdapter<?> getAdapter() {
		return mAdapter;
	}
	
	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		throw new UnsupportedOperationException("use setBannerAdapter instead!");
	}
	
	@Override
	public int getSelectedItemPosition() {
		BaseBannerAdapter<?> adapter = getAdapter();
		if (null != adapter && !adapter.isEmpty()) {
			return adapter.transferPosition(super.getSelectedItemPosition());
		}
		return super.getSelectedItemPosition();
	}
	
	public boolean addOnItemSelectedListener(OnItemSelectedListener listener) {
		if (null == listener) {
			return false;
		}
		if (!search(listener, false)) {
			mOnItemSelectedListenerSet.add(listener);
			return true;
		}
		return false;
	}
	
	public boolean removeOnItemSelectedListener(OnItemSelectedListener listener) {
		return search(listener, true);
	}
	
	@Override
	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		if (null == listener) {
			mOnItemSelectedListenerSet.clear();
			return ;
		}
		addOnItemSelectedListener(listener);
	}
	
	private boolean search(OnItemSelectedListener listener, boolean remove) {
		Iterator<OnItemSelectedListener> ite = mOnItemSelectedListenerSet.iterator();
		while(ite.hasNext()) {
			OnItemSelectedListener tmp = ite.next();
			if (null == tmp) {
				ite.remove();
				if (null == listener) {
					return true;
				}
				continue;
			}
			if (tmp == listener) {
				if (remove) {
					ite.remove();
				}
				return true;
			}
		}
		return false;
	}
	
	private void invalidateAdapterData() {
		if (null != mAdapter) {
			if (mAdapter.getDataCount() <= 1) {
				super.setSelection(0);
			} else {
				super.setSelection(mAdapter.getDataCount() * FIRST_ITEM_MULTIPLIER);
				restartInterval();
				return ;
			}
		}
		interruptInterval();
	}
	
	public void restartInterval() {
		mHandler.removeCallbacks(mIntervalRun);
		mHandler.postDelayed(mIntervalRun, INTERVAL);
	}
	
	public void interruptInterval() {
		mHandler.removeCallbacks(mIntervalRun);
	}
	
	private class DataSetObserver extends android.database.DataSetObserver {
		@Override
		public void onInvalidated() {
			invalidateAdapterData();
		}
		
		@Override
		public void onChanged() {
			invalidateAdapterData();
		}
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float firstX = e1.getX();
		float secondX = e2.getX();
		if (firstX < secondX) {
			// movePrevious
			onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, mCounterfeitDpadLeftKeyEvent);
		} else if (firstX > secondX) {
			// moveNext
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, mCounterfeitDpadRightKeyEvent);
		}
		return true;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mHandler.removeCallbacksAndMessages(null);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.mLastX = event.getX();
			this.mLastY = event.getY();
			requestDisallowInterceptTouchEvent(true);
			interruptInterval();
			break;
		case MotionEvent.ACTION_MOVE:
			float newX = event.getX();
			float newY = event.getY();
			float f9 = Math.abs(newX - this.mLastX);
			float f10 = Math.abs(newY - this.mLastY);
			if (f9 < f10)
				requestDisallowInterceptTouchEvent(false);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			requestDisallowInterceptTouchEvent(false);
			restartInterval();
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// counterfeit code
	private MotionEvent mCounterfeitDownEvent;
	private MotionEvent mCounterfeitMoveEvent;
	private KeyEvent mCounterfeitDpadLeftKeyEvent;
	private KeyEvent mCounterfeitDpadRightKeyEvent;
	/*private*/ {
		long eventTime = SystemClock.uptimeMillis();
		mCounterfeitDownEvent = MotionEvent.obtain(eventTime, eventTime, MotionEvent.ACTION_DOWN, 0.0F, 0.0F, 0);
		mCounterfeitMoveEvent = MotionEvent.obtain(eventTime, eventTime, MotionEvent.ACTION_MOVE, 1.0F, 0.0F, 0);
		mCounterfeitDpadLeftKeyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT);
		mCounterfeitDpadRightKeyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT);
	}
	
	/*package*/ boolean moveNext0() {
		if (null != mAdapter && mAdapter.getDataCount() > 1) {
			int cur = getSelectedItemPosition();
			int next = cur + 1;
			if (next >= Math.min(mAdapter.getCount() - mAdapter.getDataCount() * LAST_ITEM_ATLIST,
					mAdapter.getDataCount() * LAST_ITEM_MULTIPLIER)
					|| cur < mAdapter.getDataCount() * FIRST_ITEM_MULTIPLIER) {
				cur = mAdapter.getDataCount() * FIRST_ITEM_MULTIPLIER + cur % mAdapter.getDataCount();
				// 伪代码，如果当前选中的项不在理想的范围内，则直接先跳到某个位置
				super.setSelection(cur);
			}
			// 模拟事件，触发类似ViewFlipper的滚动画面
			onScroll(mCounterfeitDownEvent, mCounterfeitMoveEvent, 1.0F, 0F);
			// moveNext
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, mCounterfeitDpadRightKeyEvent);
			return true;
		}
		return false;
	}
	
	/*package*/ boolean movePrevious0() {
		if (null != mAdapter && mAdapter.getDataCount() > 1) {
			int cur = getSelectedItemPosition();
			int pre = cur - 1;
			if (pre <= mAdapter.getDataCount() * FIRST_ITEM_MULTIPLIER) {
				cur = mAdapter.getDataCount() * FIRST_ITEM_MULTIPLIER + cur % mAdapter.getDataCount();
				// 伪代码，如果当前选中的项不在理想的范围内，则直接先跳到某个位置
				super.setSelection(cur);
			}
			// 模拟事件，触发类似ViewFlipper的滚动画面
			onScroll(mCounterfeitDownEvent, mCounterfeitMoveEvent, -1.0F, 0F);
			// movePrevious
			onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, mCounterfeitDpadLeftKeyEvent);
			return true;
		}
		return false;
	}
	
	private class OnItemSelectedListenerImpl implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Iterator<OnItemSelectedListener> ite = mOnItemSelectedListenerSet.iterator();
			while(ite.hasNext()) {
				OnItemSelectedListener tmp = ite.next();
				tmp.onItemSelected(parent, view, getAdapter().transferPosition(position), id);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			Iterator<OnItemSelectedListener> ite = mOnItemSelectedListenerSet.iterator();
			while(ite.hasNext()) {
				OnItemSelectedListener tmp = ite.next();
				tmp.onNothingSelected(parent);
			}
		}
	}
}
