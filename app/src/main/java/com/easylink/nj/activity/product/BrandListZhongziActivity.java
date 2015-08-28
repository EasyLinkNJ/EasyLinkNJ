package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.bean.product.BrandItem;
import com.easylink.nj.bean.product.BrandZhongzi;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandListZhongziActivity extends BrandListActivity<List<BrandZhongzi>> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getBrandZhongziList(page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return BrandZhongzi.class;
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        BrandItem item = getBrandItem(position);

        if (item != null) {

            BrandZhongzi bz = (BrandZhongzi) item;
            if (bz != null)
                ProductListZhongziActivity.startActivity(this, bz.getId());
        }
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, BrandListZhongziActivity.class);
        activity.startActivity(intent);
    }
}
