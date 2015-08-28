package com.easylink.nj.adapter.product;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.ProductItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 产品列表适配器
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListAdapter extends ExAdapter<ProductItem> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvName, mTvDesc;

        @Override
        public int getConvertViewRid() {

            return R.layout.act_product_product_item_common;
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
            mTvName = (TextView) convertView.findViewById(R.id.tvName);
            mTvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        }

        @Override
        public void invalidateConvertView() {

            ProductItem product = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(product.getLogoUrl()));
            mTvName.setText(product.getSimpleName());
            mTvDesc.setText("价格：" + product.getPrice());
        }
    }
}
