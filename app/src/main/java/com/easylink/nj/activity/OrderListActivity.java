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
import com.easylink.nj.adapter.OrderAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListActivity extends NjHttpActivity<Order> {

    private ListView mLvOrder;
    private OrderAdapter mAdapter;
    private TextView mTvBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
        if (getData())
            fillData2View();
    }

    private boolean getData() {

        List<Cart> orderCarts = DBManager.getInstance().getOrderCarts();
        if (orderCarts == null || orderCarts.isEmpty()) {

            switchDisable(R.mipmap.ic_order_nothing);
            hideView(mTvBottomBar);

            return false;
        }

        switchContent(0);
        showView(mTvBottomBar);

        if (mAdapter == null) {

            mAdapter = new OrderAdapter();
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView) {

                    Cart cart = mAdapter.getItem(position);
                    if (cart != null)
                        OrderDetailActivity.startActivity(OrderListActivity.this, cart.getId());
                }
            });
        }
        mAdapter.setData(orderCarts);

        return true;
    }

    private void fillData2View() {

        if (mLvOrder == null) {

            mLvOrder = (ListView) findViewById(R.id.lvCart);
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
    public boolean invalidateContent(int what, Order productList) {

        return true;
    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, OrderListActivity.class);
        act.startActivity(intent);
    }
}
