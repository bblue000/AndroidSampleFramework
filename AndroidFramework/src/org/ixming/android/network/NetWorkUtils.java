package org.ixming.android.network;

import java.lang.reflect.Method;
import java.net.Socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.WebSettings;

public class NetWorkUtils {
    public final static String WIFI_STATE = "WIFI";
    private static int mIsOPhoneChecked = 0;
    
    public static boolean isOPhone()
    {
        switch (mIsOPhoneChecked)
        {
            case 0:
                mIsOPhoneChecked = 2;
                try
                {
                    Method method1 = NetworkInfo.class.getMethod("getApType");
                    Method method2 = NetworkInfo.class.getMethod("getInterfaceName");
                    Method method3 = Socket.class.getMethod("setInterface", String.class);
                    Method method4 = WebSettings.class.getMethod("setProxy", Context.class,
                            String.class, int.class);
                    if (method1 != null && method2 != null && method3 != null && method4 != null)
                    {
                        mIsOPhoneChecked = 1;
                        return true;
                    }
                } catch (Exception e)
                {
                }
                return false;
            case 1:
                return true;
            case 2:
                return false;
        }
        return false;
    }
    
    public static boolean hasActiveNetwork(Context context) {
    	try {
    		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(
        			Context.CONNECTIVITY_SERVICE);
        	return null != manager.getActiveNetworkInfo();
		} catch (Exception e) {
			return false;
		}
    }
}
