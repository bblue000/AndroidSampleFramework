package org.ixming.android.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class ViewUtils {

	private ViewUtils() { }
	
	public static void setViewVisibility(View view, int visibility) {
		if (null == view) {
			return;
		}
		if (view.getVisibility() != visibility) {
			view.setVisibility(visibility);
		}
	}
	
	public static void setViewVisible(View view) {
		setViewVisibility(view, View.VISIBLE);
	}
	
	public static void setViewInvisible(View view) {
		setViewVisibility(view, View.INVISIBLE);
	}
	
	public static void setViewGone(View view) {
		setViewVisibility(view, View.GONE);
	}
	
	public static RelativeLayout newTransparentRelativeLayout(Context context) {
		RelativeLayout layout = new RelativeLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setBackgroundDrawable(null);
		return layout;
	}
	
	public static int maxWidthOfView() {
		return (0x1 << 30) - 1;
	}
	
}
