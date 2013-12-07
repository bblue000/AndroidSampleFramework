package org.ixming.android.inject;

/**
 * 配置那些需要使用动态注入。
 * <p>
 * 默认情况下，全部支持。
 * </p>
 * @author Yin Yong
 * @version 2.0
 */
public class InjectConfigure {

	private boolean mInjectReses = true;
	private boolean mInjectViews = true;
	
	public InjectConfigure() {}
	
	public InjectConfigure injectReses(boolean ifInject) {
		mInjectReses = ifInject;
		return this;
	}

	public InjectConfigure injectViews(boolean ifInject) {
		mInjectViews = ifInject;
		return this;
	}
	
	public boolean isInjectReses() {
		return mInjectReses;
	}
	
	public boolean isInjectViews() {
		return mInjectViews;
	}
	
	@Override
	public String toString() {
		return "InjectConfigure [mInjectReses=" + mInjectReses
				+ ", mInjectViews=" + mInjectViews + "]";
	}
}
