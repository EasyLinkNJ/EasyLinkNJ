package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/22.
 */
public class AddressActivity extends NjHttpActivity<Address> {

    private List<Address> mAddresses;
    private TextView mTvTitlebarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
    }

    @Override
    protected void initData() {

        mAddresses = DBManager.getInstance().getAddresses();
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("我的地址");
        mTvTitlebarRight = addTitleRightTextView("添加", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddressEditActivity.startActivity(AddressActivity.this);
            }
        });
    }

    @Override
    protected void initContentView() {

        if (mAddresses == null || mAddresses.isEmpty()) {

            switchDisable(R.mipmap.ic_address_nothing);
        } else {

//            mTvTitlebarRight.setText("管理");
        }
    }

    @Override
    public void invalidateContent(int what, Address address) {

    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, AddressActivity.class);
        act.startActivity(intent);
    }
}
