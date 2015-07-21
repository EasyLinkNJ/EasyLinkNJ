package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.OrderListAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.utils.DBManager;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListActivity extends NjHttpActivity<Order> {

    private ListView mLvOrder;
    private OrderListAdapter mAdapter;
    private Order mOrder;
    private TextView mTvBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
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

            mAdapter = new OrderListAdapter();
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView) {

                    Cart cart = mAdapter.getItem(position);
                    ProductDetailActivity.startActivity(OrderListActivity.this, cart.productId, true);
                }
            });
        }
        mAdapter.setData(mOrder.carts);

        return true;
    }

    private void fillData2View() {

        if (mLvOrder == null) {

            mLvOrder = (ListView) findViewById(R.id.lvCart);

//            View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
//            EditText etPersion = (EditText) headerView.findViewById(R.id.etPersion);
//            EditText etPhone = (EditText) headerView.findViewById(R.id.etTel);
//            EditText etAddress = (EditText) headerView.findViewById(R.id.etAddress);
//            etPersion.setText(mOrder.address.name);
//            etPhone.setText(mOrder.address.phone);
//            etAddress.setText(mOrder.address.address);
//            etPersion.setEnabled(false);
//            etPhone.setEnabled(false);
//            etAddress.setEnabled(false);
//            mLvOrder.addHeaderView(headerView);

            mLvOrder.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }

        if (mTvBottomBar == null) {

            mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
            mTvBottomBar.setText("提醒客服处理");
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
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("我的订单");
    }

    @Override
    protected void initContentView() {

    }

    @Override
    public void invalidateContent(int what, Order productList) {

    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, OrderListActivity.class);
        act.startActivity(intent);
    }
}
