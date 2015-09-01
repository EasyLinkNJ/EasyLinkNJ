package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.OrderSectionAdapter;
import com.easylink.nj.bean.OrderData;
import com.easylink.nj.bean.db.Order;

import java.util.ArrayList;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderDetailActivity extends NjHttpActivity<Order> {

    private OrderSectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
    }

    @Override
    protected void initData() {

        ArrayList<OrderData> datas = (ArrayList<OrderData>) getIntent().getSerializableExtra("datas");

        mAdapter = new OrderSectionAdapter(false);
        mAdapter.setData(datas);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                OrderData data = mAdapter.getItem(position);
                if (data != null)
                    for (ProductDetailActivity.ProductType type : ProductDetailActivity.ProductType.values())
                        if (type.getDesc().equals(data.type))
                            ProductDetailActivity.startActivity(OrderDetailActivity.this, type, data.productId, true);
            }
        });
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("订单详情");
    }

    @Override
    protected void initContentView() {

        ListView lvOrder = (ListView) findViewById(R.id.lvCart);
        lvOrder.setDivider(null);

        View vHeader = ViewUtil.inflateLayout(R.layout.view_order_header);
        EditText etPersion = (EditText) vHeader.findViewById(R.id.etPersion);
        EditText etPhone = (EditText) vHeader.findViewById(R.id.etTel);
        EditText etAddress = (EditText) vHeader.findViewById(R.id.etAddress);
        etPersion.setEnabled(false);
        etPhone.setEnabled(false);
        etAddress.setEnabled(false);

        OrderData data = mAdapter.getItem(0);
        etPersion.setText(data.name);
        etPhone.setText(data.phone);
        etAddress.setText(data.address);

        lvOrder.addHeaderView(vHeader);
        lvOrder.setAdapter(mAdapter);

        TextView bottomBar = (TextView) findViewById(R.id.tvBottomBar);
        bottomBar.setText("提醒客服处理");
        bottomBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showToast("已发送提醒");
            }
        });
    }

    @Override
    public boolean invalidateContent(int what, Order order) {

        return true;
    }

    public static void startActivity(Activity act, ArrayList<OrderData> datas) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderDetailActivity.class);
        intent.putExtra("datas", datas);
        act.startActivity(intent);
    }
}
