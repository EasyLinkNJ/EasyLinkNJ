package com.easylink.nj.view;

import android.content.Context;
import android.os.Bundle;

import com.easylink.nj.R;


/**
 * 提示对话框，不带标题栏，带一个确定按钮。
 * 实现了title的布局。
 *
 * @author yhb
 */
public class AlertTitleDialog extends AlertDialog {

    public AlertTitleDialog(Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.dialog_alert_title);
    }

    @Override
    protected void initContentView() {

        initTitleView();
        super.initContentView();
    }
}
