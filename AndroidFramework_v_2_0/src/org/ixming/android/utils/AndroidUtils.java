package org.ixming.android.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.URLUtil;

/**
 * 定义调用Android系统相关应用的方法
 * @author YinYong
 * @version 1.0
 */
public class AndroidUtils {
	private AndroidUtils() { }
	
	static final String TAG = AndroidUtils.class.getSimpleName();
	private static int displayWidth = 0;
	private static int displayHeight = 0;
	
	// >>>>>>>>>>>>>>>>>>>
	// 获取系统、机器相关的信息
	/**
	 * 获取sdk版本
	 */
	public static int getAndroidSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
	
	// 获取屏幕宽度
	public static int getDisplayWidth(Context context) {
		if (displayWidth <= 0) {
			WindowManager wm = (WindowManager) context.getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			displayWidth = dm.widthPixels;
		}
		return displayWidth;
	}

	// 获取屏幕高度
	public static int getDisplayHeight(Context context) {
		if (displayHeight <= 0) {
			WindowManager wm = (WindowManager) context.getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			displayHeight = dm.heightPixels;
		}
		return displayHeight;
	}
	
	/**
	 * 获取客户端的分辨率
	 */
	public static String getDeviceResolution(Context context) {
		return getDeviceResolution(context, "x");
	}
	/**
	 * 获取客户端的分辨率
	 * @param linkMark 连接符，{@link #getDeviceResolution(Context)} 使用的是“x”
	 */
	public static String getDeviceResolution(Context context, String linkMark) {
		int width = getDisplayWidth(context);
		int height = getDisplayHeight(context);
		return String.format("%d%s%d", width, linkMark, height);
	}
	
	// >>>>>>>>>>>>>>>>>>>
	// 获取应用程序相关的信息
	/**
	 * 返回当前程序版本号
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			// 这里的context.getPackageName()可以换成你要查看的程序的包名
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			FrameworkLog.e(TAG, "getAppVersionCode Exception: " + e.getMessage());
		}
		return versionCode;
	}
	
	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context, String defVersion) {
		String versionName = defVersion;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			// 这里的context.getPackageName()可以换成你要查看的程序的包名
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return defVersion;
			}
		} catch (Exception e) {
			FrameworkLog.e(TAG, "getAppVersionName Exception: " + e.getMessage());
		}
		return versionName;
	}
		
	/**
	 * 获得设备识别认证码
	 * <p/>
	 * 需要权限：android.permission.READ_PHONE_STATE
	 * @return the unique device ID, 
	 * for example, the IMEI for GSM and the MEID or ESN for CDMA phones. 
	 * Return null if device ID is not available. 
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return null;
		}
		return tm.getDeviceId();
	}
	
	// >>>>>>>>>>>>>>>>>>>
	// 调用系统的组件
	/**
	 * 需要CALL_PHONE权限
	 */
	public static void callPhone(Context context, String number) {
		if (null == context) {
			return ;
		}
		// 系统打电话界面：
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_DIAL);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//需要拨打的号码
		intent.setData(Uri.parse("tel:" + number));
		context.startActivity(intent);
	}
	
	/**
	 * 启动定位应用
	 * @added 1.0
	 */
	public static void locate(Context context, String chooserTilte,
			String lat, String lng, String addr) {
		if (null == context) {
			return ;
		}
		// 系统打电话界面：
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//需要拨打的号码
//		intent.setData(Uri.parse("geo:" + lat + "," + lng + "?q=my+street+address"));
		String uri = "geo:0,0"+ "?q=" + lat + "," + lng;
		if (null != addr && addr.length() > 0) {
			uri += ("(" + addr + ")");
		}
		intent.setData(Uri.parse(uri));
		
		try {
			context.startActivity(Intent.createChooser(intent, chooserTilte)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//			context.startActivity(intent);
		} catch (Exception e) {
			ToastUtils.showToast(context, "没有合适的应用打开位置信息");
		}
	}
	
	/**
	 * 调用系统的HTTP下载
	 */
	public static void callHTTPDownload(Context context, String chooserTilte, String url) {
		if (null == context) {
			return ;
		}
		// update v2
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//系统默认的action，用来打开默认的电话界面
//		intent.setAction(Intent.ACTION_VIEW);
//		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.setData(Uri.parse(URLUtil.guessUrl(url)));
//		intent.setDataAndType(Uri.parse(URLUtil.guessUrl(url)), "text/html");
		
		try {
			context.startActivity(Intent.createChooser(intent, chooserTilte)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		} catch (Exception e) {
			ToastUtils.showToast(context, "没有合适的应用打开链接");
		}
	}
	
	// >>>>>>>>>>>>>>>>>>>
	// 一些常用的方法封装及汇总
	public static boolean isMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}
}
