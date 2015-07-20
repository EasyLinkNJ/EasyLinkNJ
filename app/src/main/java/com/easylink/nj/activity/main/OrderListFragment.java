package com.easylink.nj.activity.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
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
            return false;
        }

        switchContent(0);

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

            mLvOrder = (ListView) findViewById(R.id.lv);
            mLvOrder.setHeaderDividersEnabled(true);
            mLvOrder.setFooterDividersEnabled(true);
            mLvOrder.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
            mLvOrder.setDividerHeight(2);//2px

            View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
            mLvOrder.addHeaderView(headerView);
            EditText etPersion = (EditText) headerView.findViewById(R.id.etPersion);
            etPersion.setText(mOrder.user.name);
            etPersion.setEnabled(false);
            EditText etPhone = (EditText) headerView.findViewById(R.id.etTel);
            etPhone.setText(mOrder.user.phone);
            etPhone.setEnabled(false);
            EditText etAddress = (EditText) headerView.findViewById(R.id.etAddress);
            etAddress.setText(mOrder.user.address);
            etAddress.setEnabled(false);

            View footerView = new View(getActivity());
            footerView.setMinimumHeight(DensityUtil.dip2px(10));
            mLvOrder.addFooterView(footerView);

            mLvOrder.addFooterView(getFooterView());

            mLvOrder.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }
    }

    private View getFooterView() {

        TextView v = new TextView(getActivity());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(42));
        v.setText("提醒客服处理");
        v.setBackgroundColor(getResources().getColor(R.color.bg_red));
        v.setTextColor(getResources().getColor(R.color.white_trans87));
        v.setGravity(Gravity.CENTER);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        v.setLayoutParams(lp);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast("已发送提醒");
            }
        });
        return v;
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
