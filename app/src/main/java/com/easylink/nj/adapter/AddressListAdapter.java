package com.easylink.nj.adapter;

import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.db.Address;

/**
 * 产品列表适配器
 * Created by KEVIN.DAI on 15/7/14.
 */
public class AddressListAdapter extends ExAdapter<Address> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private TextView mTvName, mTvPhone, mTvAddress;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_address_list;
        }

        @Override
        public void initConvertView(View convertView) {

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });

            mTvName = (TextView) convertView.findViewById(R.id.tvName);
            mTvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            mTvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        }

        @Override
        public void invalidateConvertView() {

            Address address = getItem(mPosition);
//            mTvName.setText(address.name);
            mTvName.append(address.name);
            mTvPhone.setText(address.phone);
            mTvAddress.setText(address.address);
        }
    }
}
