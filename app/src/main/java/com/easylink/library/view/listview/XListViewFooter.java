package com.easylink.library.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylink.nj.R;

public class XListViewFooter extends LinearLayout {
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_FAILED = 3;

	private View mContentView, mFlLoadingContent;
	private View mProgressBar;
	private TextView mHintView;
	private Button mBtnReLoadMore;
	private RetryLoadClickListener mRetryClickLisn;
	
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		
		FrameLayout moreView = (FrameLayout)LayoutInflater.from(context).inflate(R.layout.zlibrary_xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mFlLoadingContent = moreView.findViewById(R.id.flLoadingContent);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
		
		mBtnReLoadMore = (Button) moreView.findViewById(R.id.btnReLoadMore);
		mBtnReLoadMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(mRetryClickLisn != null){
					
					boolean result = mRetryClickLisn.onRetryLoadClickListener();
					if(result)
						setState(STATE_LOADING);
				}
			}
		});
	}
	
	public boolean isFailedState(){
		
		return mBtnReLoadMore.getVisibility() == View.VISIBLE;
	}
	public void setState(int state) {
//		mHintView.setVisibility(View.INVISIBLE);
//		mProgressBar.setVisibility(View.INVISIBLE);
//		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			
			if(mFlLoadingContent.getVisibility() != View.VISIBLE){
				mFlLoadingContent.setVisibility(View.VISIBLE);
			}
			
			if(mBtnReLoadMore.getVisibility() != View.INVISIBLE){
				mBtnReLoadMore.setVisibility(View.INVISIBLE);
			}
			
			mProgressBar.setVisibility(View.VISIBLE);
			mHintView.setVisibility(View.VISIBLE);
		} else if (state == STATE_FAILED){
			
			if(mFlLoadingContent.getVisibility() != View.INVISIBLE){
				mFlLoadingContent.setVisibility(View.INVISIBLE);
			}
			
			if(mBtnReLoadMore.getVisibility() != View.VISIBLE){
				mBtnReLoadMore.setVisibility(View.VISIBLE);
			}
			
		} else{
			
			if(mFlLoadingContent.getVisibility() != View.VISIBLE){
				mFlLoadingContent.setVisibility(View.VISIBLE);
			}
			
			if(mBtnReLoadMore.getVisibility() != View.INVISIBLE){
				mBtnReLoadMore.setVisibility(View.INVISIBLE);
			}
			
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	
	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}
	
	
	/**
	 * loading status 
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	
	public void resetHeight(int height){
		
		mBtnReLoadMore.setPadding(0, 0, 0, 0);
		mFlLoadingContent.setPadding(0, 0, 0, 0);
		mFlLoadingContent.getLayoutParams().height = height;
		mContentView.getLayoutParams().height = height;
		mContentView.requestLayout();
	}
	
	public void setRetryLoadClickListener(RetryLoadClickListener lisn){
		
		mRetryClickLisn = lisn;
	}
	
	public interface RetryLoadClickListener{
		
		boolean onRetryLoadClickListener();
	}
	
}
