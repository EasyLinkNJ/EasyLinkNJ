package com.easylink.library.context;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import com.easylink.library.util.ToastUtil;

import java.io.File;

/**
 * 
 * ExApplication
 * @author yhb
 * 
 */
public class ExApplication extends Application {

	private static Context mContext = null;
	private static boolean mHomeKeyPressed;

	@Override
	public void onCreate() {

		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getContext() {

		return mContext;
	}

	public static Resources getExResources() {

		return mContext.getResources();
	}

	public static ContentResolver getExContentResolver() {

		return mContext.getContentResolver();
	}

	public static File getAppCacheDir() {

		return mContext.getCacheDir();
	}

	public static File getAppCacheSubDir(String subDirName) {

		File subDir = new File(getAppCacheDir(), subDirName);
		if (!subDir.exists())
			subDir.mkdirs();

		return subDir;
	}
	
	public static void setHomeKeyPressed(boolean pressed){
		
		mHomeKeyPressed = pressed;
	}
	
	public static boolean isHomeKeyPressed(){
		
		return mHomeKeyPressed;
	}
	
	/**
	 * 释放相关工具的静态对象
	 */
	public static void releaseStaticResource(){
		
		ToastUtil.release();
	}
}
