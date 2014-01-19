package org.ixming.android.view.attrs;


import android.view.View;

public class ViewProperties {
	private View mView;
	private FloatRect mChildBounds;
	public ViewProperties(View iv) {
		mView = iv;
	}
	
	public float getWidth() {
		return mView.getMeasuredWidth();
	}

	public float getHeight() {
		return mView.getMeasuredHeight();
	}
	
	public float getCenterX() {
		return getWidth() / 2F;
	}
	
	public float getCenterY() {
		return getHeight() / 2F;
	}

	public float getHorizontalPadding() {
		return mView.getPaddingLeft() + mView.getPaddingRight();
	}

	public float getVerticalPadding() {
		return mView.getPaddingTop() + mView.getPaddingBottom();
	}

	public float getTrueHorizontalSpace() {
		return getWidth() - getHorizontalPadding();
	}

	public float getTrueVerticalSpace() {
		return getHeight() - getVerticalPadding();
	}
	
	public FloatRect getChildBounds() {
		if (null == mChildBounds) {
			mChildBounds = new FloatRect();
		}
		mChildBounds.set(mView.getPaddingLeft(), mView.getPaddingTop(),
				getWidth() - mView.getPaddingRight(), getHeight() - mView.getPaddingBottom());
		return mChildBounds;
	}
	
	public static int getAsInt(float val) {
		int symbol = (int) Math.signum(val);
		int absVal = (int) (Math.abs(val) + 0.5F);
		return absVal * symbol;
	}
}
