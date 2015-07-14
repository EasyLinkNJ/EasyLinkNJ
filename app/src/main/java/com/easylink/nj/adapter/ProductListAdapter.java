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
public class ProductListAdapter extends ExAdapter<Product>{

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase{

        private SimpleDraweeView mSdvCover;
        private TextView mTvTitle, mTvDate;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_news_list;
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
            mTvDate = (TextView) convertView.findViewById(R.id.tvDate);
        }

        @Override
        public void invalidateConvertView() {

            Product product = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(product.getMainpic()));
            mTvTitle.setText(product.getTitle());
//            mTvDate.setText(product.getPostdate());
        }

    }
}
