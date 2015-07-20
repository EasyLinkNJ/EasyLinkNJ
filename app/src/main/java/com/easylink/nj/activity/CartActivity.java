package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.CartGridAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartActivity extends NjHttpActivity<Cart> {

    private ListView mLvCarts;
    private CartGridAdapter mAdapter;

    private View mTvBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (getData())
            fillData2View();
    }

    private boolean getData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        if (carts == null || carts.isEmpty()) {

            switchDisable(R.mipmap.ic_cart_nothing);
            hideView(mTvBottomBar);
            return false;
        }

        switchContent(0);
        showView(mTvBottomBar);

        if (mAdapter == null) {

            mAdapter = new CartGridAdapter();
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView) {

                }
            });
        }
        mAdapter.setData(carts);

        return true;
    }

    private void fillData2View() {

        if (mLvCarts == null) {

            mLvCarts = (ListView) findViewById(R.id.lvCart);

            View v = new View(this);
            v.setMinimumHeight(DensityUtil.dip2px(7));
            mLvCarts.addFooterView(v);

            mLvCarts.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }

        if (mTvBottomBar == null) {

            mTvBottomBar = findViewById(R.id.tvBottomBar);
            mTvBottomBar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    OrderActivity.startActivity(CartActivity.this);
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("购物车");
    }

    @Override
    protected void initContentView() {

    }

    @Override
    public void invalidateContent(int what, Cart cart) {

    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, CartActivity.class);
        act.startActivity(intent);
    }
}
