package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.activity.ExActivity;
import com.easylink.nj.R;
import com.easylink.nj.activity.news.NewsListActivity;
import com.easylink.nj.activity.product.ProductListActivity;

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

        findViewById(R.id.tvNewsList).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                NewsListActivity.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.tvProductList).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ProductListActivity.startActivity(MainActivity.this);
            }
        });
    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
