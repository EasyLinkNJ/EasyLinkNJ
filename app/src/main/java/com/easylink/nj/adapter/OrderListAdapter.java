package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
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
public class OrderListAdapter extends ExAdapter<Cart> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvTitle, mTvPrice, mTvCount;
        private ImageView mIvAdd, mIvDelete;

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
            mTvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            mTvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            mTvCount = (TextView) convertView.findViewById(R.id.tvCount);
            mIvAdd = (ImageView) convertView.findViewById(R.id.ivAdd);
            mIvDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            mIvAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });
            mIvDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });
        }

        @Override
        public void invalidateConvertView() {

            Cart cart = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(cart.imgUrl));
            mTvTitle.setText(cart.name);
            mTvPrice.setText(cart.price);
            mTvCount.setText(String.valueOf(cart.count));
        }
    }
}
