package com.easylink.library.util;

import android.widget.Toast;

import com.easylink.library.context.ExApplication;

public class ToastUtil {

	private static Toast mToast;
	
	private static void initToast(){
		
		if(mToast == null)
			mToast = Toast.makeText(ExApplication.getContext(), "", Toast.LENGTH_SHORT);
	}
	
    public static void showToast(int rid) {

        try{

            initToast();
            mToast.setText(rid);
            mToast.show();

        }catch (Throwable t){

            if(LogMgr.isDebug())
                t.printStackTrace();
        }
    }
    
    public static void showToast(String text) {

        try{

            initToast();
            mToast.setText(text);
            mToast.show();

        }catch (Throwable t){

            if(LogMgr.isDebug())
                t.printStackTrace();
        }
    }
    
    public static void showToast(int rid, Object... args){

        showToast(ExApplication.getContext().getResources().getString(rid, args));
    }
    
    public static void release(){
    	
    	mToast = null;
    }
}
