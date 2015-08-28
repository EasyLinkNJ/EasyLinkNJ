package com.easylink.nj.adapter.product;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.BrandItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 品牌列表
 * Created by yihaibin on 15/8/25.
 */
public class BrandAdapter extends ExAdapter<BrandItem> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase{

        private SimpleDraweeView mSdvCover;
        private TextView mTvName, mTvDesc;

        @Override
        public int getConvertViewRid() {

//            return R.layout.act_product_brand_item_common;
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

            BrandItem brandItem = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(brandItem.getLogoUrl()));
            mTvName.setText(brandItem.getSimpleName());
            mTvDesc.setText(brandItem.getDesc());
        }
    }
}
