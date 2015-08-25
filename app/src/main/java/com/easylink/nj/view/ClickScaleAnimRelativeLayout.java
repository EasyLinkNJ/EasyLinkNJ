package com.easylink.nj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class ClickScaleAnimRelativeLayout extends RelativeLayout{
	
	private Animation mDownAnim, mUpAnim;
	
	public ClickScaleAnimRelativeLayout(Context context) {
		super(context);
	}
	
	public ClickScaleAnimRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(!isClickable())
			return super.onTouchEvent(event);
		
		if(getWidth() == 0 && getHeight() == 0)
			return super.onTouchEvent(event);
		
		if(mDownAnim == null && mUpAnim == null)
			initClickAnim();
		
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				startAnimation(mDownAnim);
				break;
			case MotionEvent.ACTION_UP:
				startAnimation(mUpAnim);
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			default:
				startAnimation(mUpAnim);
				break;
		}
		
		return super.onTouchEvent(event);
	}
	
	private void initClickAnim(){
		
		mDownAnim = new ScaleAnimation(1f, 0.95f, 1, 0.95f, getWidth()/2f, getHeight()/2f);
		mDownAnim.setDuration(50);
		mDownAnim.setFillAfter(true);
		mUpAnim = new ScaleAnimation(0.95f, 1f, 0.95f, 1f, getWidth()/2f, getHeight()/2f);
		mUpAnim.setDuration(50);
	}
}
