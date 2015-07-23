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
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.BaseDialog;
import com.easylink.nj.view.BaseDialog.OnViewClickListener;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderActivity extends NjHttpActivity<Order> {

    private OrderAdapter mAdapter;
    private TextView mTvTitle, mTvBottomBar;
    private EditText mEtPersion, mEtPhone, mEtAddress;
    private boolean isConfirmed = false;
    private Address mAddress;

    private ListView mLvOrder;
    private View mAddressInfoHeader;
    private boolean isHeaderAdded;
    private TextView mTvHeaderTitle;

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

            mAddress = DBManager.getInstance().getAddress(addressId);
            if (mAddress != null) {

                if (!isHeaderAdded) {

                    mLvOrder.addHeaderView(mAddressInfoHeader);
                    isHeaderAdded = true;

                    setHeaderUpdateState();
                }

                mEtPersion.setText(mAddress.name);
                mEtPhone.setText(mAddress.phone);
                mEtAddress.setText(mAddress.address);
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

        mAdapter = new OrderAdapter();
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
        if (clickView.getId() == R.id.rlRootView)// convert view
            ProductDetailActivity.startActivity(OrderActivity.this, cart.productId, true);
    }

    @Override
    protected void initTitleView() {

        mTvTitle = addTitleMiddleTextViewWithBack("订单");
    }

    @Override
    protected void initContentView() {

        mLvOrder = (ListView) findViewById(R.id.lvCart);

        mAddressInfoHeader = ViewUtil.inflateLayout(R.layout.view_order_header);
        mEtPersion = (EditText) mAddressInfoHeader.findViewById(R.id.etPersion);
        mEtPhone = (EditText) mAddressInfoHeader.findViewById(R.id.etTel);
        mEtAddress = (EditText) mAddressInfoHeader.findViewById(R.id.etAddress);
        mEtPersion.setEnabled(false);
        mEtPhone.setEnabled(false);
        mEtAddress.setEnabled(false);

        View headerOther = ViewUtil.inflateLayout(R.layout.view_order_header_other);
        mTvHeaderTitle = (TextView) headerOther.findViewById(R.id.tvSwitchAddress);
        mLvOrder.addHeaderView(headerOther);

        mAddress = DBManager.getInstance().getDefaultAddress();
        if (mAddress != null) {// 有默认收货地址

            mEtPersion.setText(mAddress.name);
            mEtPhone.setText(mAddress.phone);
            String str = "[默认] ";
            SpannableString ss = new SpannableString(str + mAddress.address);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_title_bar)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mEtAddress.setText(ss);

            mLvOrder.addHeaderView(mAddressInfoHeader);
            isHeaderAdded = true;

            setHeaderUpdateState();
        } else {// 没有默认收货地址

            setHeaderAddState();
        }
        mLvOrder.setAdapter(mAdapter);

        mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
        mTvBottomBar.setText("确认");
        mTvBottomBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!isConfirmed) {

                    if (isHeaderAdded)// 表示已添加地址
                        showConfirmDialog();
                    else
                        showToast("请添加收货地址");
                } else {

                    showToast("已发送提醒");
                }
            }
        });
    }

    private void setHeaderUpdateState() {

        mTvHeaderTitle.setText("更换地址");
        mTvHeaderTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddressActivity.startActivityForResult(OrderActivity.this, 0, mAddress == null ? -1 : mAddress.getId());
            }
        });
    }

    private void setHeaderAddState() {

        mTvHeaderTitle.setText("添加地址");
        mTvHeaderTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddressEditActivity.startActivityForResult(OrderActivity.this, 1);
            }
        });
    }

    private void showConfirmDialog() {

        DialogUtil.getOrderConfirmDialog(OrderActivity.this, new OnViewClickListener() {

            @Override
            public void onViewClick(BaseDialog dialog, View v) {

                mLvOrder.removeHeaderView(mTvHeaderTitle);
                isHeaderAdded = false;

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

    private void saveOrderInfo() {

        // 保存订单信息
        Order order = new Order();
        order.time = System.currentTimeMillis();
        order.orderId = String.valueOf(order.time);
        order.address = mAddress;
        order.save();

        for (Cart cart : mAdapter.getData()) {

            cart.orderId = order.orderId;
            cart.save();
        }
    }

    @Override
    public boolean invalidateContent(int what, Order order) {

        return true;
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderActivity.class);
        act.startActivity(intent);
    }
}
