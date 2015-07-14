package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.ExViewHolder;
import com.easylink.library.adapter.ExViewHolderBase;
import com.easylink.nj.R;
import com.easylink.nj.bean.news.News;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 新闻列表适配器
 * @author yihaibin
 */
public class NewsListAdapter extends ExAdapter<News>{

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

            News news = getItem(mPosition);
            mSdvCover.setImageURI(Uri.parse(news.getMainpic()));
            mTvTitle.setText(news.getTitle());
            mTvDate.setText(news.getPostdate());
        }

    }
}
