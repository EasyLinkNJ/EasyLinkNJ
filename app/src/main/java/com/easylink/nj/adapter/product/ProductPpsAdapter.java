package com.easylink.nj.adapter.product;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExExpendAdapter;
import com.easylink.library.adapter.ExViewChildHolder;
import com.easylink.library.adapter.ExViewChildHolderBase;
import com.easylink.library.adapter.ExViewGroupHolder;
import com.easylink.library.adapter.ExViewGroupHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.product.Pps;
import com.easylink.nj.bean.product.ProductPps;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by yihaibin on 15/9/5.
 */
public class ProductPpsAdapter extends ExExpendAdapter<ProductPps, Pps> {

    @Override
    public int getChildrenCount(int groupPosition) {

        ProductPps propps = getGroup(groupPosition);
        return propps == null ? 0 : propps.getList().size();
    }

    @Override
    public Pps getChild(int groupPosition, int childPosition) {

        try{
            return getGroup(groupPosition).getList().get(childPosition);
        }catch(Exception e){

            return null;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return 0;
    }

    @Override
    protected ExViewGroupHolder getViewGroupHolder(int groupPos, boolean isExpand) {

        return new ExGroupHolder();
    }

    @Override
    protected ExViewChildHolder getViewChildHolder(int groupPos, int childPos, boolean isLastChild) {

        return new ExChildHolder();
    }

    private class ExGroupHolder extends ExViewGroupHolderBase{

        private TextView tvName;

        @Override
        public int getGroupViewRid() {

            return R.layout.act_product_pps_item_group;
        }

        @Override
        public void initGroupView(View groupView, boolean isExpand) {

            tvName = (TextView) groupView.findViewById(R.id.tvName);
        }

        @Override
        public void invalidateGroupView(boolean isExpand) {

            tvName.setText(getGroup(mGroupPos).getName());
        }
    }

    private class ExChildHolder extends ExViewChildHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvName, mTvDesc;

        @Override
        public int getChildViewRid() {

            return R.layout.act_product_pps_item_child;
        }

        @Override
        public void initChildView(View childView, boolean isLastChild) {

            childView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackItemChildViewClick(mGroupPos, mChildPos, v);
                }
            });

            mSdvCover = (SimpleDraweeView) childView.findViewById(R.id.sdvCover);
            mTvName = (TextView) childView.findViewById(R.id.tvName);
            mTvDesc = (TextView) childView.findViewById(R.id.tvDesc);
        }

        @Override
        public void invalidateChildView(boolean isLastChild) {

            Pps pps = getChild(mGroupPos, mChildPos);
            mSdvCover.setImageURI(Uri.parse(pps.getMainpic()));
            mTvName.setText(pps.getName());
            mTvDesc.setText("价格：" + pps.getPrice());
        }
    }
}
