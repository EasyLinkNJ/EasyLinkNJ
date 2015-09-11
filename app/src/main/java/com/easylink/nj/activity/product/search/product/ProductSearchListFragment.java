package com.easylink.nj.activity.product.search.product;

import com.easylink.nj.activity.product.ProductListFragment;

/**
 * Created by yihaibin on 15/8/29.
 */
public abstract class ProductSearchListFragment<T> extends ProductListFragment<T> {

    public abstract void loadDataFromServerBySearch(String searchKey);

}
