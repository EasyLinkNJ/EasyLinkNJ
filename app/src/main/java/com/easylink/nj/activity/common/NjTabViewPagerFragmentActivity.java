package com.easylink.nj.activity.common;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylink.library.activity.ExFragment;
import com.easylink.library.adapter.ExFragmentFixedPagerAdapter;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.pageindicator.UnderlinePageIndicator;
import com.easylink.nj.R;

import java.util.List;

/**
 * Created by yihaibin on 15/8/29.
 */
public abstract class NjTabViewPagerFragmentActivity extends NjFragmentActivity{

    private View mFrame;
    private LinearLayout mLlTab;
    private ViewPager mVpContent;
    private ExFragmentFixedPagerAdapter mPagerAdapter;
    protected View mSelectedTab;
    private int mSelectPos = -1;
    private ExFragment mSelectedFragment;


    protected void setContentViewWithTab(){

        mFrame = getLayoutInflater().inflate(R.layout.act_product_search, null);
        mLlTab = (LinearLayout) mFrame.findViewById(R.id.llTab);
        mVpContent = (ViewPager) mFrame.findViewById(R.id.vpContent);
        setContentView(mFrame);
    }

    protected void setViewPagerLimit(int limit){

        mVpContent.setOffscreenPageLimit(limit);
    }

    protected void setTabAndFragment(List<String> tabTexts, List<? extends Fragment> fragments, int defaultPos, boolean keepFragmentState){

        initTabViews(tabTexts, defaultPos);
        initFragments(fragments, keepFragmentState);
        onSelectChangedFromPager(defaultPos, false);
        if(defaultPos != 0)
            mVpContent.setCurrentItem(defaultPos, true);
    }

    private void initTabViews(List<String> tabTexts, int defPos){

        TextView tv = null;
        //View split = null;
        LinearLayout.LayoutParams lllp = null;

        int dp42 = DensityUtil.dip2px(42);
        //int dp18 = DensityUtil.dip2px(18);

        AlphaAnimation initAnim = new AlphaAnimation(1f, 0.5f);
        initAnim.setDuration(0);
        initAnim.setFillAfter(true);

        for(int i = 0;i <tabTexts.size(); i++){

            tv = new TextView(this);
            tv.setText(tabTexts.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv.setTextColor(0XFF000000);
//            tv.setTextColor(getResources().getColorStateList(R.color.qa_text_tab_selected));
            tv.setGravity(Gravity.CENTER);
            final int pos = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mVpContent.setCurrentItem(pos, true);
                }
            });

            lllp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp42);
            lllp.weight = 1;
            mLlTab.addView(tv, lllp);

            //去掉分割线
            // if(tabTexts.size() > 1 && i != tabTexts.size() - 1) {
            //
            // split = new View(this);
            // split.setBackgroundColor(0XFFC1C1C1);
            // lllp = new LinearLayout.LayoutParams(1, dp18);
            // lllp.weight = 0;
            // mLlTab.addView(split, lllp);
            // }

            if(i != defPos)
                tv.setAnimation(initAnim);
        }

        initAnim.start();
    }

    private void initFragments(List<? extends Fragment> fragments, boolean keepFragmentState) {

        mPagerAdapter = new ExFragmentFixedPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragmentItemDestoryEnable(!keepFragmentState);
        mPagerAdapter.setFragments(fragments);

        mVpContent.setAdapter(mPagerAdapter);
        //mVpContent.setPageMargin(DensityUtil.dip2px(4));//不要PageMargin

        // UnderlinePageIndicator
        UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicatorUnderLine);
        indicator.setSelectedColor(0Xff3f51b5);
        indicator.setViewPager(mVpContent);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                onSelectChangedFromPager(position, true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void onSelectChangedFromPager(int position, boolean needAnim){

        if(mSelectPos == position)
            return;

        if(mSelectedTab != null)
            mSelectedTab.startAnimation(getFadeOutAnim());

        mSelectedTab = mLlTab.getChildAt(position);//position * 2//去掉分割线
        if(mSelectedTab != null){

            if(needAnim)
                mSelectedTab.startAnimation(getFadeInAnim());

            mSelectPos = position;
            onSelectChanged(position, true);
        }
    }

    protected void onSelectChanged(int position, boolean fromUserSelect){

        if(fromUserSelect){

            if(mSelectedFragment != null)
                mSelectedFragment.onViewPageSelectChanged(false);

            mSelectedFragment = (ExFragment) mPagerAdapter.getItem(position);
            mSelectedFragment.onViewPageSelectChanged(true);
        }
    }

    protected int getSelectPosition(){

        return mVpContent.getCurrentItem();
    }

    protected void hideFrame(){

        ViewUtil.hideView(mFrame);
    }

    protected void showFrame(){

        ViewUtil.showView(mFrame);
    }

    private AlphaAnimation getFadeInAnim(){

        AlphaAnimation fadeInAnim = new AlphaAnimation(0.5f, 1f);
        fadeInAnim.setDuration(200);
        fadeInAnim.setFillAfter(true);
        return fadeInAnim;
    }

    private AlphaAnimation getFadeOutAnim(){

        AlphaAnimation	fadeOutAnim = new AlphaAnimation(1f, 0.5f);
        fadeOutAnim.setDuration(200);
        fadeOutAnim.setFillAfter(true);
        return fadeOutAnim;
    }
}
