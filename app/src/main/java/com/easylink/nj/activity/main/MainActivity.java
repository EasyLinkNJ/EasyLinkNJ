package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.activity.ExActivity;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.task.HttpTask;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.LogMgr;
import com.easylink.nj.R;
import com.easylink.nj.activity.news.NewsActivity;
import com.easylink.nj.httptask.NjHttpUtil;

import org.json.JSONObject;

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

        findViewById(R.id.tvGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewsActivity.startActivity(MainActivity.this);
            }
        });
    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
