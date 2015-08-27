package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.adapter.CartListAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartActivity extends NjHttpActivity<Cart> {

    private ListView mLvCarts;
    //    private CartGridAdapter mAdapter;
    private CartListAdapter mAdapter;
    private TextView mTvBottomBar;

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

            mAdapter = new CartListAdapter();
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView) {

                    onItemViewClickCallback(position, clickView);
                }
            });
        }
        mAdapter.setData(carts);

        return true;
    }

    private void onItemViewClickCallback(int position, View clickView) {

        Cart cart = mAdapter.getItem(position);

        int vId = clickView.getId();
        if (vId == R.id.rlRootView) {// convert view

            ProductDetailActivity.startActivityFromNJ(this, cart.productId, true);
        } else if (vId == R.id.ivAdd) {// add view

            cart.count = cart.count + 1;
            cart.save();
            mAdapter.notifyDataSetChanged();
        } else if (vId == R.id.ivDelete) {// delete view

            cart.count = cart.count - 1;
            if (cart.count == 0) {

                mAdapter.remove(cart);
                hideView(mTvBottomBar);
                switchDisable(R.mipmap.ic_cart_nothing);
                DBManager.getInstance().clearCart();
            } else {

                cart.save();
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private void fillData2View() {

        if (mLvCarts == null) {

            mLvCarts = (ListView) findViewById(R.id.lvCart);
            mLvCarts.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }

        if (mTvBottomBar == null) {

            mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
            mTvBottomBar.setText("提交订单");
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
    public boolean invalidateContent(int what, Cart cart) {

        return true;
    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, CartActivity.class);
        act.startActivity(intent);
    }
}
