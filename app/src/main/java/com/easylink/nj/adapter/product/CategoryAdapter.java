package com.easylink.nj.adapter.product;

import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.CategoryItem;

/**
 * 品牌列表
 * Created by yihaibin on 15/8/25.
 */
public class CategoryAdapter extends ExAdapter<CategoryItem> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase{

        private TextView mTvName;

        @Override
        public int getConvertViewRid() {

//            return R.layout.act_product_brand_item_common;
            return R.layout.act_product_category_item_common;
        }

        @Override
        public void initConvertView(View convertView) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });

            mTvName = (TextView) convertView.findViewById(R.id.tvName);
        }

        @Override
        public void invalidateConvertView() {

            CategoryItem item = getItem(mPosition);
            mTvName.setText(item.getName());
        }
    }
}
