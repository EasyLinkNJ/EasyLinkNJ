package com.easylink.nj.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;


/**
 * 提示对话框，不带标题栏，带一个确定按钮。
 * 虽然继承titleDialog，但是没有实现title的布局。
 *
 * @author yhb
 */
public class AlertDialog extends TitleDialog {

    private String mContentText = TextUtil.TEXT_EMPTY;
    private String mConfirmText = TextUtil.TEXT_EMPTY;
    private OnViewClickListener mOnViewClickLisn;

    public AlertDialog(Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.dialog_alert);
    }

    @Override
    protected void initContentView() {

        TextView tv = (TextView) findViewById(R.id.tvText);
        if (tv != null) {

            tv.setText(mContentText);
        }

        tv = (TextView) findViewById(R.id.tvConfirm);
        if (tv != null) {

            tv.setText(mConfirmText);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnViewClickLisn != null)
                        mOnViewClickLisn.onViewClick(AlertDialog.this, v);
                }
            });
        }
    }

    public void setConfirmText(String text) {

        mConfirmText = TextUtil.filterNull(text);
    }

    public void setConfirmText(int resId) {

        mConfirmText = getContext().getString(resId);
    }

    public void setContentText(String text) {

        mContentText = TextUtil.filterNull(text);
    }

    public void setContentText(int resId) {

        mContentText = getContext().getString(resId);
    }

    public void setOnConfirmViewClickListener(OnViewClickListener lisn) {

        mOnViewClickLisn = lisn;
    }
}
