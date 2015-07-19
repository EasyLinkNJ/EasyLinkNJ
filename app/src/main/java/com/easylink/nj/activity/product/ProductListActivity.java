package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.Product;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * 产品列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListActivity extends NjHttpXlvActivity<ProductList> {

    @Override
    public ExAdapter getAdapter() {

        final ProductListAdapter adapter = new ProductListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                Product product = adapter.getItem(position);
                if (product != null)
                    ProductDetailActivity.startActivity(ProductListActivity.this, product.getId());
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

        return NjHttpUtil.getProductList(page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductList result) {

        return result == null ? null : result.getList();
    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, ProductListActivity.class);
        act.startActivity(intent);
    }
}
