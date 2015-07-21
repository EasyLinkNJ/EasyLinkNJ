package com.easylink.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yihaibin on 15/7/22.
 */
public class ExViewPager extends ViewPager{

    private boolean mCanScroll = true;

    public ExViewPager(Context context) {

        super(context);
    }

    public ExViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return mCanScroll ? super.onInterceptTouchEvent(ev) : false;
    }

    public void setCanScroll(boolean canScroll){

        mCanScroll = canScroll;
    }
}
