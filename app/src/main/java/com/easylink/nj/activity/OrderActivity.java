package com.easylink.nj.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.LogMgr;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.activity.product.ProductDetailActivity.ProductType;
import com.easylink.nj.adapter.OrderAdapter;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.bean.product.PostOrder;
import com.easylink.nj.utils.DBManager;
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.BaseDialog;
import com.easylink.nj.view.BaseDialog.OnViewClickListener;
import com.easylink.nj.view.ConfirmTitleDialog;

import java.util.ArrayList;
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

        if (resultCode != Activity.RESULT_OK || data == null)
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

                Cart cart = mAdapter.getItem(position);
                if (cart != null)
                    for (ProductType type : ProductType.values())
                        if (type.getDesc().equals(cart.type))
                            ProductDetailActivity.startActivity(OrderActivity.this, type, cart.productId, true);
            }
        });
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

        ConfirmTitleDialog confirmDialog = DialogUtil.getOrderConfirmDialog(OrderActivity.this, new OnViewClickListener() {

            @Override
            public void onViewClick(BaseDialog dialog, View v) {

                isConfirmed = true;

                mLvOrder.removeHeaderView(mTvHeaderTitle);
                isHeaderAdded = false;

                saveOrderInfo();
                refreshView();

                dialog.dismiss();
            }
        });
        confirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        confirmDialog.show();
    }

    private void saveOrderInfo() {

        // 保存订单信息
        Order order = new Order();
        order.orderId = String.valueOf(order.time);
        order.time = System.currentTimeMillis();
        order.address = mAddress;
        order.carts = mAdapter.getData();
        order.save();

        for (Cart cart : order.carts) {

            cart.orderId = order.orderId;
            cart.save();
        }

        postOrderInfo(order);
    }

    private void refreshView() {

        mTvTitle.setText("订单详情");
        mTvBottomBar.setText("提醒客服处理");
    }

    private void postOrderInfo(Order order) {

        try {
            PostOrder postOrder = new PostOrder();
            List<PostOrder.OrderItem> orderItems = new ArrayList<>();
            PostOrder.OrderItem orderItem;
            for (Cart cart : order.carts) {

                orderItem = new PostOrder.OrderItem();
                orderItem.setModid(cart.productId);
                orderItem.setModname(cart.type);
                orderItem.setNum(String.valueOf(cart.count));
                orderItems.add(orderItem);
            }
            postOrder.setOrderjs(orderItems);
            String json = JSON.toJSONString(postOrder);

            if (LogMgr.isDebug())
                LogMgr.e("daisw", "post order json: " + json);
        } catch (Exception e) {

            e.printStackTrace();
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
