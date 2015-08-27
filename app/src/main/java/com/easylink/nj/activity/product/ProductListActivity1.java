package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.Product;
import com.easylink.nj.bean.product.ProductItem;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * 产品列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListActivity1 extends NjHttpXlvActivity<ProductList> {

    private String mCateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        super.initData();
        mCateId = TextUtil.filterNull(getIntent().getStringExtra("cateId"));
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        final ProductListAdapter adapter = new ProductListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                ProductItem product = adapter.getItem(position);
                if (product != null)
                    ProductDetailActivity.startActivityFromNJ(ProductListActivity1.this, "1"/*product.getId()*/, false);
            }
        });

        return adapter;
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("产品列表");
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductListByCateId(page, limit, mCateId);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductList result) {

        return result == null ? null : result.getList();
    }

    public static void startActivity(Activity act, String cateId) {

        Intent intent = new Intent(act, ProductListActivity1.class);
        intent.putExtra("cateId", cateId);
        act.startActivity(intent);
    }
}
