package com.easylink.nj.activity.common;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.library.view.listview.XListView;
import com.easylink.nj.R;

import java.util.List;

/**
 * Created by yihaibin on 15/7/19.
 */
public abstract class NjHttpXlvFragment<T> extends NjHttpFragment<T>{

    private final int LIMIT_SIZE = 10;
    private XListView mXlv;
    private ExAdapter mAdapter;
    private int mCurrentPage = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setFragmentContentView(ViewUtil.getCleanXListView(getActivity(), R.id.lv));
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        mAdapter = getAdapter();
    }

    @Override
    protected void initContentView() {

        mXlv = (XListView) findViewById(R.id.lv);
        mXlv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        mXlv.setDividerHeight(2);//2px
        mXlv.setAdapter(mAdapter);
        mXlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh(boolean force) {

                executeHttpTaskByUiSwitch(1, getXlvHttpTaskParam(1, LIMIT_SIZE), getXlvJsonClazz());
            }

            @Override
            public boolean onAutoLoadMore() {

                if(DeviceUtil.isNetworkDisable()){

                    return false;
                }else{

                    executeHttpTaskByUiSwitch(2, getXlvHttpTaskParam(mCurrentPage+1, LIMIT_SIZE), getXlvJsonClazz());
                    return true;
                }
            }

            @Override
            public boolean onManualLoadMore() {

                if(DeviceUtil.isNetworkDisable()){

                    showToast(R.string.toast_network_failed);
                    return false;
                }else{

                    executeHttpTaskByUiSwitch(2, getXlvHttpTaskParam(mCurrentPage+1, LIMIT_SIZE), getXlvJsonClazz());
                    return true;
                }
            }
        });
        mXlv.setPullLoadEnable(false);
        mXlv.setPullRefreshEnable(false);
    }

    public abstract ExAdapter getAdapter();
    public abstract HttpTaskParams getXlvHttpTaskParam(int page, int limit);
    public abstract Class<?> getXlvJsonClazz();

    protected void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, getXlvHttpTaskParam(1, LIMIT_SIZE), getXlvJsonClazz());
    }

    @Override
    public void switchLoading(int what) {

        if(what == 0)
            super.switchLoading(what);
    }

    @Override
    public void switchFailed(int what, int failedCode, String msg) {

        if(what == 0)
            super.switchFailed(what, failedCode, msg);

        if(what == 1)
            mXlv.stopRefresh();

        if(what == 2)
            mXlv.stopLoadMoreFailed();
    }

    @Override
    public void invalidateContent(int what, T data) {

        List<?> list = getListOnInvalidateContent(data);
        if(what == 0){

            mAdapter.setData(list);
            mAdapter.notifyDataSetChanged();

            mXlv.setPullRefreshEnable(data != null && list.size() > 0);
            mXlv.setPullLoadEnable(data != null && list.size() >= LIMIT_SIZE);
            mCurrentPage = 1;

        }else if(what == 1){

            //下拉刷新
            mAdapter.setData(list);
            mAdapter.notifyDataSetChanged();

            mXlv.setPullRefreshEnable(data != null && list.size() > 0);
            mXlv.setPullLoadEnable(data != null && list.size() >= LIMIT_SIZE);
            mXlv.stopRefresh();
            mCurrentPage = 1;

        }else if(what == 2){

            //加载更多
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();

            mXlv.setPullLoadEnable(data != null && list.size() >= LIMIT_SIZE);
            mXlv.stopLoadMore();
            mCurrentPage += 1;
        }
    }

    protected List<?> getListOnInvalidateContent(T result){

        return (List<?>) result;
    }

    @Override
    public void switchContent(int what) {

        if(what == 0)
            super.switchContent(what);
    }

    @Override
    protected void onTipViewClick() {

        loadDataFromServer();
    }
}
