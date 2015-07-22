package com.easylink.nj.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.easylink.nj.R;

public class ListTitleDialog extends BaseDialog {

    public ListTitleDialog(Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_title);
    }

    @Override
    protected void initContentView() {

        findViewById(R.id.tvSetDefault).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(ListTitleDialog.this, 0);
            }
        });
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
