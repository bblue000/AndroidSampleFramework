package org.ixming.android.view;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomViewPager extends ViewPager {

	// 由于ViewPager的生命周期理论上是长于OnPageChangeListener的，
	// 如果存在需要被系统回收的OnPageChangeListener，此处不必再持有它
	private final HashSet<WeakReference<OnPageChangeListener>> mListenerSet
		= new HashSet<WeakReference<OnPageChangeListener>>();
	public CustomViewPager(Context context) {
		super(context);
		initCustomViewPager();
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomViewPager();
	}
	
	private void initCustomViewPager() {
		super.setOnPageChangeListener(new OnPageChangeListenerImpl());
	}
	
	/**
	 * 增加一个事件监听器
	 * @param listener 新的监听器，如果重复将不予添加。
	 * @return true if 成功加入，false if 已经存在改监听器或者listener为null
	 */
	public boolean addOnPageChangeListener(OnPageChangeListener listener) {
		if (null == listener) {
			return false;
		}
		if (!search(listener, false)) {
			mListenerSet.add(new WeakReference<ViewPager.OnPageChangeListener>(listener));
			return true;
		}
		return false;
	}
	
	/**
	 * 推荐使用 {@link #addOnPageChangeListener(OnPageChangeListener)} ，因为对于 {@code ViewPager} 可能不止一个事件监听器
	 * <p/>
	 * 如果 {@code listener} 为null，相当于清空监听列表。
	 */
	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		if (null == listener) {
			mListenerSet.clear();
			return ;
		}
		addOnPageChangeListener(listener);
	}
	
	public boolean removeOnPageChangeListener(OnPageChangeListener listener) {
		return search(listener, true);
	}
	
	private class OnPageChangeListenerImpl implements OnPageChangeListener {
		private int mScrollState;
		@Override
		public void onPageScrollStateChanged(int state) {
			mScrollState = state;
			if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
				// 如果已经停止Scroll，该行为将在所有scroll事件之后，是处理确认选定某一项的好时机的好时机
			}
			Iterator<WeakReference<OnPageChangeListener>> ite = mListenerSet.iterator();
			while (ite.hasNext()) {
				WeakReference<OnPageChangeListener> ref = ite.next();
				OnPageChangeListener tmp;
				if (null == ref || null == (tmp = ref.get())) {
					ite.remove();
					continue;
				}
				tmp.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// position都是以左边的一项计
			Iterator<WeakReference<OnPageChangeListener>> ite = mListenerSet.iterator();
			while (ite.hasNext()) {
				WeakReference<OnPageChangeListener> ref = ite.next();
				OnPageChangeListener tmp;
				if (null == ref || null == (tmp = ref.get())) {
					ite.remove();
					continue;
				}
				tmp.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageSelected(int position) {
			// 一定是之前选择的非此项，且mScrollState == 2 —— SCROLL_STATE_SETTLING
			Iterator<WeakReference<OnPageChangeListener>> ite = mListenerSet.iterator();
			while (ite.hasNext()) {
				WeakReference<OnPageChangeListener> ref = ite.next();
				OnPageChangeListener tmp;
				if (null == ref || null == (tmp = ref.get())) {
					ite.remove();
					continue;
				}
				tmp.onPageSelected(position);
			}
		}
	}
	
	private boolean search(OnPageChangeListener listener, boolean remove) {
		Iterator<WeakReference<OnPageChangeListener>> ite = mListenerSet.iterator();
		while (ite.hasNext()) {
			WeakReference<OnPageChangeListener> ref = ite.next();
			OnPageChangeListener tmp;
			if (null == ref || null == (tmp = ref.get())) {
				ite.remove();
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
}
