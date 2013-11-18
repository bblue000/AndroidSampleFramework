package org.ixming.android.common.view;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class ViewUtils {

	private ViewUtils() { }
	
	public static RelativeLayout newTransparentRelativeLayout(Context context) {
		RelativeLayout layout = new RelativeLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setBackgroundColor(0x00000000);
		return layout;
	}
	
}
