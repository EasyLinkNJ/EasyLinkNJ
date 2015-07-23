package com.easylink.nj.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.adapter.OnItemViewLongClickListener;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.AddressListAdapter;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.utils.DBManager;
import com.easylink.nj.utils.DialogUtil;
import com.easylink.nj.view.ListTitleDialog;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/22.
 */
public class AddressActivity extends NjHttpActivity<Address> {

    private List<Address> mAddresses;
    private AddressListAdapter mAdapter;
    private boolean isFromOrder;
    private long mAddressId;

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

            switchContent(0);

            if (isFromOrder) {

                setResult(Activity.RESULT_OK, data);
                finish();
            }
        }
    }

    @Override
    protected void initData() {

        mAddresses = DBManager.getInstance().getAddresses();
        isFromOrder = getIntent().getBooleanExtra("isFromOrder", false);
        mAddressId = getIntent().getLongExtra("addressId", -1);
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("我的地址");
        addTitleRightTextView("添加", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddressEditActivity.startActivityForResult(AddressActivity.this, 0);
            }
        });
    }

    @Override
    protected void initContentView() {

        ListView lvAddress = (ListView) findViewById(R.id.lvAddress);
        mAdapter = new AddressListAdapter();
        mAdapter.setSelectId(mAddressId);
        mAdapter.setData(mAddresses);
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                if (isFromOrder) {

                    Intent intent = new Intent();
                    intent.putExtra("addressId", mAdapter.getItem(position).getId().longValue());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {

                    AddressEditActivity.startActivityForResult(AddressActivity.this, 1, mAdapter.getItem(position).getId());
                }
            }
        });
        if (!isFromOrder)
            mAdapter.setOnItemViewLongClickListener(new OnItemViewLongClickListener() {

                @Override
                public void onItemViewLongClick(int position, View clickView) {

                    final Address address = mAdapter.getItem(position);

                    DialogUtil.getListTitleDialog(AddressActivity.this, new ListTitleDialog.OnItemClickListener() {

                        @Override
                        public void onItemClick(Dialog dialog, int index) {

                            if (index == 0) {// 设为默认

                                dialog.dismiss();

                                Address defAddress = DBManager.getInstance().getDefaultAddress();
                                if (defAddress != null) {

                                    defAddress.isDefault = false;
                                    defAddress.save();
                                }
                                address.isDefault = true;
                                address.save();
                                mAdapter.notifyDataSetChanged();
                            } else if (index == 1) {// 删除

                                dialog.dismiss();

                                address.delete();
                                mAdapter.remove(address);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }).show();
                }
            });
        lvAddress.setAdapter(mAdapter);

        if (mAddresses == null || mAddresses.isEmpty())
            switchDisable(R.mipmap.ic_address_nothing);
    }

    @Override
    public boolean invalidateContent(int what, Address address) {

        return true;
    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, AddressActivity.class);
        act.startActivity(intent);
    }

    public static void startActivityForResult(Activity act, int requestCode, long addressId) {

        Intent intent = new Intent(act, AddressActivity.class);
        intent.putExtra("isFromOrder", true);
        intent.putExtra("addressId", addressId);
        act.startActivityForResult(intent, requestCode);
    }
}
