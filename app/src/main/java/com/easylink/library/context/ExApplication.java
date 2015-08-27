package com.easylink.library.context;

import android.app.Application;
import android.content.Context;

import com.easylink.library.util.ToastUtil;

/**
 * ExApplication
 *
 * @author yhb
 */
public class ExApplication extends Application {

    private static Context mContext = null;

    @Override
    public void onCreate() {

        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {

        return mContext;
    }

    /**
     * 释放相关工具的静态对象
     */
    public static void releaseStaticResource() {

        ToastUtil.release();
    }
}
