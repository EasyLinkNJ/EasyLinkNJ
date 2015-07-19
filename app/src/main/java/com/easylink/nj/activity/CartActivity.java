package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;
import com.easylink.nj.adapter.CartGridAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartActivity extends NjActivity {

    private CartGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        mAdapter = new CartGridAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

            }
        });
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("购物车");
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lvCart);
        View v = new View(this);
        v.setMinimumHeight(DensityUtil.dip2px(7));
        lv.addFooterView(v);
        lv.setAdapter(mAdapter);

        findViewById(R.id.tvBottomBar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                OrderActivity.startActivity(CartActivity.this);
            }
        });
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, CartActivity.class);
        act.startActivity(intent);
    }
}
