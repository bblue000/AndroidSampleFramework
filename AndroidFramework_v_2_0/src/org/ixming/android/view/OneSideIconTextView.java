package org.ixming.android.view;

import org.ixming.android.view.attrs.ViewProperties;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * used as a tab indicator.
 * <p/>
 * 这样的TextView在使用时，如果TextView指定了大小，它的Drawable会根据TextView的大小而缩放。
 * <p/>
 * @author Yin Yong
 */
public class OneSideIconTextView extends TextView {
	
	private ViewProperties mViewProperties;
	public OneSideIconTextView(Context context) {
		super(context);
		init();
	}
	
	public OneSideIconTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.style.Widget_TextView);
	}

	public OneSideIconTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		mViewProperties = new ViewProperties(this) {
			@Override
			public float getWidth() {
				return OneSideIconTextView.this.getWidth();
			}
			
			@Override
			public float getHeight() {
				return OneSideIconTextView.this.getHeight();
			}
		};
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (checkCompoundDrawables()) {
			return ;
		}
	}
	
	@Override
	public void setCompoundDrawables(Drawable left, Drawable top,
			Drawable right, Drawable bottom) {
		checkNumberOfCompoundDrawables(left, top, right, bottom);
		super.setCompoundDrawables(left, top, right, bottom);
	}
	
	private void checkNumberOfCompoundDrawables(Drawable left, Drawable top,
			Drawable right, Drawable bottom) {
		int count = 0;
		if (null != left) {
			count ++;
		}
		if (null != top) {
			count ++;
		}
		if (null != right) {
			count ++;
		}
		if (null != bottom) {
			count ++;
		}
		if (count > 1) {
			throw new IllegalArgumentException("only one compound drawable should be set!");
		}
	}
	
	private boolean checkCompoundDrawables() {
		Drawable[] drawables = getCompoundDrawables();
		if (null == drawables) {
			return false;
		}
		int width = getWidth();
		int height = getHeight();
		if (width == 0 || height == 0) {
			return false;
		}
		boolean flag = false;
		if (null != drawables[0]) {
			flag = applyHorizontalDrawable(drawables[0]);
		}
		if (null != drawables[1]) {
			flag = applyVerticalDrawable(drawables[1]);
		}
		if (null != drawables[2]) {
			flag = applyHorizontalDrawable(drawables[2]);
		}
		if (null != drawables[3]) {
			flag = applyVerticalDrawable(drawables[3]);
		}
		if (flag) {
			super.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
		}
		return flag;
	}
	
	private boolean applyHorizontalDrawable(Drawable hd) {
		float textWidth = TextUtils.isEmpty(getText()) ? 0 : getLayout().getLineWidth(0);
		int remainWidth = Math.max(0, ViewProperties.getAsInt(
				mViewProperties.getTrueHorizontalSpace() - textWidth - getCompoundDrawablePadding()));
		int remainHeight = ViewProperties.getAsInt(mViewProperties.getTrueVerticalSpace());
		return check0(hd, remainWidth, remainHeight);
	}

	private boolean applyVerticalDrawable(Drawable vd) {
		int textHeight = TextUtils.isEmpty(getText()) ? 0 : getLayout().getHeight();
		int remainWidth = ViewProperties.getAsInt(mViewProperties.getTrueHorizontalSpace());
		int remainHeight = Math.max(0, ViewProperties.getAsInt(
				mViewProperties.getTrueVerticalSpace() - textHeight - getCompoundDrawablePadding()));
		return check0(vd, remainWidth, remainHeight);
	}
	
	private boolean check0(Drawable d, int remainWidth, int remainHeight) {
		Rect rect = d.getBounds();
		int imageWidth = d.getIntrinsicWidth();
		int imageHeight = d.getIntrinsicHeight();
		if (remainWidth < imageWidth) {
			imageWidth = remainWidth;
		}
		
		if (remainHeight < imageHeight) {
			imageHeight = remainHeight;
		}
		
		if (iconWithSameWidthAndHeight()) {
			int size = Math.min(imageWidth, imageHeight);
			if (rect.width() != size || rect.height() != size) {
				d.setBounds(0, 0, size, size);
				return true;
			}
		} else {
			if (rect.width() != imageWidth || rect.height() != imageHeight) {
				d.setBounds(0, 0, imageWidth, imageHeight);
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean iconWithSameWidthAndHeight() {
		return false;
	}
}

