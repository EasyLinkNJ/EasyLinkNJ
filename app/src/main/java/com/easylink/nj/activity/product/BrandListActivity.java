package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.BrandAdapter;
import com.easylink.nj.bean.product.BrandItem;

/**
 * Created by yihaibin on 15/8/25.
 */
public abstract class BrandListActivity<T> extends NjHttpXlvActivity<T> implements OnItemViewClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        final BrandAdapter adapter = new BrandAdapter();
        adapter.setOnItemViewClickListener(this);
        return adapter;
    }

    protected BrandItem getBrandItem(int position) {

        return (BrandItem)super.getAdapterItem(position);
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("品牌列表");
    }
}
