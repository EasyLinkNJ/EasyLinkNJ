package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.OrderAdapter;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderDetailActivity extends NjHttpActivity<Order> {

    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCart(getIntent().getLongExtra("cartId", -1));
        mAdapter = new OrderAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                Cart cart = mAdapter.getItem(position);
                if (cart != null)
                    for (ProductDetailActivity.ProductType type : ProductDetailActivity.ProductType.values())
                        if (type.getDesc().equals(cart.type))
                            ProductDetailActivity.startActivity(OrderDetailActivity.this, type, cart.productId, true);
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

        Cart cart = mAdapter.getItem(0);
        Address address = null;
        if (cart != null) {

            Order order = DBManager.getInstance().getOrder(cart.orderId);
            if (order != null)
                address = order.address;
        }
        if (address != null) {// 有默认收货地址

            View vHeader = ViewUtil.inflateLayout(R.layout.view_order_header);
            EditText etPersion = (EditText) vHeader.findViewById(R.id.etPersion);
            EditText etPhone = (EditText) vHeader.findViewById(R.id.etTel);
            EditText etAddress = (EditText) vHeader.findViewById(R.id.etAddress);
            etPersion.setEnabled(false);
            etPhone.setEnabled(false);
            etAddress.setEnabled(false);

            etPersion.setText(address.name);
            etPhone.setText(address.phone);
            String str = "[默认] ";
            SpannableString ss = new SpannableString(str + address.address);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_title_bar)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            etAddress.setText(ss);

            lvOrder.addHeaderView(vHeader);
        }
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

    public static void startActivity(Activity act, long cartId) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderDetailActivity.class);
        intent.putExtra("cartId", cartId);
        act.startActivity(intent);
    }
}
