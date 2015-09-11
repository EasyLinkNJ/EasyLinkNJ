package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.easylink.library.activity.ExFragment;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.pageindicator.IconPageIndicator;
import com.easylink.library.view.pager.autoscroll.AutoScrollViewPager;
import com.easylink.nj.R;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.activity.product.ProductListHuafeiActivity;
import com.easylink.nj.activity.product.ProductListNongjiActivity;
import com.easylink.nj.activity.product.ProductListNongyaoActivity;
import com.easylink.nj.activity.product.ProductListZhongziActivity;
import com.easylink.nj.adapter.HomeBannerPageAdapter;
import com.easylink.nj.bean.Banner;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;
import com.easylink.nj.prefs.SharedPrefs;
import com.easylink.nj.view.ClickScaleAnimRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/7/14.
 */
public class HomeFragment extends ExFragment{

    private AutoScrollViewPager mAsvpBanner;
    private IconPageIndicator mIpiBannerIndicator;
    private HomeBannerPageAdapter mBannerPagerAdapter;
    private SharedPrefs mSharePrefs;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(R.layout.act_main_fmt_home);
        loadBannerFromServer();
    }

    @Override
    public void onStart() {

        super.onStart();
        if(!isHidden()){

            if(mBannerPagerAdapter.getCount() > 1)
                mAsvpBanner.startAutoScroll();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        super.onHiddenChanged(hidden);
        if(hidden){

            mAsvpBanner.stopAutoScroll();
        }else{

            if(mBannerPagerAdapter.getCount() > 1)
                mAsvpBanner.startAutoScroll();
        }
    }

    @Override
    public void onStop() {

        super.onStop();
        mAsvpBanner.stopAutoScroll();
    }

    @Override
    protected void initData() {

        mSharePrefs = new SharedPrefs(getActivity(), "bannerPrefs");
    }

    @Override
    protected void initContentView() {

        initBannerViews();

        ClickScaleAnimRelativeLayout csarl = null;

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlNongji);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductListNongjiActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlNongyao);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductListNongyaoActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlHuafei);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductListHuafeiActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlZhongzi);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductListZhongziActivity.startActivity(getActivity());
            }
        });
    }

    private void initBannerViews() {

        int bannerHeight = (int)(DeviceUtil.getScreenWidth() * (450 / 1080f));
        FrameLayout flBannerDiv = (FrameLayout) findViewById(R.id.flAdvertDiv);
        flBannerDiv.getLayoutParams().height = bannerHeight;

        mAsvpBanner = (AutoScrollViewPager) findViewById(R.id.asvpAdvert);
        ViewUtil.setViewPagerScrollDuration(mAsvpBanner, 800);
        mAsvpBanner.setInterval(3000);
        mAsvpBanner.setStopScrollWhenTouch(true);
        mAsvpBanner.setCycle(true);

        mBannerPagerAdapter = new HomeBannerPageAdapter();
        mBannerPagerAdapter.setData(getInitBanners());
        mAsvpBanner.setAdapter(mBannerPagerAdapter);

        mIpiBannerIndicator = (IconPageIndicator) findViewById(R.id.ipiAdvert);
        mIpiBannerIndicator.setIndicatorSpace(DensityUtil.dip2px(3));
        mIpiBannerIndicator.setViewPager(mAsvpBanner);

        mBannerPagerAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View view) {

                onBannerItemViewClick(position);
            }
        });
    }

    private void onBannerItemViewClick(int position) {

        Banner banner = mBannerPagerAdapter.getItem(position);
        if(banner == null)
            return;

        if(Banner.MOD_NAME_NONGJI.equals(banner.getModname())){

            ProductDetailActivity.startActivityFromNJ(getActivity(), banner.getModid(), false);
        }else if(Banner.MOD_NAME_NONGJI_PEIJIAN.equals(banner.getModname())){

            ProductDetailActivity.startActivityFromNJParts(getActivity(), banner.getModid(), false);
        }else if(Banner.MOD_NAME_NONGYAO.equals(banner.getModname())){

            ProductDetailActivity.startActivityFromNY(getActivity(), banner.getModid(), false);
        }else if(Banner.MOD_NAME_HUAFEI.equals(banner.getModname())){

            ProductDetailActivity.startActivityFromHF(getActivity(), banner.getModid(), false);
        }else if(Banner.MOD_NAME_ZHONGZI.equals(banner.getModname())){

            ProductDetailActivity.startActivityFromZZ(getActivity(), banner.getModid(), false);
        }
    }

    private void loadBannerFromServer() {

        if(DeviceUtil.isNetworkDisable())
            return;

        executeHttpTask(1, NjHttpUtil.getBannerList(), new NjJsonListener<List<Banner>>(Banner.class) {

            @Override
            public void onTaskResult(List<Banner> result) {

                try{

                    saveBanners((ArrayList<Banner>) result);
                    if(result == null || result.isEmpty())
                        result = getDefaultBanners();

                    mAsvpBanner.stopAutoScroll();
                    mBannerPagerAdapter.setData(result);
                    mAsvpBanner.setAdapter(mBannerPagerAdapter);
                    mBannerPagerAdapter.notifyDataSetChanged();

                    mIpiBannerIndicator.notifyDataSetChanged();
                    if(mBannerPagerAdapter.getCount() > 1)
                        mIpiBannerIndicator.setCurrentItem(0);

                }catch(Exception e){

                }

                if(mBannerPagerAdapter.getCount() > 1)
                    mAsvpBanner.startAutoScroll();
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                //nothing
            }
        });
    }

    private ArrayList<Banner> getInitBanners(){

        ArrayList<Banner> banners = getStorageBanners();
        if(banners == null || banners.isEmpty())
            banners = getDefaultBanners();

        return banners;
    }

    private ArrayList<Banner> getDefaultBanners(){

        ArrayList<Banner> banners = new ArrayList<Banner>();
        Banner banner = new Banner();
        banner.setModname(Banner.MOD_NAME_NONE);
        banner.setPicurl("res:///" + R.mipmap.ic_main_home_cover);
        banners.add(banner);
        return banners;
    }

    private ArrayList<Banner> getStorageBanners(){

        try{

            return (ArrayList<Banner>)mSharePrefs.getSerializable("bannerKey");

        }catch(Exception e){

            return null;
        }
    }

    private void saveBanners(ArrayList<Banner> banners){

        try{
            mSharePrefs.putSerializable("bannerKey", banners);
        }catch(Exception e){

        }
    }

    public static HomeFragment newInstance(FragmentActivity activity){

        return (HomeFragment) Fragment.instantiate(activity, HomeFragment.class.getName(), new Bundle());
    }
}
/*
        extends NjHttpXlvFragment<NewsList> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    public void onInitXListView(XListView xlv) {

        xlv.addHeaderView(getHeadView());
    }

    @Override
    public ExAdapter getAdapter() {

        final NewsListAdapter adapter = new NewsListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

                News news = adapter.getItem(position);
                if (news != null)
                    NewsDetailActivity.startActivity(getActivity(), news.getId(), news.getUrl());
            }
        });
        return adapter;
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getNewsList(page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return NewsList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(NewsList result) {

        return result == null ? null : result.getList();
    }

//    private NewsListAdapter mAdapter;

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
//        loadDataFromServer();
//    }

//    @Override
//    protected void initData() {

//        mAdapter = new NewsListAdapter();
//        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
//            @Override
//            public void onItemViewClick(int position, View clickView) {
//
//                News news = mAdapter.getItem(position);
//                if (news != null)
//                    NewsDetailActivity.startActivity(getActivity(), news.getId(), news.getUrl());
//            }
//        });
//    }

//    @Override
//    protected void initContentView() {

//        ListView lv = (ListView) findViewById(R.id.lv);
//        lv.addHeaderView(getHeadView());
//        lv.addFooterView(getFootView());
//        lv.setAdapter(mAdapter);
//        lv.setHeaderDividersEnabled(true);
//        lv.setFooterDividersEnabled(true);
//        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
//        lv.setDividerHeight(2);//2px
//    }

//    @Override
//    public void invalidateContent(int what, NewsList newsList) {
//
//        mAdapter.setData(newsList.getList());
//        mAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    protected void onTipViewClick() {
//
//        loadDataFromServer();
//    }

//    private void loadDataFromServer() {
//
//        executeHttpTaskByUiSwitch(0, NjHttpUtil.getNewsTop5(), NewsList.class);
//    }

    private View getHeadView(){

        LinearLayout ll = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.act_main_fmt_home_header, null);

        ImageView cover = (ImageView) ll.findViewById(R.id.ivCover);
        cover.setImageResource(R.mipmap.ic_main_home_cover);

//        TextView tv = (TextView) ll.findViewById(R.id.tvShopping);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ProductListActivity.startActivity(getActivity());
//            }
//        });
//
//        tv = (TextView) ll.findViewById(R.id.tvRepair);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        tv = (TextView) ll.findViewById(R.id.tvReplace);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return ll;
    }
//
//    private View getFootView(){
//
//        TextView tv = new TextView(getActivity());
//        tv.setText(R.string.look_more);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        tv.setTextColor(getResources().getColor(R.color.black_trans87));
//        tv.setGravity(Gravity.CENTER);
//        tv.setPadding(0, DensityUtil.dip2px(12), 0, DensityUtil.dip2px(12));
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                NewsListActivity.startActivity(getActivity());
//            }
//        });
//        return tv;
//    }

    public static HomeFragment newInstance(FragmentActivity activity){

        return (HomeFragment) Fragment.instantiate(activity, HomeFragment.class.getName(), new Bundle());
    }
}
*/