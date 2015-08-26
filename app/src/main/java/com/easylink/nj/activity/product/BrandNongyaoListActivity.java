package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.bean.news.NewsList;
import com.easylink.nj.bean.product.BrandNongyao;
import com.easylink.nj.bean.product.ProductNongyao;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class BrandNongyaoListActivity extends BrandListActivity<List<BrandNongyao>> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getBrandNongyaoList(page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return BrandNongyao.class;
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        BrandNongyao pn = (BrandNongyao) getAdapterItem(position);
        if(pn != null)
            ProductNongyaoListActivity.startActivity(this, pn.getId());
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, BrandNongyaoListActivity.class);
        activity.startActivity(intent);
    }
}
