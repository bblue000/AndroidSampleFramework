package org.ixming.android.sharebind;

interface ShareMsgListener {

	public void sendSuccess(String result);
    
    public void sendFailure(String errorMsg);
	
}
