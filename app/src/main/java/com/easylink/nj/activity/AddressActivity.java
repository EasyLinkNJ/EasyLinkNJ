package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.AddressListAdapter;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/22.
 */
public class AddressActivity extends NjHttpActivity<Address> {

    private List<Address> mAddresses;
    private ListView mLvAddress;
    private AddressListAdapter mAdapter;
    private TextView mTvTitlebarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || resultCode != Activity.RESULT_OK)
            return;
        boolean isChanged = data.getBooleanExtra("isChanged", false);
        if (isChanged) {

            mAddresses = DBManager.getInstance().getAddresses();
            mAdapter.setData(mAddresses);
            mAdapter.notifyDataSetChanged();
        }
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

                AddressEditActivity.startActivityForResult(AddressActivity.this, 0);
            }
        });
    }

    @Override
    protected void initContentView() {

        if (mAddresses == null || mAddresses.isEmpty()) {

            switchDisable(R.mipmap.ic_address_nothing);
        } else {

//            mTvTitlebarRight.setText("管理");
            mLvAddress = (ListView) findViewById(R.id.lvAddress);
            mAdapter = new AddressListAdapter();
            mAdapter.setData(mAddresses);
            mLvAddress.setAdapter(mAdapter);
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
