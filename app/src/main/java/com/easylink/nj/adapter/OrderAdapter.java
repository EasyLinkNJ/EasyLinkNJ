package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.db.Cart;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class OrderAdapter extends ExAdapter<Cart> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvOrderId, mTvTitle, mTvPrice, mTvNum, mTvTotalPrice;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_order_list;
        }

        @Override
        public void initConvertView(View convertView) {

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });

            mSdvCover = (SimpleDraweeView) convertView.findViewById(R.id.sdvCover);
            mTvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
            mTvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            mTvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            mTvNum = (TextView) convertView.findViewById(R.id.tvSelectNum);
            convertView.findViewById(R.id.tvNum).setVisibility(View.GONE);
            convertView.findViewById(R.id.tvCount).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivAdd).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivDelete).setVisibility(View.GONE);
            mTvTotalPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
            mTvTotalPrice.setVisibility(View.VISIBLE);
        }

        @Override
        public void invalidateConvertView() {

            Cart cart = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(cart.imgUrl));
            mTvTitle.setText(cart.name);
            mTvPrice.setText(cart.price);
            mTvNum.setText("数量：x " + String.valueOf(cart.count));

            try {

                int price = Integer.valueOf(cart.price.substring(0, cart.price.indexOf("万"))) * cart.count;
                mTvTotalPrice.setText("总价：￥" + price + "万");
            } catch (Exception e) {

                mTvTotalPrice.setText("总价：面议");
            }
        }
    }
}
