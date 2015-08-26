package com.easylink.nj.activity.product;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.activity.common.NjHttpXlvFragment;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.ProductItem;

/**
 * Created by yihaibin on 15/8/25.
 */
public abstract class ProductListFragment<T> extends NjHttpXlvFragment<T> implements OnItemViewClickListener{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
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
}
