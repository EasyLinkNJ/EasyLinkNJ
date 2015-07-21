package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpFragment;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.CartListAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.utils.DBManager;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListFragment extends NjHttpFragment<ProductList> {

    private ListView mLvOrder;
    private CartListAdapter mAdapter;
    private Order mOrder;
    private TextView mTvBottomBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(R.layout.act_order);
    }

    @Override
    public void onResume() {

        super.onResume();
        if (getData())
            fillData2View();
    }

    private boolean getData() {

        mOrder = DBManager.getInstance().getOrder();
        if (mOrder == null || mOrder.carts == null || mOrder.carts.isEmpty()) {

            switchDisable(R.mipmap.ic_order_nothing);
            hideView(mTvBottomBar);

            return false;
        }

        switchContent(0);
        showView(mTvBottomBar);

        if (mAdapter == null) {

            mAdapter = new CartListAdapter();
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView) {

                    Cart cart = mAdapter.getItem(position);
                    ProductDetailActivity.startActivity(getActivity(), cart.productId, true);
                }
            });
        }
        mAdapter.setData(mOrder.carts);

        return true;
    }

    private void fillData2View() {

        if (mLvOrder == null) {

            mLvOrder = (ListView) findViewById(R.id.lvOrder);

            View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
            EditText etPersion = (EditText) headerView.findViewById(R.id.etPersion);
            EditText etPhone = (EditText) headerView.findViewById(R.id.etTel);
            EditText etAddress = (EditText) headerView.findViewById(R.id.etAddress);
            etPersion.setText(mOrder.user.name);
            etPhone.setText(mOrder.user.phone);
            etAddress.setText(mOrder.user.address);
            etPersion.setEnabled(false);
            etPhone.setEnabled(false);
            etAddress.setEnabled(false);
            mLvOrder.addHeaderView(headerView);

            mLvOrder.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }

        if (mTvBottomBar == null) {

            mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
            mTvBottomBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showToast("已发送提醒");
                }
            });
        }
    }

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
