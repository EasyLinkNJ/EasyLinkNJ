package com.easylink.nj.activity.common;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.httptask.NjJsonListener;

/**
 */
public abstract class NjHttpActivity<T> extends NjActivity{

    private FrameLayout mFlFrame;
    private View mContentView;
    private ImageView mIvTip;
    private ProgressBar mPbLoading;
    private int mTipResId;
    private int mFailedImageResId;
    private int mDisabledImageResId;

    @Override
    public void setContentView(int layoutResId) {

        super.setContentView(inflateFrameView(getLayoutInflater().inflate(layoutResId, null)));
    }

    @Override
    public void setContentView(View view) {

        super.setContentView(inflateFrameView(view));
    }

    /**
     * loadingView, tipView 默认先隐藏
     * @param v
     * @return
     */
    protected View inflateFrameView(View v){

        FrameLayout.LayoutParams fllp = null;
        mFlFrame = new FrameLayout(this);

        //add content view
        mContentView = v;
        fllp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mFlFrame.addView(v, fllp);

        //add tip view
        mIvTip = new ImageView(this);
        mIvTip.setVisibility(View.INVISIBLE);//默认隐藏
        mIvTip.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mIvTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onTipViewClick();
            }
        });
        fllp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mFlFrame.addView(mIvTip, fllp);

        //add progress bar
        mPbLoading = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        mPbLoading.setVisibility(View.INVISIBLE);
        fllp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        fllp.gravity = Gravity.CENTER;
        mFlFrame.addView(mPbLoading, fllp);

        //设置网络错误提示图和为空图
        mFailedImageResId = R.mipmap.ic_launcher;
//        mDisabledImageResId = R.drawable.ic_tip_null;
        return mFlFrame;
    }

    public View getFrameContentView(){

        return mContentView;
    }

    public void executeHttpTaskByUiSwitch(final int what, HttpTaskParams params, Class<?> t){

        executeHttpTask(what, params, new NjJsonListener<T>(t) {

            @Override
            public void onTaskPre() {

                switchLoading(what);
            }

            @Override
            public void onTaskResult(T result) {

                invalidateContent(what, result);
                switchContent(what);
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                switchFailed(what, failedCode, msg);
            }
        });
    }

    public void switchLoading(int what){

        ViewUtil.hideView(mContentView);
        ViewUtil.hideView(mIvTip);
        ViewUtil.showView(mPbLoading);
    }

    public void switchContent(int what){

        ViewUtil.hideView(mPbLoading);
        ViewUtil.hideView(mIvTip);
        ViewUtil.showView(mContentView);
    }

    public abstract void invalidateContent(int what, T t);

    public void switchFailed(int what, int failedCode, String msg){

        ViewUtil.hideView(mPbLoading);
        ViewUtil.hideView(mContentView);
        ViewUtil.showImageView(mIvTip, mFailedImageResId);
    }

    public void switchDisable(int resId) {

        ViewUtil.hideView(mPbLoading);
        ViewUtil.hideView(mContentView);
        ViewUtil.showImageView(mIvTip, resId);
    }

    protected void onTipViewClick(){

    }
}
