package com.easylink.library.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.LogMgr;
import com.easylink.library.util.ToastUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.ExDecorView;

/**
 * 根据Ex框架，扩展的基类Activity，提供titlebar、toast、view，httptask 相关常用的api
 * @author yhb
 */
public abstract class ExActivity extends Activity {

	private ExDecorView mExDecorView;
	private HttpTaskExecuter mHttpTaskExecuter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initAndSetExDecorView();
	}

	private void initAndSetExDecorView() {

		mExDecorView = new ExDecorView(this);
		super.setContentView(mExDecorView);
	}

	@Override
	public void setContentView(int layoutResId) {

		setContentViewToExDecorView(getLayoutInflater().inflate(layoutResId, null));
	}

	@Override
	public void setContentView(View view) {

		setContentViewToExDecorView(view);
	}

	private void setContentViewToExDecorView(View contentView) {

		mExDecorView.setContentView(contentView);
		initData();
		initTitleView();
		initContentView();
	}

	protected abstract void initData();

	protected abstract void initTitleView();

	protected abstract void initContentView();

	@Override
	protected void onPause() {

		super.onPause();
		if (isFinishing())
			abortAllHttpTask();
	}

	/*
	 * http task api
	 */

	public boolean executeHttpTask(int what, HttpTaskParams params, HttpTaskStringListener<?> lisn) {

		return executeHttpTask(what, params, false, lisn);
	}

	public boolean executeHttpTaskCache(int what, HttpTaskParams params, HttpTaskStringListener<?> lisn) {

		return executeHttpTask(what, params, true, lisn);
	}

	private boolean executeHttpTask(int what, HttpTaskParams params, boolean cacheOnly, HttpTaskStringListener<?> lisn) {

		if (isFinishing())
			return false;

		if (mHttpTaskExecuter == null)
			mHttpTaskExecuter = new HttpTaskExecuter();

		return mHttpTaskExecuter.executeHttpTask(what, params, cacheOnly, lisn);
	}

	public boolean isHttpTaskRunning(int what) {

		return mHttpTaskExecuter == null ? false : mHttpTaskExecuter.isHttpTaskRunning(what);
	}

	public void abortHttpTask(int what) {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortHttpTask(what);
	}

	public void abortAllHttpTask() {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortAllHttpTask();
	}

    /*
     *  status bar api
     */
    public void setStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(color);
    }

    public void setStatusBarColorResource(int resColor){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(resColor));
    }

    public boolean setStatusBarTranslucent(boolean translucent, boolean kitkatEnable){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return false;

        try{

            //umeng日志中，在某些机型上，addFlags函数会报
            // java.lang.SecurityException: No permission to prevent power key:
            // Neither user 10069 nor current process has android.permission.PREVENT_POWER_KEY
            //所以这里做了简单的try catch 处理
            if (kitkatEnable || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                if(translucent){

                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }else{

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }

                return true;
            }

        }catch(Exception e){

            if(LogMgr.isDebug())
                e.printStackTrace();
        }

        return false;
    }

	public int getStatusBarHeight(){

        return DeviceUtil.getStatusBarHeight();
    }

	/*
	 * get decor view part
	 */

	public ExDecorView getExDecorView() {

		return mExDecorView;
	}

	public LinearLayout getTitleView() {

		return mExDecorView.getTitleView();
	}

    // Created by Daisw on 15/4/10.
    public RelativeLayout.LayoutParams getTitleViewLayoutParams() {

        return (RelativeLayout.LayoutParams) getTitleView().getLayoutParams();
    }

	public LinearLayout getTitleLeftView() {

		return mExDecorView.getTitleLeftView();
	}

	public LinearLayout getTitleMiddleView() {

		return mExDecorView.getTitleMiddleView();
	}

	public LinearLayout getTitleRightView() {

		return mExDecorView.getTitleRightView();
	}

    /*
     * set decor view part
     */
	public void setTitleViewBackgroundAlpha(int alpha) {

		mExDecorView.setTitleViewBackgroundAlpha(alpha);
	}

	public void setTitleViewBackground(int resId) {

		mExDecorView.setTitleViewBackground(resId);
	}

	public void setTitleViewBackground(Drawable drawable) {

		mExDecorView.setTitleViewBackground(drawable);
	}

	public void setTitleViewBackgroundColor(int color) {

        mExDecorView.setTitleViewBackgroundColor(color);
    }

	/*
	 * add title view left part
	 */

	public ImageView addTitleLeftImageView(int icResId, OnClickListener lisn) {

		return mExDecorView.addTitleLeftImageView(icResId, lisn);
	}

	public ImageView addTitleLeftImageViewHoriWrap(int icResId, OnClickListener lisn) {

		return mExDecorView.addTitleLeftImageViewHoriWrap(icResId, lisn);
	}

	public TextView addTitleLeftTextView(int textRid, OnClickListener lisn) {

		return mExDecorView.addTitleLeftTextView(textRid, lisn);
	}

	public TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn) {

		return mExDecorView.addTitleLeftTextView(text, lisn);
	}

	public void addTitleLeftView(View v) {

		mExDecorView.addTitleLeftView(v);
	}

	public void addTitleLeftView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleLeftView(v, lllp);
	}

	public ImageView addTitleLeftBackView() {

		return mExDecorView.addTitleLeftImageViewBack(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

	public ImageView addTitleLeftBackView(OnClickListener clickLisn) {

		return mExDecorView.addTitleLeftImageViewBack(clickLisn);
	}

	/*
	 * add title view middle part
	 */

	public ImageView addTitleMiddleImageViewWithBack(int icResId) {

		addTitleLeftBackView();
		return addTitleMiddleImageView(icResId);
	}

	public ImageView addTitleMiddleImageView(int icResId) {

		return mExDecorView.addTitleMiddleImageView(icResId);
	}

	public ImageView addTitleMiddleImageViewHoriWrapWithBack(int icResId) {

		addTitleLeftBackView();
		return addTitleMiddleImageViewHoriWrap(icResId);
	}

	public ImageView addTitleMiddleImageViewHoriWrap(int icResId) {

		return mExDecorView.addTitleMiddleImageViewHoriWrap(icResId);
	}

	public TextView addTitleMiddleTextViewWithBack(int textRid) {

		return addTitleMiddleTextViewWithBack(getResources().getText(textRid));
	}

	public TextView addTitleMiddleTextView(int textRid) {

		return mExDecorView.addTitleMiddleTextView(textRid);
	}

	public TextView addTitleMiddleTextView(CharSequence text) {

		return mExDecorView.addTitleMiddleTextView(text);
	}

	public TextView addTitleMiddleTextViewWithBack(CharSequence text) {

		addTitleLeftBackView();
		return mExDecorView.addTitleMiddleTextView(text);
	}

	public TextView addTitleMiddleTextViewMainStyle(int textResId) {

		return mExDecorView.addTitleMiddleTextViewMainStyle(textResId);
	}

	public TextView addTitleMiddleTextViewMainStyle(CharSequence text) {

		return mExDecorView.addTitleMiddleTextViewMainStyle(text);
	}

	public TextView addTitleMiddleTextViewSubStyle(int textResId) {

		return mExDecorView.addTitleMiddleTextViewSubStyle(textResId);
	}

	public TextView addTitleMiddleTextViewSubStyle(CharSequence text) {

		return mExDecorView.addTitleMiddleTextViewSubStyle(text);
	}

	public void addTitleMiddleViewWithBack(View v) {

		addTitleLeftBackView();
		addTitleMiddleView(v);
	}

	public void addTitleMiddleView(View v) {

		mExDecorView.addTitleMiddleView(v);
	}

	public void addTitleMiddleView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleMiddleView(v, lllp);
	}

	/*
	 * add title view right part
	 */

	public ImageView addTitleRightImageView(int icResId, OnClickListener lisn) {

		return mExDecorView.addTitleRightImageView(icResId, lisn);
	}

	public ImageView addTitleRightImageViewHoriWrap(int icResId, OnClickListener lisn) {

		return mExDecorView.addTitleRightImageViewHoriWrap(icResId, lisn);
	}

	public TextView addTitleRightTextView(int textRid, OnClickListener lisn) {

		return mExDecorView.addTitleRightTextView(textRid, lisn);
	}

	public TextView addTitleRightTextView(CharSequence text, OnClickListener lisn) {

		return mExDecorView.addTitleRightTextView(text, lisn);
	}

	public void addTitleRightView(View v) {

		mExDecorView.addTitleRightView(v);
	}

	public void addTitleRightView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleRightView(v, lllp);
	}

	/*
	 * receiver
	 */

	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {

		try {
			return super.registerReceiver(receiver, filter);
		} catch (Exception e) {

			if (LogMgr.isDebug())
				LogMgr.e(simpleTag(), "registerReceiver error, msg=" + e.getMessage());
		}
		return null;
	}

	@Override
	public void unregisterReceiver(BroadcastReceiver receiver) {

		try {
			super.unregisterReceiver(receiver);
		} catch (Exception e) {

			if (LogMgr.isDebug())
				LogMgr.e(simpleTag(), "unregisterReceiver error, msg=" + e.getMessage());
		}
	}

	/*
	 * toast part
	 */

	public void showToast(int rid) {

        ToastUtil.showToast(rid);
	}

	public void showToast(String text) {

	    ToastUtil.showToast(text);
	}

	/*
	 * view util part
	 */

	public void showView(View v) {

		ViewUtil.showView(v);
	}

	public void hideView(View v) {

		ViewUtil.hideView(v);
	}

	public void goneView(View v) {

		ViewUtil.goneView(v);
	}

	public void showImageView(ImageView v, int imageResId) {

		ViewUtil.showImageView(v, imageResId);
	}

	public void showImageView(ImageView v, Drawable drawable) {

		ViewUtil.showImageView(v, drawable);
	}

	public void hideImageView(ImageView v) {

		ViewUtil.hideImageView(v);
	}

	public void goneImageView(ImageView v) {

		ViewUtil.goneImageView(v);
	}

	/*
	 * tag part
	 */

	public String simpleTag() {

		return getClass().getSimpleName();
	}

	public String tag() {

		return getClass().getName();
	}
}
