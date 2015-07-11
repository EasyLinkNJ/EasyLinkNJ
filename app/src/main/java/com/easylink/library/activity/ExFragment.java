package com.easylink.library.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.ToastUtil;
import com.easylink.library.util.ViewUtil;

/**
 * 
 * 根据Ex框架，扩展的基类Activity，提供titlebar、toast、view，httptask 相关常用的api
 * #暂时将ExDecorView去掉，因为其实没啥用
 * @author yhb
 * 
 */
public abstract class ExFragment extends Fragment{

	private FrameLayout mFrameView;
	private HttpTaskExecuter mHttpTaskExecuter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mFrameView = onCreateFragmentFrameView();
		return mFrameView;
	}
	
	protected FrameLayout onCreateFragmentFrameView(){
		
		return new FrameLayout(getActivity());
	}
	
	protected void setFragmentContentView(int layoutResId){

		setFragmentContentView(getActivity().getLayoutInflater().inflate(layoutResId, null));
	}
	
	protected void setFragmentContentView(View view){

		mFrameView.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
															  FrameLayout.LayoutParams.MATCH_PARENT));
		initData();
		initContentView();
	}
	
	protected abstract void initData();
	protected abstract void initContentView();
	
	@Override
	public void onPause() {
		
		super.onPause();
		if (isActivityFinishing())
			abortAllHttpTask();
	}
	
	/**
	 * 当Fragment在ViewPager中，选中状态的回调。
	 * 这个方法什么时候去调用，自己去实现
	 * @param selected
	 */
	public void onViewPageSelectChanged(boolean selected){
		
	}

	protected View findViewById(int id){

		return mFrameView.findViewById(id);
	}

	protected FrameLayout getFragmentFrameView(){

		return mFrameView;
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
		
		if (isActivityFinishing())
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
	
	protected boolean isActivityFinishing(){
		
		return getActivity() == null || getActivity().isFinishing();
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
