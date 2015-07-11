package com.easylink.library.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easylink.library.context.ExApplication;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.LogMgr;
import com.easylink.library.util.ToastUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.ExDecorView;

/**
 * 根据Ex框架，扩展的基类FragmentActivity
 * @author yhb
 */
public abstract class ExFragmentActivity extends FragmentActivity{

	private ExDecorView mExDecorView;
	private HttpTaskExecuter mHttpTaskExecuter;
	private Fragment mCurrentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		initAndSetExDecorView();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		//super.onSaveInstanceState(outState);
		//fragmentActivity 靠该方法保存状态:fragment的复用等，取消状态的保存
	}
	
	private void initAndSetExDecorView(){
		
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

	private void setContentViewToExDecorView(View contentView){
		
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

	@Override
	protected void onResume() {
		
		super.onResume();
	}

	/*
	 * http task api 
	 */
	
	protected boolean executeHttpTask(int what, HttpTaskParams params, HttpTaskStringListener<?> lisn){
		
		return executeHttpTask(what, params, false, lisn);
	}
	
	protected boolean executeHttpTaskCache(int what, HttpTaskParams params, HttpTaskStringListener<?> lisn){
		
		return executeHttpTask(what, params, true, lisn);
	}
	
	private boolean executeHttpTask(int what, HttpTaskParams params, boolean cacheOnly, HttpTaskStringListener<?> lisn){
		
		if (isFinishing())
			return false;
		
		if (mHttpTaskExecuter == null)
			mHttpTaskExecuter = new HttpTaskExecuter();
		
		return mHttpTaskExecuter.executeHttpTask(what, params, cacheOnly, lisn);
	}
	
	protected boolean isHttpTaskRunning(int what) {

		return mHttpTaskExecuter == null ? false : mHttpTaskExecuter.isHttpTaskRunning(what);
	}
	
	protected void abortHttpTask(int what) {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortHttpTask(what);
	}

	protected void abortAllHttpTask() {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortAllHttpTask();
	}

    /*
     *  status bar api
     */
    protected void setStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(color);
    }

    protected void setStatusBarTranslucent(boolean translucent){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

            if(translucent){

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }else{

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    protected int getStatusBarHeight(){

        return DeviceUtil.getStatusBarHeight();
    }

	/*
	 * get decor view part
	 */
	
	protected RelativeLayout getExDecorView(){
		
		return mExDecorView;
	}
	
	protected LinearLayout getTitleView(){
		
		return mExDecorView.getTitleView();
	}
	
	protected LinearLayout getTitleLeftView(){
		
		return mExDecorView.getTitleLeftView();
	}
	
	protected LinearLayout getTitleMiddleView(){
		
		return mExDecorView.getTitleMiddleView();
	}
	
	protected LinearLayout getTitleRightView(){
		
		return mExDecorView.getTitleRightView();
	}	
	
	protected void setTitleViewBackgroundAlpha(int alpha){
		
		mExDecorView.setTitleViewBackgroundAlpha(alpha);
	}
	
	protected void setTitleViewBackground(int resId){
		
		mExDecorView.setTitleViewBackground(resId);
	}
	
	public void setTitleViewBackground(Drawable drawable){
		
		mExDecorView.setTitleViewBackground(drawable);
	}
	
	/*
	 * add title view left part
	 */
	
	protected ImageView addTitleLeftImageView(int icResId, OnClickListener lisn){
		
		return mExDecorView.addTitleLeftImageView(icResId, lisn);
	}

	protected ImageView addTitleLeftImageViewHoriWrap(int icResId, OnClickListener lisn){
		
		return mExDecorView.addTitleLeftImageViewHoriWrap(icResId, lisn);
	}
	
	protected TextView addTitleLeftTextView(int textRid, OnClickListener lisn){
		
		return mExDecorView.addTitleLeftTextView(textRid, lisn);
	}
	
	protected TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn){
		
		return mExDecorView.addTitleLeftTextView(text, lisn);
	}
	
	protected void addTitleLeftView(View v){
		
		mExDecorView.addTitleLeftView(v);
	}
	
	protected void addTitleLeftView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleLeftView(v, lllp);
	}
	
	protected ImageView addTitleLeftBackView() {

		return mExDecorView.addTitleLeftImageViewBack(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});
	}

	protected ImageView addTitleLeftBackView(OnClickListener clickLisn) {

		return mExDecorView.addTitleLeftImageViewBack(clickLisn);
	}	
	
	/*
	 * add title view middle part
	 */
	
	protected ImageView addTitleMiddleImageViewWithBack(int icResId){
		
		addTitleLeftBackView();
		return addTitleMiddleImageView(icResId);
	}
	
	protected ImageView addTitleMiddleImageView(int icResId){
		
		return mExDecorView.addTitleMiddleImageView(icResId);
	}

	protected ImageView addTitleMiddleImageViewHoriWrapWithBack(int icResId){
		
		addTitleLeftBackView();
		return addTitleMiddleImageViewHoriWrap(icResId);
	}
	
	protected ImageView addTitleMiddleImageViewHoriWrap(int icResId){
		
		return mExDecorView.addTitleMiddleImageViewHoriWrap(icResId);
	}
	
	protected TextView addTitleMiddleTextViewWithBack(int textRid){
		
		return addTitleMiddleTextViewWithBack(getResources().getText(textRid));
	}
	
	protected TextView addTitleMiddleTextView(int textRid){
		
		return mExDecorView.addTitleMiddleTextView(textRid);
	}
	
	protected TextView addTitleMiddleTextView(CharSequence text){
		
		return mExDecorView.addTitleMiddleTextView(text);
	}
	
	protected TextView addTitleMiddleTextViewWithBack(CharSequence text){
		
		addTitleLeftBackView();
		return mExDecorView.addTitleMiddleTextView(text);
	}
	
	protected TextView addTitleMiddleTextViewMainStyle(int textResId){
		
		return mExDecorView.addTitleMiddleTextViewMainStyle(textResId);
	}
	
	protected TextView addTitleMiddleTextViewMainStyle(CharSequence text){
		
		return mExDecorView.addTitleMiddleTextViewMainStyle(text);
	}
	
	protected TextView addTitleMiddleTextViewSubStyle(int textResId){
		
		return mExDecorView.addTitleMiddleTextViewSubStyle(textResId);
	}
	
	protected TextView addTitleMiddleTextViewSubStyle(CharSequence text){
		
		return mExDecorView.addTitleMiddleTextViewSubStyle(text);
	}	
	
	protected void addTitleMiddleViewWithBack(View v){
		
		addTitleLeftBackView();
		addTitleMiddleView(v);
	}
	
	protected void addTitleMiddleView(View v){
		
		mExDecorView.addTitleMiddleView(v);
	}
	
	protected void addTitleMiddleView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleMiddleView(v, lllp);
	}
	
	/*
	 * add title view right part
	 */
	
	protected ImageView addTitleRightImageView(int icResId, OnClickListener lisn){
		
		return mExDecorView.addTitleRightImageView(icResId, lisn);
	}
	
	protected ImageView addTitleRightImageViewHoriWrap(int icResId, OnClickListener lisn){
		
		return mExDecorView.addTitleRightImageViewHoriWrap(icResId, lisn);
	}	
	
	protected TextView addTitleRightTextView(int textRid, OnClickListener lisn){
		
		return mExDecorView.addTitleRightTextView(textRid, lisn);
	}
	
	protected TextView addTitleRightTextView(CharSequence text, OnClickListener lisn){
		
		return mExDecorView.addTitleRightTextView(text, lisn);
	}	
	
	protected void addTitleRightView(View v){
		
		mExDecorView.addTitleRightView(v);
	}
	
	protected void addTitleRightView(View v, LinearLayout.LayoutParams lllp) {

		mExDecorView.addTitleRightView(v, lllp);
	}	
	
	/*
	 * fragment activity part 
	 */
	
	protected void addFragment(int frameId, Fragment f){
		
		if(f == null)
			return;
		
		getSupportFragmentManager().beginTransaction().add(frameId, f).commitAllowingStateLoss();
	}
	
	protected void addFragment(int frameId, Fragment f, String tag){
		
		if(f == null)
			return;
		
		getSupportFragmentManager().beginTransaction().add(frameId, f, tag).commitAllowingStateLoss();
	}
	
	protected Fragment findFragmentByTag(String tag){
		
		if(tag == null)
			return null;
		
		return getSupportFragmentManager().findFragmentByTag(tag);
	}
	
	protected Fragment findFragmentById(int id){
		
		return getSupportFragmentManager().findFragmentById(id);
	}
	
	protected void replaceFragment(int frameId, Fragment f){
		
		if(f == null)
			return;
		
		getSupportFragmentManager().beginTransaction().replace(frameId, f);
	}
	
	protected void switchFragment(Fragment f){
		
		if(f == null)
			return;
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.show(f);
		if(mCurrentFragment != null){
			transaction.hide(mCurrentFragment);
		}
		transaction.commitAllowingStateLoss();
		mCurrentFragment = f;
	}
	
	/*
	 * receiver
	 */
	
	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
		
		try{
			return super.registerReceiver(receiver, filter);
		}catch(Exception e){
			
			if(LogMgr.isDebug())
				LogMgr.d("~~registerReceiver error, msg="+e.getMessage());
		}
		return null;
	}
	
	@Override
	public void unregisterReceiver(BroadcastReceiver receiver) {
		
		try{
			super.unregisterReceiver(receiver);
		}catch(Exception e){
			
			if(LogMgr.isDebug())
				LogMgr.d("~~unregisterReceiver error, msg="+e.getMessage());
		}
	}
	
	/*
	 * toast part
	 */
	
	protected void showToast(int rid) {

	    ToastUtil.showToast(rid);
	}

	protected void showToast(String text) {

	    ToastUtil.showToast(text);
	}
	
	/*
	 * view util part	
	 */
	
	protected void showView(View v){
		
		ViewUtil.showView(v);
	}
	
	protected void hideView(View v){
		
		ViewUtil.hideView(v);
	}
	
	protected void goneView(View v){
		
		ViewUtil.goneView(v);
	}
	
	protected void showImageView(ImageView v, int imageResId){
		
		ViewUtil.showImageView(v, imageResId);
	}
	
	protected void showImageView(ImageView v, Drawable drawable){
		
		ViewUtil.showImageView(v, drawable);
	}
	
	protected void hideImageView(ImageView v){
		
		ViewUtil.hideImageView(v);
	}
	
	protected void goneImageView(ImageView v){
		
		ViewUtil.goneImageView(v);
	}
	
	/*
	 *  tag part
	 */
	
	public String simpleTag(){
		
		return getClass().getSimpleName();
	}
	
	public String tag(){
		
		return getClass().getName();
	}
}
