package com.easylink.nj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.easylink.nj.R;
import com.easylink.nj.activity.main.MainActivity;

/**
 * 闪屏页面
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        delayStartMainActivity();
    }

    @SuppressLint("HandlerLeak")
    private void delayStartMainActivity() {

        new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (!isFinishing()) {

                    finishToEnterActivity();
                }
            }

            ;

        }.sendEmptyMessageDelayed(0, 2000);
    }

    private void finishToEnterActivity() {

        MainActivity.startActivity(this);
        finish();
    }
}
