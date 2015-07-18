package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;
import com.easylink.nj.adapter.CartAdapter;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class OrderActivity extends NjActivity {

    private CartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(ViewUtil.getCleanListView(this, R.id.lv));
    }

    @Override
    protected void initData() {

        List<Cart> carts = DBManager.getInstance().getCarts();
        mAdapter = new CartAdapter();
        mAdapter.setData(carts);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

            }
        });
        ListView lv = (ListView) findViewById(R.id.lv);
        View v = new View(this);
        v.setMinimumHeight(DensityUtil.dip2px(8));
        lv.addFooterView(v);
        lv.setAdapter(mAdapter);
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("购物车");
        addTitleRightTextView("下单", new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    protected void initContentView() {

    }

    public static void startActivity(Activity act) {

        if (act == null)
            return;

        Intent intent = new Intent(act, OrderActivity.class);
        act.startActivity(intent);
    }
}
