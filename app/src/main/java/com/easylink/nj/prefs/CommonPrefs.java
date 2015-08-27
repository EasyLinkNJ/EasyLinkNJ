package com.easylink.nj.prefs;

import android.content.Context;

import com.easylink.library.util.TextUtil;

public class CommonPrefs {

    private final String KEY_USER_TOKEN = "user_token";

    private SharedPrefs mExSharedPrefs;
    private CommonPrefs(Context context) {

        mExSharedPrefs = new SharedPrefs(context, "suanduoyi_app_android");
    }

    private static CommonPrefs mSettingPrefs;
    public static CommonPrefs getInstance(Context context) {

        if (mSettingPrefs == null)
            mSettingPrefs = new CommonPrefs(context);

        return mSettingPrefs;
    }

    public static void releaseInstance() {

        if (mSettingPrefs != null)
            mSettingPrefs = null;
    }

    public void setUserToken(String userToken) {

        mExSharedPrefs.putString(KEY_USER_TOKEN, userToken);
    }

    public String getUserToken() {

        return mExSharedPrefs.getString(KEY_USER_TOKEN);
    }

    public boolean hasUserToken() {

        return !TextUtil.isEmptyTrim(getUserToken());
    }
}
