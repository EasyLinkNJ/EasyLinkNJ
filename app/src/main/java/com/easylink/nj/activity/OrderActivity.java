package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.TextUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.OrderListAdapter;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.utils.DBManager;
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.BaseDialog;
import com.easylink.nj.view.BaseDialog.OnViewClickListener;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderActivity extends NjHttpActivity<Order> {

    private OrderListAdapter mAdapter;
    private TextView mTvTitle, mTvBottomBar;
    private EditText mEtPersion, mEtPhone, mEtAddress;
    private boolean isConfirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || data == null)
            return;

        long addressId = data.getLongExtra("addressId", -1);
        if (addressId != -1) {

            Address address = DBManager.getInstance().getAddress(addressId);
            if (address != null) {

                mEtPersion.setText(address.name);
                mEtPhone.setText(address.phone);
                mEtAddress.setText(address.address);
            }
        }
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        if (carts == null || carts.isEmpty()) {

            switchDisable(R.mipmap.ic_order_nothing);
            hideView(mTvBottomBar);

            return;
        }

        switchContent(0);
        showView(mTvBottomBar);

        mAdapter = new OrderListAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                onItemViewClickCallback(position, clickView);
            }
        });
    }

    private void onItemViewClickCallback(int position, View clickView) {

        Cart cart = mAdapter.getItem(position);

        int vId = clickView.getId();
        if (vId == R.id.rlRootView) {// convert view

            ProductDetailActivity.startActivity(OrderActivity.this, cart.productId, true);
        } else if (vId == R.id.ivAdd) {// add view

            if (isConfirmed)
                return;

            cart.count = cart.count + 1;
            mAdapter.notifyDataSetChanged();
        } else if (vId == R.id.ivDelete) {// delete view

            if (isConfirmed)
                return;

            cart.count = cart.count - 1;
            if (cart.count == 0)
                mAdapter.remove(cart);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initTitleView() {

        mTvTitle = addTitleMiddleTextViewWithBack("订单");
    }

    @Override
    protected void initContentView() {

        ListView lvOrder = (ListView) findViewById(R.id.lvCart);

        View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
        mEtPersion = (EditText) headerView.findViewById(R.id.etPersion);
        mEtPhone = (EditText) headerView.findViewById(R.id.etTel);
        mEtAddress = (EditText) headerView.findViewById(R.id.etAddress);

        mEtPersion.setEnabled(false);
        mEtPhone.setEnabled(false);
        mEtAddress.setEnabled(false);

//        mEtPersion.setTextColor(getResources().getColor(R.color.black_trans54));
//        mEtPhone.setTextColor(getResources().getColor(R.color.black_trans54));
//        mEtAddress.setTextColor(getResources().getColor(R.color.black_trans54));

        Address address = DBManager.getInstance().getDefaultAddress();
        if (address != null) {

            mEtPersion.setText(address.name);
            mEtPhone.setText(address.phone);
            String str = "[默认] ";
            SpannableString ss = new SpannableString(str + address.address);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_title_bar)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mEtAddress.setText(ss);
        }
        View headerOther = ViewUtil.inflateLayout(R.layout.view_order_header_other);
        headerOther.findViewById(R.id.tvSwitchAddress).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddressActivity.startActivityForResult(OrderActivity.this, 0);
            }
        });
        lvOrder.addHeaderView(headerOther);
        lvOrder.addHeaderView(headerView);

        lvOrder.setAdapter(mAdapter);

        mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
        mTvBottomBar.setText("确认");
        mTvBottomBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!isConfirmed) {

                    if (!isPersionUsable()) {

                        showToast("请填写正确的收货人信息");
                    } else if (!isPhoneUsable()) {

                        showToast("请填写正确的电话号码");
                    } else if (!isAddressUsable()) {

                        showToast("请填写详细的收货地址");
                    } else {

                        showConfirmDialog();
                    }
                } else {

                    showToast("已发送提醒");
                }
            }
        });
    }

    private void showConfirmDialog() {

        DialogUtil.getOrderConfirmDialog(OrderActivity.this, new OnViewClickListener() {

            @Override
            public void onViewClick(BaseDialog dialog, View v) {

                isConfirmed = true;

                saveOrderInfo();
                refreshView();

                dialog.dismiss();
            }
        }).show();
    }

    private void refreshView() {

        mTvTitle.setText("订单详情");
        mTvBottomBar.setText("提醒客服处理");
        mEtPersion.setEnabled(false);
        mEtPhone.setEnabled(false);
        mEtAddress.setEnabled(false);
    }

    private boolean isPersionUsable() {

        Editable name = mEtPersion.getText();
        int length = name.length();
        return TextUtil.isNotEmpty(name) && length > 1 && length <= 4;
    }

    private boolean isPhoneUsable() {

        return TextUtil.isMobile(mEtPhone.getText());
    }

    private boolean isAddressUsable() {

        return TextUtil.isNotEmpty(mEtAddress.getText()) && mEtAddress.length() > 5;
    }

    private void saveOrderInfo() {

        List<Address> addresses = DBManager.getInstance().getAddresses();
        boolean isAddressEmpty = addresses == null || addresses.isEmpty();

        // 保存收货地址
        Address address = new Address();
        address.name = mEtPersion.getText().toString();
        address.phone = mEtPhone.getText().toString();
        address.address = mEtAddress.getText().toString();
        address.isDefault = isAddressEmpty;
        address.save();

        // 保存订单信息
        Order order = new Order();
        order.time = System.currentTimeMillis();
        order.orderId = String.valueOf(order.time);
        order.address = address;
        order.save();

        for (Cart cart : mAdapter.getData()) {

            cart.orderId = order.orderId;
            cart.save();
        }
    }

    @Override
    public void invalidateContent(int what, Order order) {

    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderActivity.class);
        act.startActivity(intent);
    }
}
