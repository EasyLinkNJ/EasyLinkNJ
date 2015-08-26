package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easylink.library.activity.ExFragment;
import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.view.listview.XListView;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpXlvFragment;
import com.easylink.nj.activity.news.NewsDetailActivity;
import com.easylink.nj.activity.product.BrandHuafeiListActivity;
import com.easylink.nj.activity.product.BrandListActivity;
import com.easylink.nj.activity.product.BrandNongjiListActivity;
import com.easylink.nj.activity.product.BrandNongyaoListActivity;
import com.easylink.nj.activity.product.BrandZhongziListActivity;
import com.easylink.nj.adapter.NewsListAdapter;
import com.easylink.nj.bean.news.News;
import com.easylink.nj.bean.news.NewsList;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.view.ClickScaleAnimRelativeLayout;

import java.util.List;

/**
 * Created by yihaibin on 15/7/14.
 */
public class HomeFragment extends ExFragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(R.layout.act_main_fmt_home);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initContentView() {

        ImageView cover = (ImageView) findViewById(R.id.ivCover);
        cover.setImageResource(R.mipmap.ic_main_home_cover);

        ClickScaleAnimRelativeLayout csarl = null;

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlNongji);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BrandNongjiListActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlNongyao);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BrandNongyaoListActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlHuafei);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BrandHuafeiListActivity.startActivity(getActivity());
            }
        });

        csarl = (ClickScaleAnimRelativeLayout) findViewById(R.id.csarlZhongzi);
        csarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BrandZhongziListActivity.startActivity(getActivity());
            }
        });
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