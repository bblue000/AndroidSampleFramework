package org.ixming.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HarmoniousImageView extends FrameLayout {

	/**
	 * currently shown ImageView's index
	 */
	private int mCurActiveIndex = 0;
	/**
	 * a array of two elements
	 */
	private ImageView[] mImageViews;
	private AnimationListener mAnimListener = new AnimListenerImpl();
	private Animation mInAnimation;
	public HarmoniousImageView(Context context) {
		this(context, null);
	}
	
	public HarmoniousImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HarmoniousImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		initAttrs(context, attrs, defStyle);
	}
	
	private void init() {
		setStaticTransformationsEnabled(true);
		mImageViews = new ImageView[2];
		
		ImageView iv1 = new ImageView(getContext());
		iv1.setLayoutParams(generateDefaultLayoutParams());
		iv1.setVisibility(GONE);
		mImageViews[0] = iv1;
		
		ImageView iv2 = new ImageView(getContext());
		iv2.setLayoutParams(generateDefaultLayoutParams());
		iv2.setVisibility(GONE);
		mImageViews[1] = iv2;
		
		addView(iv1);
		addView(iv2);
		
		mInAnimation = new AlphaAnimation(0.0F, 1.0F);
		mInAnimation.setAnimationListener(mAnimListener);
		mInAnimation.setDuration(600L);
	}
	
	private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
		if (null == attrs) {
			return ;
		}
		final ScaleType[] scaleTypeArray = {
	        ScaleType.MATRIX,
	        ScaleType.FIT_XY,
	        ScaleType.FIT_START,
	        ScaleType.FIT_CENTER,
	        ScaleType.FIT_END,
	        ScaleType.CENTER,
	        ScaleType.CENTER_CROP,
	        ScaleType.CENTER_INSIDE
	    };
		TypedArray a = context.obtainStyledAttributes(attrs,
				new int[] { android.R.attr.scaleType },
                defStyle, 0);
		
		int index = a.getInt(0, 1);
        if (index >= 0) {
            setScaleType(scaleTypeArray[index]);
        } else {
        	setScaleType(ScaleType.FIT_XY);
        }
		
		a.recycle();
	}
	
	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}
	
	public void setScaleType(ScaleType scaleType) {
		for (int i = 0; i < mImageViews.length; i++) {
			mImageViews[i].setScaleType(scaleType);
		}
	}
	
	/**
	 * 设置ImageBitmap
	 * <p/>
	 * 调用该方法都会导致动画显示图片
	 */
	public void setImageBitmap(Bitmap bm) {
		Drawable drawable = null;
		if (null != bm) {
			drawable = new BitmapDrawable(getResources(), bm);
		}
		setImageDrawable(drawable);
	}
	
	/**
	 * 使用一个Drawable设置图片
	 * <p/>
	 * 调用该方法都会导致动画显示图片
	 */
	public void setImageDrawable(Drawable drawable) {
		if (null == drawable) {
			clearImage(mImageViews[0]);
			clearImage(mImageViews[1]);
		} else {
			int newActiveIndex = (mCurActiveIndex + 1) % 2;
			mImageViews[mCurActiveIndex].clearAnimation();
			setImageBitmapDirectly(mImageViews[newActiveIndex], drawable);
			mImageViews[newActiveIndex].startAnimation(mInAnimation);
			mCurActiveIndex = newActiveIndex;
		}
	}
	
	/**
	 * 使用一个res下的文件设置图片
	 * <p/>
	 * 调用该方法都会导致动画显示图片
	 */
	public void setImageResource(int resId) {
		setImageDrawable(getResources().getDrawable(resId));
	}
	
	/**
	 * 调用该方法不会动画显示图片
	 */
	public void setImageResourceDirectly(int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		if (null == drawable) {
			return ;
		}
		setImageDrawableDirectly(drawable);
	}
	
	/**
	 * 调用该方法不会动画显示图片
	 */
	public void setImageDrawableDirectly(Drawable drawable) {
		int newActiveIndex = (mCurActiveIndex + 1) % 2;
		setImageBitmapDirectly(mImageViews[newActiveIndex], drawable);
		clearImage(mImageViews[mCurActiveIndex]);
		mCurActiveIndex = newActiveIndex;
	}
	
	/**
	 * 获取当前设置的显示图片动画（默认为淡入效果）
	 */
	public Animation getInAnimation() {
		return mInAnimation;
	}
	
	/**
	 * 设置当前设置的显示图片动画（默认为淡入效果）
	 */
	public void setInAnimation(Animation anim) {
		if (null == anim) {
			return ;
		}
		if (null != mInAnimation) {
			mInAnimation.setAnimationListener(null);
		}
		mInAnimation = anim;
		mInAnimation.setAnimationListener(mAnimListener);
	}
	
	/*package*/ void setImageBitmapDirectly(ImageView iv, Bitmap bm) {
		iv.bringToFront();
		iv.setImageBitmap(bm);
		iv.setVisibility(VISIBLE);
	}
	
	/*package*/ void setImageBitmapDirectly(ImageView iv, Drawable drawable) {
		iv.bringToFront();
		iv.setImageDrawable(drawable);
		iv.setVisibility(VISIBLE);
	}
	
	/*package*/ void clearImage(ImageView iv) {
		iv.setImageDrawable(null);
		iv.setVisibility(GONE);
	}
	
	private class AnimListenerImpl implements Animation.AnimationListener {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			clearImage(mImageViews[(mCurActiveIndex + 1) % 2]);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}
	
}
