package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjFragmentActivity;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongjiActivity extends NjFragmentActivity implements OnItemViewClickListener, View.OnClickListener{

    private TextView mTvBrand, mTvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_nongji_list);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("农业机械");
    }

    @Override
    protected void initContentView() {

        initTabViews();
        addFragment(R.id.flContent, ProductListNongjiFragment.newInstance(this));
    }

    private void initTabViews() {

        mTvBrand = (TextView) findViewById(R.id.tvBrand);
        mTvBrand.setOnClickListener(this);
        mTvCategory = (TextView) findViewById(R.id.tvCategory);
        mTvCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductDetailActivity.startActivityFromNJ(this, "1"/*product.getId()*/, false);
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, ProductListNongjiActivity.class);
        activity.startActivity(intent);
    }
}
