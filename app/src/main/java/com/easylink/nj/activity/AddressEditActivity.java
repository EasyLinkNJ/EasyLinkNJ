package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.db.Address;

/**
 * Created by KEVIN.DAI on 15/7/22.
 */
public class AddressEditActivity extends NjHttpActivity<Address> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address_edit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("添加地址");
    }

    @Override
    protected void initContentView() {

    }

    @Override
    public void invalidateContent(int what, Address address) {

    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, AddressEditActivity.class);
        act.startActivity(intent);
    }
}
