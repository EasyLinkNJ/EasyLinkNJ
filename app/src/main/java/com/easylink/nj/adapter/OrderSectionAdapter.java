package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;
import com.easylink.nj.bean.OrderData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by KEVIN.DAI on 15/8/31.
 */
public class OrderSectionAdapter extends ExAdapter<OrderData> {

    private final int TYPE_TITLE = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_MONEY = 2;
    private boolean isTitleClickable = true;

    public OrderSectionAdapter(boolean isTitleClickable) {

        this.isTitleClickable = isTitleClickable;
    }

    @Override
    protected ExViewHolder getViewHolder(int position) {

        int type = getItemViewType(position);

        if (type == TYPE_TITLE)
            return new TitleHolder();
        else if (type == TYPE_ITEM)
            return new ItemHolder();
        else if (type == TYPE_MONEY)
            return new MoneyHolder();
        else
            return null;
    }

    @Override
    public int getViewTypeCount() {

        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        OrderData data = getItem(position);

        if (TextUtil.isNotEmpty(data.money))
            return TYPE_MONEY;
        else if (TextUtil.isEmpty(data.productId))
            return TYPE_TITLE;
        else
            return TYPE_ITEM;
    }

    private class TitleHolder extends ExViewHolderBase {

        private TextView mTvSection;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_product_cate_sub;
        }

        @Override
        public void initConvertView(View convertView) {

            mTvSection = (TextView) convertView.findViewById(R.id.tvName);
            mTvSection.setEnabled(isTitleClickable);

            if (mTvSection.isEnabled()) {

                mTvSection.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        callbackOnItemViewClickListener(mPosition, v);
                    }
                });
                mTvSection.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {

                        callbackOnItemViewLongClickListener(mPosition, v);
                        return true;
                    }
                });
            } else {

                mTvSection.setCompoundDrawables(null, null, null, null);
            }
        }

        @Override
        public void invalidateConvertView() {

            OrderData data = getItem(mPosition);
            if (data != null && mTvSection != null)
                mTvSection.setText("订单号：" + data.orderId);
        }
    }

    private class MoneyHolder extends ExViewHolderBase {

        private TextView mTvMoney;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_product_cate_sub1;
        }

        @Override
        public void initConvertView(View convertView) {

            mTvMoney = (TextView) convertView.findViewById(R.id.tvName);
        }

        @Override
        public void invalidateConvertView() {

            OrderData data = getItem(mPosition);
            if (data != null && mTvMoney != null)
                mTvMoney.setText("总价：" + data.money);
        }
    }

    private class ItemHolder extends ExViewHolderBase {

        private SimpleDraweeView mSdvCover;
        private TextView mTvTitle, mTvPrice, mTvNum;

        @Override
        public int getConvertViewRid() {

            return R.layout.item_cart_list;
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
            mTvNum = (TextView) convertView.findViewById(R.id.tvSelectNum);
            convertView.findViewById(R.id.tvNum).setVisibility(View.GONE);
            convertView.findViewById(R.id.tvCount).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivAdd).setVisibility(View.GONE);
            convertView.findViewById(R.id.ivDelete).setVisibility(View.GONE);
        }

        @Override
        public void invalidateConvertView() {

            OrderData data = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(data.imgUrl));
            mTvTitle.setText(data.title);
            mTvPrice.setText("价格：" + data.price);
            mTvNum.setText("数量：" + String.valueOf(data.num));
        }
    }
}
