package com.easylink.nj.activity.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.news.news.NewsListAdapter;
import com.easylink.nj.bean.news.New;
import com.easylink.nj.bean.news.NewListData;
import com.easylink.nj.httptask.NjHttpUtil;

/**
 * 新闻列表
 * @author yihaibin
 */
public class NewsActivity extends NjHttpActivity<NewListData>{

    private NewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(ViewUtil.getCleanListView(this, R.id.lv));
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        mAdapter = new NewsListAdapter();
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

                New news = mAdapter.getItem(position);
                if(news != null)
                    NewsDetailActivity.startActivity(NewsActivity.this, news.getUrl());
            }
        });
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack(R.string.news_list);
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        lv.setDividerHeight(2);//2px
        lv.setAdapter(mAdapter);
    }

    private void loadDataFromServer(){

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getNews(), NewListData.class);
    }

    @Override
    public void invalidateContent(int what, NewListData data) {

        mAdapter.setData(data.getList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onTipViewClick() {

        loadDataFromServer();
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, NewsActivity.class);
        activity.startActivity(intent);
    }

}
