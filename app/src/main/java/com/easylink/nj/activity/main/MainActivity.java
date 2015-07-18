package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.activity.ExFragmentActivity;
import com.easylink.library.adapter.ExFragmentFixedPagerAdapter;
import com.easylink.library.plugin.DelayBackHandler;
import com.easylink.library.util.DensityUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.product.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class MainActivity extends ExFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, DelayBackHandler.OnDelayBackListener {

    private DelayBackHandler mBackKeyHandler;
    private ExFragmentFixedPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private TextView mTvHome, mTvProduct, mTvCart, mTvOrder, mTvCurrentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    @Override
    protected void initData() {

        mBackKeyHandler = new DelayBackHandler();
        mBackKeyHandler.setOnDelayBackListener(this);
    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initContentView() {

        initTabViews();
        initViewPager();
        onPageSelected(0);
    }

    private void initTabViews() {

        mTvHome = (TextView) findViewById(R.id.tvHome);
        mTvHome.setOnClickListener(this);

        mTvProduct = (TextView) findViewById(R.id.tvProduct);
        mTvProduct.setOnClickListener(this);

        mTvCart = (TextView) findViewById(R.id.tvCart);
        mTvCart.setOnClickListener(this);

        mTvOrder = (TextView) findViewById(R.id.tvOrder);
        mTvOrder.setOnClickListener(this);
    }

    private void initViewPager() {

        mPagerAdapter = new ExFragmentFixedPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragments(getMainFragments());
        mPagerAdapter.setFragmentItemDestoryEnable(false);

        mViewPager = (ViewPager) findViewById(R.id.vpContent);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageMargin(DensityUtil.dip2px(6));
    }

    private List<Fragment> getMainFragments(){

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(HomeFragment.newInstance(this));
        fragments.add(ProductListFragment.newInstance(this));
        fragments.add(HomeFragment.newInstance(this));
        fragments.add(HomeFragment.newInstance(this));
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {

        if(mTvCurrentSelected != null){

            mTvCurrentSelected.setSelected(false);
            mTvCurrentSelected = null;
        }

        TextView tvNewSelected = null;
        switch (position){
            case 0:
                tvNewSelected = mTvHome;
                break;
            case 1:
                tvNewSelected = mTvProduct;
                break;
            case 2:
                tvNewSelected = mTvCart;
                break;
            case 3:
                tvNewSelected = mTvOrder;
                break;
        }

        if(tvNewSelected != null){

            tvNewSelected.setSelected(true);
            mTvCurrentSelected = tvNewSelected;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvHome:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tvProduct:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tvCart:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.tvOrder:
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        mBackKeyHandler.triggerPreBack();
    }

    @Override
    public void onDelayBack(boolean preBack) {

        if(preBack){

            showToast(R.string.toast_exit_tip);
        }else{

            super.finish();
        }
    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
