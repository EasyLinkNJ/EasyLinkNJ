package com.easylink.nj.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.easylink.nj.R;
import com.easylink.nj.view.BaseDialog.OnViewClickListener;
import com.easylink.nj.view.ConfirmTitleDialog;
import com.easylink.nj.view.ListTitleDialog;


/**
 * Created by KEVIN.DAI on 15/7/19.
 */
public class DialogUtil {

    public static ConfirmTitleDialog getOrderConfirmDialog(Context context, OnViewClickListener confirmLisn) {

        ConfirmTitleDialog dialog = new ConfirmTitleDialog(context);
        setDialogCancelable(dialog, true);

        dialog.setTitleText("订单已确认");
        dialog.setContentText("客服人员将于1个工作日内和您电话联系");
        dialog.setConfirmText(R.string.confirm_ding);
        dialog.setOnConfirmViewClickListener(confirmLisn);

        return dialog;
    }

    public static ListTitleDialog getListTitleDialog(Context context, boolean isOnlyShowDel, ListTitleDialog.OnItemClickListener lins) {

        ListTitleDialog dialog = new ListTitleDialog(context, isOnlyShowDel);
        setDialogCancelable(dialog, true);

        dialog.setOnItemClickListener(lins);
        return dialog;
    }

    private static void setDialogCancelable(Dialog dialog, boolean cancelable) {

        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {

            dialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {

                    dialog.dismiss();
                }
            });
        }
    }
}
