package com.easylink.nj.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.bean.db.Address;

/**
 * 产品列表适配器
 * Created by KEVIN.DAI on 15/7/14.
 */
public class AddressListAdapter extends ExAdapter<Address> {

    private long mSelectId = -1;

    public void setSelectId(long id) {

        mSelectId = id;
    }

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private TextView mTvName, mTvPhone, mTvAddress;
        private ImageView mIvIcon;

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
            mIvIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            ViewUtil.hideView(mIvIcon);
        }

        @Override
        public void invalidateConvertView() {

            Address address = getItem(mPosition);
            mTvName.setText("收  货  人：" + address.name);
            mTvPhone.setText("联系方式：" + address.phone);
            if (address.isDefault) {

                String str = "[默认] ";
                SpannableString ss = new SpannableString(str + address.address);
                ss.setSpan(new ForegroundColorSpan(mTvAddress.getResources().getColor(R.color.bg_title_bar)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvAddress.setText(ss);
            } else {

                mTvAddress.setText(address.address);
            }
            if (mSelectId != -1) {

                if (mSelectId == address.getId()) {

                    mIvIcon.setImageResource(R.mipmap.ic_done_view);
                    ViewUtil.showView(mIvIcon);
                }
            } else {

                mIvIcon.setImageResource(R.mipmap.ic_arrow_right_gray);
                ViewUtil.showView(mIvIcon);
            }
        }
    }
}
