package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.Product;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 产品列表适配器
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListAdapter extends ExAdapter<Product> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvTitle, mTvPrice, mTvIntro;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_product_list;
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
            mTvIntro = (TextView) convertView.findViewById(R.id.tvIntro);
        }

        @Override
        public void invalidateConvertView() {

            Product product = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(product.getMainpic()));
            mTvTitle.setText(product.getTitle());
//            mTvPrice.setText(product.getPrice());
//            mTvIntro.setText(product.get);
        }

    }
}
