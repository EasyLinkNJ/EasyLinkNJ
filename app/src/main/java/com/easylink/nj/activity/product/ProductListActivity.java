package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.Product;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

/**
 * 产品列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListActivity extends NjHttpActivity<ProductList> {

    private ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(ViewUtil.getCleanListView(this, R.id.lv));
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        mAdapter = new ProductListAdapter();
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

                Product product = mAdapter.getItem(position);
                if (product != null)
                    ProductDetailActivity.startActivity(ProductListActivity.this, product.getId());
            }
        });
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("产品列表");
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        lv.setDividerHeight(2);//2px
        lv.setAdapter(mAdapter);
    }

    @Override
    protected void onTipViewClick() {

        loadDataFromServer();
    }

    private void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductList(), ProductList.class);
    }

    @Override
    public void invalidateContent(int what, ProductList data) {

        mAdapter.setData(data.getList());
        mAdapter.notifyDataSetChanged();
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;
        Intent intent = new Intent(act, ProductListActivity.class);
        act.startActivity(intent);
    }
}
