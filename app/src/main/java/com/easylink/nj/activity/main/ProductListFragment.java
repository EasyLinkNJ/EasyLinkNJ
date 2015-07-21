package com.easylink.nj.activity.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;
import com.easylink.nj.activity.common.NjFragment;
import com.easylink.nj.activity.common.NjHttpFragment;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.activity.product.ProductListActivity;
import com.easylink.nj.adapter.ProductCateAdapter;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.Cate;
import com.easylink.nj.bean.product.Product;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.utils.DataUtil;

/**
 * 产品分类列表
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductListFragment extends NjFragment {

    private ProductCateAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
    }

    @Override
    protected void initData() {

        mAdapter = new ProductCateAdapter();
        mAdapter.setData(DataUtil.getProductCate());
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(int position, View clickView) {

                Cate cate = mAdapter.getItem(position);
                if(cate == null)
                    return;

                ProductListActivity.startActivity(getActivity(), cate.getId());
            }
        });
    }

    @Override
    protected void initContentView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        lv.setDividerHeight(2);//2px
        lv.setAdapter(mAdapter);
    }


//    private ProductListAdapter mAdapter;
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//        setFragmentContentView(ViewUtil.getCleanListView(getActivity(), R.id.lv));
//        loadDataFromServer();
//    }
//
//    @Override
//    protected void initData() {
//
//        mAdapter = new ProductListAdapter();
//        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
//
//            @Override
//            public void onItemViewClick(int position, View clickView) {
//
//                Product product = mAdapter.getItem(position);
//                if (product != null)
//                    ProductDetailActivity.startActivity(getActivity(), product.getId(), false);
//            }
//        });
//    }
//
//    @Override
//    protected void initContentView() {
//
//        ListView lv = (ListView) findViewById(R.id.lv);
//        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
//        lv.setDividerHeight(2);//2px
//        lv.setAdapter(mAdapter);
//    }
//
//    @Override
//    protected void onTipViewClick() {
//
//        loadDataFromServer();
//    }
//
//    private void loadDataFromServer() {
//
//        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductList(), ProductList.class);
//    }
//
//    @Override
//    public void invalidateContent(int what, ProductList data) {
//
//        mAdapter.setData(data.getList());
//        mAdapter.notifyDataSetChanged();
//    }
//
    public static ProductListFragment newInstance(FragmentActivity activity) {

        return (ProductListFragment) Fragment.instantiate(activity, ProductListFragment.class.getName(), new Bundle());
    }
}
