package com.easylink.library.util;

import android.util.Log;

public class LogMgr {

	private static String mMainTag = "ExLogMgr";
	private static boolean mIsDebug = true;

	public static boolean isDebug() {
		
		return mIsDebug;
	}
	
	public static void turnOn(){
		
		mIsDebug = true;
	}
	
	public static void turnOff(){
		
		mIsDebug = false;
	}
	
	public static void setMainTag(String tagName) {
		
		mMainTag = tagName;
	}

	public static String getMainTag() {
		
		return mMainTag;
	}
	
	public static void v(String log){
		
		v(mMainTag, log);
	}

	public static void v(String tag, String log) {
		
		if (mIsDebug)
			Log.v(tag, log);
	}

	public static void d(String log){
		
		d(mMainTag, log);
	}
	
	public static void d(String tag, String log) {
		
		if (mIsDebug)
			Log.d(tag, log);
	}

	public static void i(String log) {
		
		 i(mMainTag, log);
	}
	
	public static void i(String tag, String log) {
		
		if (mIsDebug)
			Log.i(tag, log);
	}

	public static void w(String log) {
		
		 w(mMainTag, log);
	}
	
	public static void w(String tag, String log) {
		
		if (mIsDebug)
			Log.w(tag, log);
	}

	public static void e(String log) {
		
		e(mMainTag, log);
	}
	
	public static void e(String tag, String log) {
		
		if (mIsDebug)
			Log.e(tag, log);
	}
}
