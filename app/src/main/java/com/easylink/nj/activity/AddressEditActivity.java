package com.easylink.nj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.utils.DBManager;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/22.
 */
public class AddressEditActivity extends NjHttpActivity<Address> {

    private EditText mEtPersion, mEtPhone, mEtAddress;
    private TextView mTvBottomBar;

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

        mEtPersion = (EditText) findViewById(R.id.etPersion);
        mEtPhone = (EditText) findViewById(R.id.etTel);
        mEtAddress = (EditText) findViewById(R.id.etAddress);

        mTvBottomBar = (TextView) findViewById(R.id.tvBottomBar);
        mTvBottomBar.setText("确认");
        mTvBottomBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!isPersionUsable()) {

                    showToast("请填写正确的收货人信息");
                } else if (!isPhoneUsable()) {

                    showToast("请填写正确的电话号码");
                } else if (!isAddressUsable()) {

                    showToast("请填写详细的收货地址");
                } else {

                    List<Address> addresses = DBManager.getInstance().getAddresses();
                    boolean isAddressEmpty = addresses == null || addresses.isEmpty();

                    // 保存收货地址
                    Address address = new Address();
                    address.name = mEtPersion.getText().toString();
                    address.phone = mEtPhone.getText().toString();
                    address.address = mEtAddress.getText().toString();
                    address.isDefault = isAddressEmpty;
                    address.save();

                    showToast("已保存");

                    Intent intent = new Intent();
                    intent.putExtra("isChanged", true);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean isPersionUsable() {

        Editable name = mEtPersion.getText();
        int length = name.length();
        return TextUtil.isNotEmpty(name) && length > 1 && length <= 4;
    }

    private boolean isPhoneUsable() {

        return TextUtil.isMobile(mEtPhone.getText());
    }

    private boolean isAddressUsable() {

        return TextUtil.isNotEmpty(mEtAddress.getText()) && mEtAddress.length() > 5;
    }

    @Override
    public void invalidateContent(int what, Address address) {

    }

    public static void startActivityForResult(Activity act, int requestCode) {

        Intent intent = new Intent(act, AddressEditActivity.class);
        act.startActivityForResult(intent, requestCode);
    }
}
