package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.easylink.library.util.LogMgr;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.product.ProductDetail;
import com.easylink.nj.httptask.NjHttpUtil;

/**
 * 产品详情
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductDetailActivity extends NjHttpActivity<ProductDetail> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_detail);
        loadDataFromServer();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("产品详情");
    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected void onTipViewClick() {

        loadDataFromServer();
    }

    private void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductDetail(getIntent().getStringExtra("productId")), ProductDetail.class);
    }

    @Override
    public void invalidateContent(int what, ProductDetail productDetail) {

        LogMgr.i("daisw", "~~" + productDetail.getName());
    }

    public static void startActivity(Activity act, String productId) {

        if (act == null)
            return;
        Intent intent = new Intent(act, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        act.startActivity(intent);
    }
}
