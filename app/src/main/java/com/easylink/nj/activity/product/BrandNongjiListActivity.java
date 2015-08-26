package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.BrandAdapter;
import com.easylink.nj.bean.news.NewsList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandNongjiListActivity extends BrandListActivity<NewsList> implements OnItemViewClickListener{

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

    @Override
    public void onItemViewClick(int position, View clickView) {


    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, BrandNongjiListActivity.class);
        activity.startActivity(intent);
    }
}
