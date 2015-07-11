package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.easylink.library.activity.ExActivity;
import com.easylink.nj.R;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class MainActivity extends ExActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextView("我是主页");
    }

    @Override
    protected void initContentView() {

    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}