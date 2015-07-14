package com.easylink.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.easylink.library.context.ExApplication;


@SuppressLint("NewApi")
public class AppInfoUtil {

	public static String getAppName() { 
	
		try { 
			
			Context ctx = ExApplication.getContext();
			PackageManager packageManager = ctx.getPackageManager(); 
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 0); 
			CharSequence charSequcence = packageManager.getApplicationLabel(applicationInfo); 
			if(charSequcence == null)
				return "";
			else
				return charSequcence.toString(); 
		
		} catch (Exception e) { 
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		} 
		
		return "";
	} 

	public static String getChannelName() {

		try {

			Context ctx = ExApplication.getContext();
			ApplicationInfo appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
			String channelName = appInfo.metaData.getString("UMENG_CHANNEL");
			return channelName == null ? "" : channelName;

		} catch (NameNotFoundException e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}

		return "";
	}
	
	public static String getMetaDataValue(String name){
		
		String value = null;
		try {

			Context ctx = ExApplication.getContext();
			ApplicationInfo appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
			value = appInfo.metaData.getString(name);

		} catch (NameNotFoundException e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}
		
		return value == null ? "" : value;
	}

    /**
     * 获取VersionCode，默认返回1
     * @return
     */
	public static int getVersionCode() {

		try {

			Context ctx = ExApplication.getContext();
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			return packInfo.versionCode;

		} catch (NameNotFoundException e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}

		return 1;
	}

	public static String getVersionName() {

		try {

			Context ctx = ExApplication.getContext();
			PackageInfo packInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			return packInfo.versionName == null ? "" : packInfo.versionName;

		} catch (Exception e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}

		return "";
	}

	/**
	 * 获取应用第一次安装时间
	 * 
	 * @return
	 */
	public static long getInstallAppTime() {

		long firstInstallTime = 0;

		try {
			
			Context ctx = ExApplication.getContext();
			PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			firstInstallTime = packageInfo.firstInstallTime;// 应用第一次安装的时间
			
		} catch (Exception e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}

		return firstInstallTime;
	}
}
