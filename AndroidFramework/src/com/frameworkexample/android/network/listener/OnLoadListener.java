package com.frameworkexample.android.network.listener;

import com.frameworkexample.android.network.ReqBean;

public interface OnLoadListener
{
    public void onSuccess(Object obj, ReqBean reqMode);

    public void onError(Object obj, ReqBean reqMode);
}