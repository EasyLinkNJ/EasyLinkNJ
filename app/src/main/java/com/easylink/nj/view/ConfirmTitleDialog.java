package com.easylink.nj.view;

import android.content.Context;
import android.os.Bundle;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;


/**
 * 确认、取消对话框，带标题栏，带一个确定按钮和取消按钮。
 *
 * @author yhb
 */
public class ConfirmTitleDialog extends AlertTitleDialog {

    private String mCancelText = TextUtil.TEXT_EMPTY;
//    private OnViewClickListener mOnCancelViewClickLisn;

    public ConfirmTitleDialog(Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.dialog_confirm_title);
    }

    @Override
    protected void initContentView() {

        super.initContentView();//调用父类初始化confirm btn

//        TextView tv = (TextView) findViewById(R.id.tvCancel);
//        if (tv != null) {
//
//            tv.setText(mCancelText);
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (mOnCancelViewClickLisn != null)
//                        mOnCancelViewClickLisn.onViewClick(ConfirmTitleDialog.this, v);
//                }
//            });
//        }
    }

    public void setCancelText(String text) {

        mCancelText = TextUtil.filterNull(text);
    }

    public void setCancelText(int resId) {

        mCancelText = getContext().getString(resId);
    }

//    public void setOnCancelViewClickListener(OnViewClickListener lisn) {
//
//        mOnCancelViewClickLisn = lisn;
//    }
}
