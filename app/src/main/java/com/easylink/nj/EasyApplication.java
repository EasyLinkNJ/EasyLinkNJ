package com.easylink.nj;

import com.activeandroid.ActiveAndroid;
import com.easylink.library.context.ExApplication;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class EasyApplication extends ExApplication {

    @Override
    public void onCreate() {

        super.onCreate();
        initApplication();
    }

    private void initApplication() {

        Fresco.initialize(this);
        ActiveAndroid.initialize(this, BuildConfig.DEBUG);
    }
}
