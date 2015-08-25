package com.easylink.nj.adapter.product;

import android.view.View;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.bean.product.Brand;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandAdapter extends ExAdapter<Brand> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase{


        @Override
        public int getConvertViewRid() {

            return 0;
        }

        @Override
        public void initConvertView(View convertView) {

        }

        @Override
        public void invalidateConvertView() {

        }
    }
}
