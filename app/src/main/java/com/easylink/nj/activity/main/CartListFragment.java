package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpFragment;
import com.easylink.nj.adapter.CartGridAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartListFragment extends NjHttpFragment<ProductList> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView((ViewUtil.getCleanListView(getActivity(), R.id.lv)));
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        CartGridAdapter mAdapter = new CartGridAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

            }
        });
        ListView lv = (ListView) findViewById(R.id.lv);
        View v = new View(getActivity());
        v.setMinimumHeight(DensityUtil.dip2px(8));
        lv.addFooterView(v);
        lv.setAdapter(mAdapter);
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
