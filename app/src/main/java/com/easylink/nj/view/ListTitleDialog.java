package com.easylink.nj.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.easylink.library.util.DensityUtil;
import com.easylink.nj.R;

public class ListTitleDialog extends BaseDialog {

    private boolean isOnlyShowDel = false;

    public ListTitleDialog(Context context, boolean isOnlyShowDel) {

        super(context);
        this.isOnlyShowDel = isOnlyShowDel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_title);
    }

    @Override
    protected void initContentView() {

        if (isOnlyShowDel) {

            findViewById(R.id.tvSetDefault).setVisibility(View.GONE);
            findViewById(R.id.tvDelete).setPadding(0, DensityUtil.dip2px(20), 0, DensityUtil.dip2px(20));
        } else {

            findViewById(R.id.tvSetDefault).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(ListTitleDialog.this, 0);
                }
            });
        }
        findViewById(R.id.tvDelete).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(ListTitleDialog.this, 1);
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener lins) {

        mOnItemClickListener = lins;
    }

    public interface OnItemClickListener {

        void onItemClick(Dialog dialog, int position);
    }
}
