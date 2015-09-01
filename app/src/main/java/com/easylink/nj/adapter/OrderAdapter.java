package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.library.util.TextUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.bean.db.Cart;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class OrderAdapter extends ExAdapter<Cart> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private final class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvOrderId, mTvTitle, mTvPrice, mTvNum, mTvTotalPrice;
        private View mVDivider;

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
            mTvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
            mVDivider = convertView.findViewById(R.id.vDivider);
            mTvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            mTvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            mTvNum = (TextView) convertView.findViewById(R.id.tvSelectNum);
            convertView.findViewById(R.id.tvNum).setVisibility(View.GONE);
            convertView.findViewById(R.id.tvCount).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivAdd).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivDelete).setVisibility(View.GONE);
            mTvTotalPrice = (TextView) convertView.findViewById(R.id.tvTotalPrice);
            mTvTotalPrice.setVisibility(View.VISIBLE);
        }

        @Override
        public void invalidateConvertView() {

            Cart cart = getItem(mPosition);
            if (mPosition == 0 && TextUtil.isNotEmpty(cart.orderId)) {

                mTvOrderId.setText("订  单  号：" + cart.orderId);
                ViewUtil.showView(mTvOrderId);
                ViewUtil.showView(mVDivider);
            } else {

                ViewUtil.goneView(mTvOrderId);
                ViewUtil.goneView(mVDivider);
            }
            mSdvCover.setImageURI(Uri.parse(cart.imgUrl));
            mTvTitle.setText(cart.name);
            mTvPrice.setText("价格：" + cart.price);
            mTvNum.setText("数量：" + String.valueOf(cart.count));

            try {

                if (cart.price.contains("万")) {

                    int price = Integer.valueOf(cart.price.substring(0, cart.price.indexOf("万"))) * cart.count;
                    mTvTotalPrice.setText("总价：￥" + price + "万");
                } else {

                    int price = Integer.valueOf(cart.price) * cart.count;
                    mTvTotalPrice.setText("总价：￥" + price);
                }
            } catch (Exception e) {

                mTvTotalPrice.setText("总价：面议");
            }
        }
    }
}
