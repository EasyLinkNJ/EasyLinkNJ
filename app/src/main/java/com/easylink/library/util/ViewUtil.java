package com.easylink.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.easylink.library.context.ExApplication;

import java.lang.reflect.Field;

public class ViewUtil {

	/**
	 * 获取XListView，该ListView对公共的样式做了清除
	 * @param context
	 * @return
	 */
//	public static XListView getCleanXListView(Context context, int id){
//
//		XListView xlv = new XListView(context);
//		xlv.setId(id);
//		xlv.setDividerHeight(0);
//		xlv.setDivider(null);
//		xlv.setFadingEdgeLength(0);
//		xlv.setFooterDividersEnabled(false);
//		xlv.setHeaderDividersEnabled(false);
//		xlv.setSelector(new ColorDrawable(0X00000000));
//		xlv.setScrollingCacheEnabled(false);
//		xlv.setPullRefreshEnable(false);
//		xlv.setPullLoadEnable(false);
//		return xlv;
//	}
	
	/**
	 * 获取ListView，该ListView对公共的样式做了清除
	 * @param context
	 * @return
	 */
	public static ListView getCleanListView(Context context, int id){
	
		ListView lv = new ListView(context);
		lv.setId(id);
		lv.setDividerHeight(0);
		lv.setDivider(null);
		lv.setFadingEdgeLength(0);
		lv.setFooterDividersEnabled(false);
		lv.setHeaderDividersEnabled(false);
		lv.setSelector(new ColorDrawable(0X00000000));
		lv.setScrollingCacheEnabled(false);
		return lv;
	}
	
	/**
	 * 获取ExpandListView，该ListView对公共的样式做了清除
	 * @param context
	 * @return
	 */
	public static ExpandableListView getCleanExpandListView(Context context, int id){
	
		ExpandableListView elv = new ExpandableListView(context);
		elv.setId(id);
		elv.setDividerHeight(0);
		elv.setDivider(null);
		elv.setFadingEdgeLength(0);
		elv.setFooterDividersEnabled(false);
		elv.setHeaderDividersEnabled(false);
		elv.setChildDivider(null);
		elv.setSelector(new ColorDrawable(0X00000000));
		elv.setScrollingCacheEnabled(false);
		elv.setChildIndicator(null);
		elv.setGroupIndicator(null);
		return elv;
	}
	
	public static boolean checkTextViewEmpty(TextView tv){

		String text = null;
        if(tv != null)
            text = tv.getText().toString();
		if(text == null)
			return true;
		
		text = text.trim();
        return text.length() == 0 ? true : false;
	}
	
	public static void showView(View v){

        if(v == null)
            return;
		
		if(v.getVisibility() != View.VISIBLE)
			v.setVisibility(View.VISIBLE);
	}
	
	public static void hideView(View v){

        if(v == null)
            return;
		
		if(v.getVisibility() != View.INVISIBLE)
			v.setVisibility(View.INVISIBLE);
	}
	
	public static void goneView(View v){

        if(v == null)
            return;
		
		if(v.getVisibility() != View.GONE)
			v.setVisibility(View.GONE);
	}
	
	public static void showImageView(ImageView v, int imageResId){

        if(v == null)
            return;
		
		if(imageResId > 0){
			
			v.setImageResource(imageResId);
		}else{
			
			v.setImageDrawable(null);
		}
		
		if(v.getVisibility() != View.VISIBLE)
			v.setVisibility(View.VISIBLE);
	}
	
	public static void showImageView(ImageView v, Drawable drawable){

        if(v == null)
            return;
		
		v.setImageDrawable(drawable);
		if(v.getVisibility() != View.VISIBLE)
			v.setVisibility(View.VISIBLE);
	}
	
	public static void hideImageView(ImageView v){

        if(v == null)
            return;
		
		if(v.getVisibility() != View.INVISIBLE){
			
			v.setVisibility(View.INVISIBLE);
		}
		v.setImageDrawable(null);
	}
	
	public static void goneImageView(ImageView v){

        if(v == null)
            return;
		
		if(v.getVisibility() != View.GONE){
			
			v.setVisibility(View.GONE);
		}
		v.setImageDrawable(null);
	}
	
//	public static void clearAndRecycleImageViewDrawable(ImageView v){
//
//		if(v == null)
//			return;
//
//		if(v.getDrawable() instanceof BitmapDrawable){
//
//			BitmapDrawable bd = (BitmapDrawable) v.getDrawable();
//			v.setImageDrawable(null);
//			ImageUtil.recycleBitmap(bd.getBitmap());
//		}else{
//
//			v.setImageDrawable(null);
//		}
//	}
	
	public static void measureView(View child) {

        if(child == null)
            return;

		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public static View inflateLayout(int resource) {
		
		return inflateLayout(resource, null);
	}
	
	public static View inflateLayout(int resource, ViewGroup root) {
		
		return LayoutInflater.from(ExApplication.getContext()).inflate(resource, root);
	}

	public static FrameLayout inflateSpaceViewBydp(int dp) {

		Context ctx = ExApplication.getContext();
		FrameLayout fl = new FrameLayout(ctx);
		View spaceView = new View(ctx);

		FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				DensityUtil.dip2px(dp));
		fl.addView(spaceView, fllp);
		return fl;
	}
	
	public static FrameLayout inflateSpaceViewBypx(int px) {

		Context ctx = ExApplication.getContext();
		FrameLayout fl = new FrameLayout(ctx);
		View spaceView = new View(ctx);

		FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, px);
		fl.addView(spaceView, fllp);
		return fl;
	}
	
	public static View inflateSpaceView(int layoutParamWidth, int layoutParamHeight){
		
		View v = new View(ExApplication.getContext());
		v.setLayoutParams(new ViewGroup.LayoutParams(layoutParamWidth, layoutParamHeight));
		return v;
	}
	
	public static void setViewPagerScrollDuration(ViewPager viewPager, final int setDuration){
		
        try { 
        	
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");  
            mScroller.setAccessible(true);   
            mScroller.set(viewPager, new Scroller(viewPager.getContext()){
            	
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                
                	super.startScroll(startX, startY, dx, dy, setDuration);
                }
                
                @Override
                public void startScroll(int startX, int startY, int dx, int dy) {
                    
                	super.startScroll(startX, startY, dx, dy, setDuration);
                }
            });
            
        }catch(Throwable t){
        	
        	if(LogMgr.isDebug())
        		LogMgr.e("ViewPager set duration failed");
        }
	}

	public static Bitmap view2Bitmap(View view) {  
		
		try{
			int width = view.getWidth();
			int height = view.getHeight();
//			int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
//			int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
			view.measure(width, height);
//			view.layout(0, 0, width, height);
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);
			return bitmap;
		}catch(Throwable t){
			
			if(LogMgr.isDebug())
				t.printStackTrace();
			
			System.gc();
		}
		
		return null;
    }  
}
