package com.easylink.nj.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.easylink.nj.R;

/**
 * 对话框的基类
 *
 * @author yhb
 */
public abstract class BaseDialog extends Dialog {

    private Object mTagObj;

    public BaseDialog(Context context) {

        super(context, R.style.qa_ex_theme_dialog);
    }

    public BaseDialog(Context context, int style) {
        super(context, style);
    }

    @Override
    public void setContentView(int layoutResID) {

        resetContentView(layoutResID);
        initContentView();
    }

    private void resetContentView(int layoutResID) {

        FrameLayout fl = new FrameLayout(getContext());
        View v = new View(getContext());
        fl.addView(v, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0));// 创建一个空view，用来水平撑开对话框
        fl.addView(getLayoutInflater().inflate(layoutResID, null), new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        setContentView(fl, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    protected abstract void initContentView();

    public void setTag(Object tagObj) {

        mTagObj = tagObj;
    }

    public Object getTag() {

        return mTagObj;
    }

    public static interface OnViewClickListener {

        public void onViewClick(BaseDialog dialog, View v);
    }
}
