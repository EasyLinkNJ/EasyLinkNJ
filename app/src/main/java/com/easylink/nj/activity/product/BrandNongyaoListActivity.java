package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.BrandAdapter;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandNongyaoListActivity extends NjHttpXlvActivity{

    public static final int TYPE_NONGJI = 1;
    public static final int TYPE_NONGYAO = 2;
    public static final int TYPE_HUAFEI = 3;
    public static final int TYPE_ZHONGZI = 4;

    @Override
    public ExAdapter getAdapter() {

        final BrandAdapter adapter = new BrandAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

            }
        });
        return adapter;
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("品牌列表");
        switch(getIntent().getIntExtra("type", 0)){

            case TYPE_NONGJI:
                break;
            case TYPE_NONGYAO:
                break;
            case TYPE_HUAFEI:
                break;
            case TYPE_ZHONGZI:
                break;
        }
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return null;
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return null;
    }

    public static void startAcitivty4Nongji(Activity activity){

        startAcitivty(activity, TYPE_NONGJI);
    }

    public static void startAcitivty4Nongyao(Activity activity){

        startAcitivty(activity, TYPE_NONGYAO);
    }

    public static void startAcitivty4Huafei(Activity activity){

        startAcitivty(activity, TYPE_HUAFEI);
    }

    public static void startAcitivty4Zhongzi(Activity activity){

        startAcitivty(activity, TYPE_ZHONGZI);
    }

    public static void startAcitivty(Activity activity, int type){

        Intent intent = new Intent();
        intent.setClass(activity, BrandNongyaoListActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }
}
