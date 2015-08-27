package com.easylink.nj.activity.product;

import android.os.Bundle;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.ProductListAdapter;
import com.easylink.nj.bean.product.ProductItem;

/**
 * Created by yihaibin on 15/8/25.
 */
public abstract class ProductListActivity<T> extends NjHttpXlvActivity<T> implements OnItemViewClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        final ProductListAdapter adapter = new ProductListAdapter();
        adapter.setOnItemViewClickListener(this);
        return adapter;
    }

    protected ProductItem getProductItem(int position) {

        return (ProductItem)super.getAdapterItem(position);
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("产品列表");
    }
}
