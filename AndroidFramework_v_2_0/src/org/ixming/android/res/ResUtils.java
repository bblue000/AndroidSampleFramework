package org.ixming.android.res;

import java.lang.reflect.Field;

import org.ixming.android.res.annotation.ResInject;
import org.ixming.android.utils.FrameworkLog;

import android.content.Context;

/**
 * 此处提供除了layout之外的资源文件的运行时注入，运行时获取资源，给target中的成员变量并赋值。
 * 
 * <p>
 * <strong>注意：</strong>
 * 该工具的确提供了图片的加载，但是获取到的图片没有规避OOM的操作；<br/>
 * 此处可以用于加载单个的，相对小的图片；<br/>
 * 如果需要加载大型图片，或者需要自定义/获取图片局部，推荐使用框架中的“图片加载工具”。
 * </p>
 * 
 * @author Yin Yong
 */
public class ResUtils {
	
	static final String TAG = ResUtils.class.getSimpleName();
	
	/**
	 * @param context provide a context, from which we get the target resource
	 * @param target any instances of different classes can be injected.
	 */
	public static void inject(Context context, Object target) {
		injectObject(context, target);
	}

	private static void injectObject(Context context, Object target) {
		// inject object
        Field[] fields = target.getClass().getDeclaredFields();
        
        if (null == fields || fields.length == 0) {
        	return ;
        }
        ResLoader reLoader = new ResLoader();
        for (Field field : fields) {
			ResInject resInject = field.getAnnotation(ResInject.class);
			if (null == resInject) continue;
			Object res = reLoader.loadRes(
                    resInject.type(), context, resInject.id());
            if (null == res) continue;
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
                field.set(target, res);
            } catch (Throwable e) {
                FrameworkLog.e(TAG, "set value failed: target valu type = " + field.getType()
                		+ ", and resTargetType(set) = " + resInject.type()
                		+ ", result type = " + res.getClass()
                		+ ", details: " + e.getMessage());
            }
		}
	}
}
