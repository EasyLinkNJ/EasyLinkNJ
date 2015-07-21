package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.easylink.library.activity.ExFragmentActivity;
import com.easylink.library.adapter.ExFragmentFixedPagerAdapter;
import com.easylink.library.plugin.DelayBackHandler;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.ExViewPager;
import com.easylink.nj.R;
import com.easylink.nj.utils.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class MainActivity extends ExFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, DelayBackHandler.OnDelayBackListener {

    private DelayBackHandler mBackKeyHandler;
    private ExViewPager mViewPager;
    private View mTvHome, mTvProduct, mRlCart, mTvOrder, mTvCurrentSelected;
    private TextView mTvCartCount;
    private ImageView mIvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    @Override
    protected void onResume() {

        super.onResume();
        int count = DBManager.getInstance().getCartCount();
        if (mTvCartCount != null) {

            mTvCartCount.setText(String.valueOf(count));
            mTvCartCount.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {

        mBackKeyHandler = new DelayBackHandler();
        mBackKeyHandler.setOnDelayBackListener(this);
    }

    @Override
    protected void initTitleView() {

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_logo);
        LayoutParams lp1 = new LayoutParams(DensityUtil.dip2px(53), DensityUtil.dip2px(25));
        lp1.leftMargin = DensityUtil.dip2px(16);
        addTitleLeftView(iv, lp1);

        mIvSearch = addTitleRightImageView(R.mipmap.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewUtil.hideView(mIvSearch);
//        LayoutParams lp = new LayoutParams(DensityUtil.dip2px(278), DensityUtil.dip2px(36));
//        lp.rightMargin = DensityUtil.dip2px(12);
//        addTitleRightView(ViewUtil.inflateLayout(R.layout.view_search), lp);

    }

    @Override
    protected void initContentView() {

        initTabViews();
        initViewPager();
        onPageSelected(0);
    }

    private void initTabViews() {

        mTvHome = findViewById(R.id.tvHome);
        mTvHome.setOnClickListener(this);

        mTvProduct = findViewById(R.id.tvProduct);
        mTvProduct.setOnClickListener(this);

        mRlCart = findViewById(R.id.rlCart);
        mRlCart.setOnClickListener(this);
        mTvCartCount = (TextView) mRlCart.findViewById(R.id.tvCount);
        int count = DBManager.getInstance().getCartCount();
        if (count > 0) {

            mTvCartCount.setText(String.valueOf(count));
            ViewUtil.showView(mTvCartCount);
        }

        mTvOrder = findViewById(R.id.tvOrder);
        mTvOrder.setOnClickListener(this);
    }

    private void initViewPager() {

        ExFragmentFixedPagerAdapter mPagerAdapter = new ExFragmentFixedPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragments(getMainFragments());
        mPagerAdapter.setFragmentItemDestoryEnable(false);

        mViewPager = (ExViewPager) findViewById(R.id.vpContent);
        mViewPager.setCanScroll(false);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageMargin(DensityUtil.dip2px(6));
    }

    private List<Fragment> getMainFragments() {

        ArrayList<Fragment> fragments = new ArrayList();
        fragments.add(HomeFragment.newInstance(this));
        fragments.add(ProductListFragment.newInstance(this));
        fragments.add(CartListFragment.newInstance(this));
        fragments.add(OrderListFragment.newInstance(this));
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

        if (mTvCurrentSelected != null) {

            mTvCurrentSelected.setSelected(false);
            mTvCurrentSelected = null;
        }

        View tvNewSelected = null;
        switch (position) {
            case 0:
                tvNewSelected = mTvHome;
                break;
            case 1:
                tvNewSelected = mTvProduct;
                break;
            case 2:
                tvNewSelected = mRlCart;
                break;
            case 3:
                tvNewSelected = mTvOrder;
                break;
        }

        if (tvNewSelected != null) {

            tvNewSelected.setSelected(true);
            mTvCurrentSelected = tvNewSelected;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvHome:
                mViewPager.setCurrentItem(0, true);
                ViewUtil.hideView(mIvSearch);
                break;
            case R.id.tvProduct:
                mViewPager.setCurrentItem(1, true);
                ViewUtil.showView(mIvSearch);
                break;
            case R.id.rlCart:
                mViewPager.setCurrentItem(2, true);
                ViewUtil.hideView(mIvSearch);
                break;
            case R.id.tvOrder:
                mViewPager.setCurrentItem(3, true);
                ViewUtil.hideView(mIvSearch);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        mBackKeyHandler.triggerPreBack();
    }

    @Override
    public void onDelayBack(boolean preBack) {

        if (preBack) {

            showToast(R.string.toast_exit_tip);
        } else {

            super.finish();
        }
    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
