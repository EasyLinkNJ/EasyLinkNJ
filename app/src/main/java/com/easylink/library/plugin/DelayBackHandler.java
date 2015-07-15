package com.easylink.library.plugin;

import android.os.Handler;
import android.os.Message;

public class DelayBackHandler {

	private final int MSG_WHAT_EXIT = 1;
	private boolean mPreBack;
	private OnDelayBackListener mDelayBackLisn;
	
	private Handler mExitHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == MSG_WHAT_EXIT)
				mPreBack = false;
		}
	};
	
	public void triggerPreBack(){
		
		if(mPreBack){
			
			mExitHandler.removeMessages(MSG_WHAT_EXIT);
			if(mDelayBackLisn != null)
				mDelayBackLisn.onDelayBack(!mPreBack);
		}else{
			
			mExitHandler.sendEmptyMessageDelayed(MSG_WHAT_EXIT, 2000);
			mPreBack = true;
			
			if(mDelayBackLisn != null)
				mDelayBackLisn.onDelayBack(mPreBack);
		}
	}
	
	public void setOnDelayBackListener(OnDelayBackListener backLisn){
		
		mDelayBackLisn = backLisn;
	}
	
	public Handler getHandler(){
		
		return mExitHandler;
	}
	
	public static interface OnDelayBackListener{
		
		public void onDelayBack(boolean preBack);
	}
}
