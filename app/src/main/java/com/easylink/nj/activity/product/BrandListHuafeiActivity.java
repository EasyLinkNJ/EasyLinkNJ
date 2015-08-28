package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.bean.product.BrandHuafei;
import com.easylink.nj.bean.product.BrandItem;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandListHuafeiActivity extends BrandListActivity<List<BrandHuafei>> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getBrandHuafeiList(page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return BrandHuafei.class;
    }

//    @Override
//    protected List<?> getListOnInvalidateContent(BrandHuafeiList result) {
//
//        return result == null ? null : result.getList();
//    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        BrandItem item = getBrandItem(position);

        if (item != null) {

            BrandHuafei bh = (BrandHuafei) item;
            ProductListHuafeiActivity.startActivity(this, bh.getId());
        }
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, BrandListHuafeiActivity.class);
        activity.startActivity(intent);
    }
}
