package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.easylink.nj.activity.common.NjHttpFragment;
import com.easylink.nj.bean.product.ProductList;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartListFragment extends NjHttpFragment<ProductList> {

    @Override
    protected void initData() {

    }

    @Override
    protected void initContentView() {

    }

    @Override
    public void invalidateContent(int what, ProductList productList) {

    }

    public static CartListFragment newInstance(FragmentActivity activity) {

        return (CartListFragment) Fragment.instantiate(activity, CartListFragment.class.getName(), new Bundle());
    }
}
