package org.ixming.android.network;

public interface OnLoadListener
{
    public void onSuccess(Object obj, ReqBean reqMode);

    public void onError(Object obj, ReqBean reqMode);
}