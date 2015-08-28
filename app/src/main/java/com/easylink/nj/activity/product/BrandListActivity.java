package com.easylink.nj.activity.product;

import android.os.Bundle;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.BrandAdapter;
import com.easylink.nj.bean.product.BrandItem;

import java.util.Objects;

/**
 * Created by yihaibin on 15/8/25.
 */
public abstract class BrandListActivity<T> extends NjHttpXlvActivity<T> implements OnItemViewClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        BrandAdapter adapter = new BrandAdapter();
        adapter.setOnItemViewClickListener(this);
        return adapter;
    }

    protected BrandItem getBrandItem(int position) {

        Object obj = getAdapterItem(position);
        return obj == null ? null : (BrandItem) obj;
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("品牌列表");
    }
}
