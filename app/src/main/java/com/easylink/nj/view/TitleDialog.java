package com.easylink.nj.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;


/**
 * 带标题的对话框基类，只实现标题的设置逻辑，不实现标题的布局
 *
 * @author yhb
 */
public abstract class TitleDialog extends BaseDialog {

    private String mTitleText = TextUtil.TEXT_EMPTY;
    private int mTitleColor;

    public TitleDialog(Context context) {

        super(context);
    }

    protected void initTitleView() {

        TextView tv = (TextView) findViewById(R.id.tvTitle);
        if (tv == null)
            return;

        //QaTypeFaceUtil.setHYQiHeiTypeFace(tv);6.1去除汉仪旗黑字体
        if (TextUtil.isEmpty(mTitleText)) {

            tv.setVisibility(View.GONE);
        } else {

            tv.setText(mTitleText);
            if (mTitleColor != 0)
                tv.setTextColor(mTitleColor);
        }
    }

    /**
     * 如果标题文本为空，则不显示标题栏
     *
     * @param rid
     */
    public void setTitleText(int rid) {

        setTitleText(getContext().getString(rid));
    }

    /**
     * 如果标题文本为空，则不显示标题栏
     *
     * @param text
     */
    public void setTitleText(String text) {

        mTitleText = TextUtil.filterNull(text);
    }

    public String getTitleText() {

        return mTitleText;
    }

    public void setTitleColor(int color) {

        mTitleColor = color;
    }
}
