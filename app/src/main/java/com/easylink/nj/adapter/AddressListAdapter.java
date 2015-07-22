package com.easylink.nj.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
            convertView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    callbackOnItemViewLongClickListener(mPosition, v);
                    return true;
                }
            });

            mTvName = (TextView) convertView.findViewById(R.id.tvName);
            mTvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            mTvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        }

        @Override
        public void invalidateConvertView() {

            Address address = getItem(mPosition);
            mTvName.setText("收  货  人：" + address.name);
            mTvPhone.setText(address.phone);
            if (address.isDefault) {

                String str = "[默认] ";
                SpannableString ss = new SpannableString(str + address.address);
                ss.setSpan(new ForegroundColorSpan(mTvAddress.getResources().getColor(R.color.bg_title_bar)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvAddress.setText(ss);
            } else {

                mTvAddress.setText(address.address);
            }
        }
    }
}
