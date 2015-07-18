package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.easylink.nj.activity.common.NjHttpFragment;
import com.easylink.nj.bean.product.ProductList;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListFragment extends NjHttpFragment<ProductList> {

    @Override
    protected void initData() {

    }

    @Override
    protected void initContentView() {

    }

    @Override
    public void invalidateContent(int what, ProductList productList) {

    }

    public static OrderListFragment newInstance(FragmentActivity activity) {

        return (OrderListFragment) Fragment.instantiate(activity, OrderListFragment.class.getName(), new Bundle());
    }
}
