package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.OrderActivity;
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

    private CartGridAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        mAdapter = new CartGridAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                showToast("position: " + position);
            }
        });
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        View v = new View(getActivity());
        v.setMinimumHeight(DensityUtil.dip2px(7));
        lv.addFooterView(v);
        lv.addFooterView(getFooterView());
        lv.setAdapter(mAdapter);
    }

    private View getFooterView() {

        TextView v = new TextView(getActivity());
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(42));
        v.setText("提交订单");
        v.setBackgroundColor(getResources().getColor(R.color.bg_red));
        v.setTextColor(getResources().getColor(R.color.white_trans87));
        v.setGravity(Gravity.CENTER);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        v.setLayoutParams(lp);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.startActivity(getActivity());
            }
        });
        return v;
    }

    @Override
    public void invalidateContent(int what, ProductList productList) {

    }

    public static CartListFragment newInstance(FragmentActivity activity) {

        return (CartListFragment) Fragment.instantiate(activity, CartListFragment.class.getName(), new Bundle());
    }
}
