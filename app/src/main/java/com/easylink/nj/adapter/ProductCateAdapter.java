package com.easylink.nj.adapter;

import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.Cate;

/**
 * Created by yihaibin on 15/7/22.
 */
public class ProductCateAdapter extends ExAdapter<Cate>{

    @Override
    protected ExViewHolder getViewHolder(int position) {

        Cate cate = getItem(position);
        if(cate.getType() == Cate.TYPE_MAIN)
            return new MainViewHolder();
        else
            return new SubViewHolder();
    }

    private final class MainViewHolder extends ExViewHolderBase{

        private TextView mTvName;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_product_cate_main;
        }

        @Override
        public void initConvertView(View convertView) {

            mTvName = (TextView) convertView;
        }

        @Override
        public void invalidateConvertView() {

            Cate cate = getItem(mPosition);
            mTvName.setText(cate.getName());
        }
    }

    private final class SubViewHolder extends ExViewHolderBase{

        private TextView mTvName;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_product_cate_sub;
        }

        @Override
        public void initConvertView(View convertView) {

            mTvName = (TextView) convertView;
            mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });
        }

        @Override
        public void invalidateConvertView() {

            Cate cate = getItem(mPosition);
            mTvName.setText(cate.getName());
        }
    }
}
