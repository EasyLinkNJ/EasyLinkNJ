package com.easylink.nj.activity.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
    private Dialog mLoadingDialog;
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

        mFlFrame = new FrameLayout(this);

        mContentView = v;
        mFlFrame.addView(v, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

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
        mFlFrame.addView(mIvTip, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        //设置网络错误提示图和为空图
        mFailedImageResId = R.mipmap.ic_launcher;
//        mDisabledImageResId = R.drawable.ic_tip_null;
        return mFlFrame;
    }

    public void showLoadingDialog(){

        if(mLoadingDialog == null){

            ProgressDialog pd = new ProgressDialog(this);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(true);
            pd.setMessage(getResources().getString(R.string.loading_wait));
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                    dialog.dismiss();
                    finish();
                }
            });

            mLoadingDialog = pd;
        }

        if(!mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    public void dismissLoadingDialog(){

        if(mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    public void executeHttpTaskByUiSwitch(final int what, HttpTaskParams params, Class<?> t){

        executeHttpTask(what, params, new NjJsonListener<T>(t) {

            @Override
            public void onTaskPre() {

                switchLoading(what);
            }

            @Override
            public void onTaskResult(T result) {

                switchContent(what, result);
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
        showLoadingDialog();
    }

    public void switchContent(int what, T t){

        dismissLoadingDialog();
        ViewUtil.hideView(mIvTip);
        ViewUtil.showView(mContentView);
        invalidateContent(what, t);
    }

    public abstract void invalidateContent(int what, T t);

    public void switchFailed(int what, int failedCode, String msg){

        dismissLoadingDialog();
        ViewUtil.hideView(mContentView);
        ViewUtil.showImageView(mIvTip, mFailedImageResId);
    }

    protected void onTipViewClick(){

    }
}
