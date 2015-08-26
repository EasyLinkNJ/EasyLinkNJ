package com.easylink.nj.activity.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.NewsListAdapter;
import com.easylink.nj.bean.news.News;
import com.easylink.nj.bean.news.NewsList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * 新闻列表
 * @author yihaibin
 */
public class NewsListActivity extends NjHttpXlvActivity<NewsList> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack(R.string.news_list);
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        final NewsListAdapter adapter = new NewsListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

                News news = adapter.getItem(position);
                if (news != null)
                    NewsDetailActivity.startActivity(NewsListActivity.this, news.getId(), news.getUrl());
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

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, NewsListActivity.class);
        activity.startActivity(intent);
    }

}
