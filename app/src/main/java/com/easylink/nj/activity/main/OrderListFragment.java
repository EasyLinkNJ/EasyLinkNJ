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
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderListFragment extends NjHttpFragment<ProductList> {

    private CartListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
    }

    @Override
    public void onResume() {

        super.onResume();
        showToast("onResume");
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        mAdapter = new CartListAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                Cart cart = mAdapter.getItem(position);
                ProductDetailActivity.startActivity(getActivity(), cart.productId, true);
            }
        });
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setHeaderDividersEnabled(true);
        lv.setFooterDividersEnabled(true);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        lv.setDividerHeight(2);//2px

        View headerView = ViewUtil.inflateLayout(R.layout.view_order_header);
        lv.addHeaderView(headerView);
        View footerView = new View(getActivity());
        footerView.setMinimumHeight(DensityUtil.dip2px(10));
        lv.addFooterView(footerView);
        lv.addFooterView(getFooterView());
        lv.setAdapter(mAdapter);

        EditText etPersion = (EditText) headerView.findViewById(R.id.etPersion);
        etPersion.setEnabled(false);
        EditText etPhone = (EditText) headerView.findViewById(R.id.etTel);
        etPhone.setEnabled(false);
        EditText etAddress = (EditText) headerView.findViewById(R.id.etAddress);
        etAddress.setEnabled(false);
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
    public void invalidateContent(int what, ProductList productList) {

    }

    public static OrderListFragment newInstance(FragmentActivity activity) {

        return (OrderListFragment) Fragment.instantiate(activity, OrderListFragment.class.getName(), new Bundle());
    }
}
