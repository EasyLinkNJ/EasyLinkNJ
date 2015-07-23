package com.easylink.library.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法控制的帮助类
 * @author yhb
 */
public class SoftInputHandler{

	public final int HIDE_INPUT_METHOD_MILLIS = 200;
	
	private Activity mActivity;
	private InputMethodManager mInputMethodManager;
	private boolean mSoftKeyLocked;
	
	public SoftInputHandler(Activity activity){
		
		mActivity = activity;
	}
	
	private void initInputMethodMgr(){
		
		if(mInputMethodManager == null)
			mInputMethodManager = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

    /**
     * 默认实现
     * @param tokenView
     * @return
     */
    public boolean showSoftInput(View tokenView){

        return showSoftInput(tokenView, InputMethodManager.SHOW_IMPLICIT);//InputMethodManager.HIDE_NOT_ALWAYS
    }

    /**
     * 自定义实现
     * @param tokenView
     * @param flag
     * @return
     */
    public boolean showSoftInput(View tokenView, int flag){

        initInputMethodMgr();
        return mInputMethodManager.showSoftInput(tokenView, flag);
    }

    /**
     * 默认实现
     * @param tokenView
     * @return
     */
	public boolean hideSoftInput(View tokenView){

        return hideSoftInput(tokenView, InputMethodManager.HIDE_NOT_ALWAYS);
	}

    /**
     * 自定义实现
     * @param tokenView
     * @param flag
     * @return
     */
    public boolean hideSoftInput(View tokenView, int flag){

        initInputMethodMgr();
        return mInputMethodManager.hideSoftInputFromWindow(tokenView.getWindowToken(), flag);
    }

    /**
     * 默认实现
     * @param tokenView
     * @return
     */
    public void finishActivityBySoftInput(View tokenView){

        finishActivityBySoftInput(tokenView, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 自定义实现
     * @param tokenView
     * @param flag
     * @return
     */
	public void finishActivityBySoftInput(View tokenView, int flag){
		
		if(mActivity.isFinishing() || mSoftKeyLocked)
			return;
		
		if(hideSoftInput(tokenView, flag)){
			
			mSoftKeyLocked = true;
			tokenView.postDelayed(new Runnable() {
				@Override
				public void run() {
					
					mSoftKeyLocked = false;
					if(!mActivity.isFinishing())
						mActivity.finish();
				}
			}, HIDE_INPUT_METHOD_MILLIS);
		}else{
			
			mActivity.finish();
		}
	}

    /**
     * 默认实现
     * @param tokenView
     * @return
     */
    public void hideSoftInputPost(View tokenView, final Runnable runnable){

        hideSoftInputPost(tokenView, InputMethodManager.HIDE_NOT_ALWAYS, runnable);
    }

    /**
     * 自定义实现
     * @param tokenView
     * @param flag
     * @return
     */
	public void hideSoftInputPost(View tokenView, int flag, final Runnable runnable){
		
		if(mActivity.isFinishing() || mSoftKeyLocked)
			return;
		
		if(hideSoftInput(tokenView, flag)){
			
			mSoftKeyLocked = true;
			tokenView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					mSoftKeyLocked = false;
					 if(!mActivity.isFinishing())
						 runnable.run();
				}
			}, HIDE_INPUT_METHOD_MILLIS);
			
		}else{
			
			runnable.run();
		}
	}
	
	public boolean isSoftKeyLocked(){
		
		return mSoftKeyLocked;
	}	
}
