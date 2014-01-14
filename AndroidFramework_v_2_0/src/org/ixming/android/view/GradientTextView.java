package org.ixming.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;

public class GradientTextView extends TextView {

	private boolean mTextShaderChanged = false;
	private int[] mTextShaderColors = null;
	private float[] mTextShaderPositions = null;
	
	private boolean mForegroundShaderChanged = false;
	private int[] mForegroundShaderColors = null;
	private float[] mForegroundShaderPositions = null;
	
	private Paint mForegroundPaint = new Paint();
	public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GradientTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GradientTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
		
	}
	
	/**
	 * 设置一个Shader，让文本绘制应用该Shader
	 */
	public void setTextShader(Shader shader) {
		getPaint().setShader(shader);
		postInvalidate();
	}
	
	/**
	 * 
	 * @param colors The colors to be distributed along the gradient line
	 * @param positions May be null. The relative positions [0..1] of each corresponding color in the colors array. 
	 * If this is null, the the colors are distributed evenly along the gradient line.
	 */
	public void setLinearGradientTextShader(int[] colors, float[] positions) {
		mTextShaderColors = colors;
		mTextShaderPositions = positions;
		mTextShaderChanged = true;
		postInvalidate();
	}
	
	public void setForegroundShader(Shader shader) {
		mForegroundPaint.setShader(shader);
		postInvalidate();
	}
	
	public void setLinearGradientForegroundShader(int[] colors, float[] positions) {
		mForegroundShaderColors = colors;
		mForegroundShaderPositions = positions;
		mForegroundShaderChanged = true;
		postInvalidate();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
//			LinearGradient lg = new LinearGradient(getPaddingLeft(), getPaddingTop() + getHeight() / 2, 
//					getPaddingLeft(), getHeight() - getPaddingBottom(),
//					new int[]{ Color.WHITE, Color.TRANSPARENT, Color.TRANSPARENT },
//					new float[]{ 0, 0.8F, 1.0F}, 
//					TileMode.CLAMP);
//			getPaint().setShader(lg);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mTextShaderChanged) {
			setTextShader(new LinearGradient(getPaddingLeft(), getPaddingTop(), 
					getPaddingLeft(), getHeight() - getPaddingBottom(),
					mTextShaderColors, mTextShaderPositions, TileMode.CLAMP));
			mTextShaderChanged = false;
		}
		if (mForegroundShaderChanged) {
			setForegroundShader(new LinearGradient(getPaddingLeft(), getPaddingTop(), 
					getPaddingLeft(), getHeight() - getPaddingBottom(),
					mForegroundShaderColors, mForegroundShaderPositions, TileMode.CLAMP));
			mForegroundShaderChanged = false;
		}
		
		super.onDraw(canvas);
		
//		Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), 
//				getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
//		canvas.drawRect(rect, mForegroundPaint);
		
		canvas.drawPaint(mForegroundPaint);
		
		
//		GradientDrawable d = new GradientDrawable(Orientation.TOP_BOTTOM,
//				new int[] { Color.TRANSPARENT, 0x88FFFFFF });
//		d.setShape(GradientDrawable.RECTANGLE);
//		d.setBounds(getPaddingLeft(), getPaddingTop(), getWidth()
//				- getPaddingRight(), getHeight() - getPaddingBottom());
//		d.setGradientType(GradientDrawable.LINEAR_GRADIENT);
//		d.draw(canvas);
		
	}
	
}
