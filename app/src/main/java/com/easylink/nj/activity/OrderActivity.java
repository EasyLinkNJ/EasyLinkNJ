package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.CartListAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.utils.DBManager;
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.BaseDialog;
import com.easylink.nj.view.BaseDialog.OnViewClickListener;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderActivity extends NjActivity {

    private CartListAdapter mAdapter;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(ViewUtil.getCleanListView(this, R.id.lv));
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        mAdapter = new CartListAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                onItemViewClickCallback(position, clickView);
            }
        });

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        lv.setDividerHeight(2);//2px
        View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
        lv.addHeaderView(headerView);
        View footerView = new View(this);
        footerView.setMinimumHeight(DensityUtil.dip2px(8));
        lv.addFooterView(footerView);
        lv.setAdapter(mAdapter);
    }

    private void onItemViewClickCallback(int position, View clickView) {

        Cart cart = mAdapter.getItem(position);

        int vId = clickView.getId();
        if (vId == R.id.rlRootView) {// convert view

            ProductDetailActivity.startActivity(OrderActivity.this, cart.productId, true);
        } else if (vId == R.id.ivAdd) {// add view

            cart.count = cart.count + 1;
            mAdapter.notifyDataSetChanged();

        } else if (vId == R.id.ivDelete) {// delete view

            cart.count = cart.count - 1;
            if (cart.count == 0) {

                mAdapter.remove(cart);
            }
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void initTitleView() {

        mTvTitle = addTitleMiddleTextViewWithBack("订单");
        addTitleRightTextView("确认", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogUtil.getOrderConfirmDialog(OrderActivity.this, new OnViewClickListener() {

                    @Override
                    public void onViewClick(BaseDialog dialog, View v) {

                        dialog.dismiss();
                        // TODO 更换状态
                    }
                }).show();
            }
        });
    }

    @Override
    protected void initContentView() {

    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderActivity.class);
        act.startActivity(intent);
    }
}
