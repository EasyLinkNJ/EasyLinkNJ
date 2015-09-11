package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.easylink.library.util.AppInfoUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;

/**
 * Created by yihaibin on 15/9/6.
 */
public class AboutActivity extends NjActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_about);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("关于");
    }

    @Override
    protected void initContentView() {

        TextView tv = (TextView) findViewById(R.id.tvCopyRight);
        tv.setText(AppInfoUtil.getAppName()+" Android版 版本" + AppInfoUtil.getVersionName());
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, AboutActivity.class);
        activity.startActivity(intent);
    }
}
