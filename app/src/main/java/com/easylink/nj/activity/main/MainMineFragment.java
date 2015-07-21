package com.easylink.nj.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjFragment;

/**
 * 产品分类列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class MainMineFragment extends NjFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(R.layout.act_main_fmt_mine);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initContentView() {

        TextView tv = null;

        tv = (TextView) findViewById(R.id.tvOrderList);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //全部订单
            }
        });

        tv = (TextView) findViewById(R.id.tvAddress);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //收货地址
            }
        });

        tv = (TextView) findViewById(R.id.tvRepair);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //维修咨询
            }
        });

        tv = (TextView) findViewById(R.id.tvAbout);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //关于
            }
        });
    }

    public static MainMineFragment newInstance(FragmentActivity activity) {

        return (MainMineFragment) Fragment.instantiate(activity, MainMineFragment.class.getName(), new Bundle());
    }
}
