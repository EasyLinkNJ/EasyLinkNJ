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
 * Created by KEVIN.DAI on 15/7/18.
 */
public class CartGridAdapter extends ExAdapter<Cart> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    @Override
    public int getCount() {

        return (super.getCount() + 1) / 2;
    }

    private class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView ivPhotoLeft, ivPhotoRight;
        private TextView tvTitleLeft, tvInfoLeft, tvTitleRight, tvInfoRight;
        private View flRight;
        private int realLeftPosition;
        private int realRightPosition;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_cart_grid;
        }

        @Override
        public void initConvertView(View convertView) {

            ivPhotoLeft = (SimpleDraweeView) convertView.findViewById(R.id.ivPhotoLeft);
            ivPhotoRight = (SimpleDraweeView) convertView.findViewById(R.id.ivPhotoRight);
            tvTitleLeft = (TextView) convertView.findViewById(R.id.tvTitleLeft);
            tvInfoLeft = (TextView) convertView.findViewById(R.id.tvInfoLeft);
            tvTitleRight = (TextView) convertView.findViewById(R.id.tvTitleRight);
            tvInfoRight = (TextView) convertView.findViewById(R.id.tvInfoRight);

            convertView.findViewById(R.id.flLeft).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(realLeftPosition, v);
                }
            });
            flRight = convertView.findViewById(R.id.flRight);
            flRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(realRightPosition, v);
                }
            });
        }

        @Override
        public void invalidateConvertView() {

            realLeftPosition = mPosition * 2;
            realRightPosition = realLeftPosition + 1;
            final int size = getData().size();
            if (realLeftPosition >= 0 && realLeftPosition <= size - 1) {

                Cart cart = getData().get(realLeftPosition);
                ivPhotoLeft.setImageURI(Uri.parse(cart.imgUrl));
                tvTitleLeft.setText(cart.name);
                tvInfoLeft.setText("数量：" + cart.count);
            }
            if (realRightPosition >= 0 && realRightPosition <= size - 1) {

                Cart cart = getData().get(realRightPosition);
                ivPhotoRight.setImageURI(Uri.parse(cart.imgUrl));
                tvTitleRight.setText(cart.name);
                tvInfoRight.setText("数量：" + cart.count);
            } else {

                flRight.setVisibility(View.INVISIBLE);
            }
        }
    }
}
