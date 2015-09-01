package com.easylink.nj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.adapter.OnItemViewLongClickListener;
import com.easylink.library.util.LogMgr;
import com.easylink.nj.EasyApplication;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.activity.product.ProductDetailActivity.ProductType;
import com.easylink.nj.adapter.OrderSectionAdapter;
import com.easylink.nj.bean.OrderData;
import com.easylink.nj.bean.OrderList;
import com.easylink.nj.bean.OrderList.Orders;
import com.easylink.nj.bean.OrderList.Orders.Item;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;
import com.easylink.nj.httptask.NjJsonResponse;
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.ListTitleDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListActivity extends NjHttpActivity<Order> {

    private ListView mLvOrder;
    private OrderSectionAdapter mAdapter;
    private TextView mTvBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
        loadDataFromServer();
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("我的订单");
    }

    @Override
    protected void initContentView() {

        mLvOrder = (ListView) findViewById(R.id.lvCart);
        mLvOrder.setDivider(null);
        mLvOrder.setAdapter(mAdapter);

        mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
        mTvBottomBar.setText("提醒客服处理");
        mTvBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast("已发送提醒");
            }
        });
    }

    private void loadDataFromServer() {

        String userToken = EasyApplication.getCommonPrefs().getUserToken();
        executeHttpTask(101, NjHttpUtil.getMyOrder(userToken), new NjJsonListener<OrderList>(OrderList.class) {

            @Override
            public void onTaskPre() {

                switchLoading(0);
            }

            @Override
            public void onTaskResult(OrderList result) {

                try {

                    int sum = Integer.valueOf(result.getSum());
                    if (sum == 0) {

                        switchDisable(R.mipmap.ic_order_nothing);
                        hideView(mTvBottomBar);
                    } else if (sum > 0) {

                        List<OrderData> orderDatas = new ArrayList<>(sum);
                        OrderData orderData;
                        for (Orders orders : result.getList()) {// 遍历订单

                            orderData = new OrderData();
                            orderData.orderId = orders.getId();
                            orderData.time = Long.valueOf(orders.getCreated()) * 1000;
                            orderData.itemNum = orders.getList().size();
                            orderData.name = orders.getName();
                            orderData.phone = orders.getPhone();
                            orderData.address = orders.getAddress();
                            orderDatas.add(orderData);

                            for (Item item : orders.getList()) {// 遍历产品

                                orderData = new OrderData();
                                orderData.productId = item.getCmoddata().getId();
                                orderData.title = item.getTitle();
                                orderData.imgUrl = item.getMainpic();
                                orderData.price = item.getPrice();
                                orderData.num = Integer.valueOf(item.getNum());
                                orderData.type = item.getModname();
                                orderDatas.add(orderData);
                            }
                            orderData = new OrderData();
                            orderData.money = orders.getMoney();
                            orderDatas.add(orderData);
                        }
                        switchContent(0);
                        showView(mTvBottomBar);
                        mAdapter.setData(orderDatas);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                switchFailed(0, failedCode, msg);
            }
        });
    }

    @Override
    public void onTipViewFailedClick() {

        loadDataFromServer();
    }

    @Override
    protected void initData() {

        mAdapter = new OrderSectionAdapter(true);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                OrderData data = mAdapter.getItem(position);
                if (data == null)
                    return;

                int vid = clickView.getId();
                if (vid == R.id.tvName) {

                    ArrayList<OrderData> datas = new ArrayList<>();
                    for (int i = position; i < position + data.itemNum + 2; i++) {// 加上头和尾

                        datas.add(mAdapter.getItem(i));
                    }
                    OrderDetailActivity.startActivity(OrderListActivity.this, datas);
                } else if (vid == R.id.rlRootView) {

                    for (ProductType type : ProductType.values())
                        if (type.getDesc().equals(data.type))
                            ProductDetailActivity.startActivity(OrderListActivity.this, type, data.productId, true);
                }
            }
        });
        mAdapter.setOnItemViewLongClickListener(new OnItemViewLongClickListener() {

            @Override
            public void onItemViewLongClick(final int position, View clickView) {

                ListTitleDialog dialog = DialogUtil.getListTitleDialog(OrderListActivity.this, true, new ListTitleDialog.OnItemClickListener() {

                    @Override
                    public void onItemClick(Dialog dialog, int index) {

                        dialog.dismiss();
                        executeDelTask(mAdapter.getItem(position).orderId);

                        LogMgr.e("daisw", "@@" + position);
                    }
                });
                dialog.show();
            }
        });
    }

    private void executeDelTask(String orderId) {

        String userToken = EasyApplication.getCommonPrefs().getUserToken();
        executeHttpTask(102, NjHttpUtil.getOrderDel(orderId, userToken), new NjJsonListener<String>(String.class) {

            @Override
            public NjJsonResponse<String> onTaskResponse(String jsonText) {

                NjJsonResponse<String> resp = new NjJsonResponse<>();
                if (TextUtils.isEmpty(jsonText))
                    return resp;

                try {

                    JSONObject jsonObject = new JSONObject(jsonText);
                    jsonObject = jsonObject.getJSONObject("data");
                    resp.setData(jsonObject.getString("id"));
                } catch (Exception e) {

                    e.printStackTrace();
                }
                return resp;
            }

            @Override
            public void onTaskSuccess(NjJsonResponse<String> resp) {

                for (int i = 0; i < mAdapter.getCount(); i++) {

                    OrderData data = mAdapter.getItem(i);
                    if (data != null && data.orderId.equals(resp.getData())) {

                        for (int j = i; j < i + data.itemNum + 2; j++) {// 加上头和尾

                            mAdapter.remove(j);
                            LogMgr.e("daisw", "~~" + j);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
                showToast("删除成功");
            }

            @Override
            public void onTaskResult(String result) {
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {
            }
        });
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
