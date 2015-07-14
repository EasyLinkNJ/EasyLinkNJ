package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.easylink.library.util.LogMgr;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

/**
 * 产品列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListActivity extends NjHttpActivity<ProductList> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_list);
        loadDataFromServer();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("产品列表");
    }

    @Override
    protected void initContentView() {

    }

    private void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductList(), ProductList.class);
    }

    @Override
    public void invalidateContent(int what, ProductList productList) {

        LogMgr.i("daisw", "~~" + productList.getSum());
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;
        Intent intent = new Intent(act, ProductListActivity.class);
        act.startActivity(intent);
    }
}
