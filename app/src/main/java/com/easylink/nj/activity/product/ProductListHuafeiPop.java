package com.easylink.nj.activity.product;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easylink.nj.R;
import com.easylink.nj.bean.product.BrandHuafei;
import com.easylink.nj.bean.product.BrandNongji;
import com.easylink.nj.bean.product.CategoryHuafei;
import com.easylink.nj.bean.product.CategoryNongji;

import java.util.List;

/**
 * Created by yihaibin on 15/8/28.
 */
public class ProductListHuafeiPop extends PopupWindow {

    public static final int TYPE_BRAND = 0x001;
    public static final int TYPE_CATEGORY = 0x002;
    public static final int TYPE_DATE = 0x003;

    private int listType;

    private List<BrandHuafei> mBrandList;
    private List<CategoryHuafei> mCategoryList;

    private int typePosition, originPlacesPosition, datePosition;

    private View contentView;
    private ListView contentListView;
    private PopListAdapter contentListAdapter;

    private LayoutInflater inflater;

    public ProductListHuafeiPop(Context context) {
        super(context);
        // data
        inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(R.layout.pop_product_nongji_list_section, null);
        contentListView = (ListView) contentView.findViewById(R.id.lv_selection);
        contentListAdapter = new PopListAdapter();
        // config
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(0X00000000));
    }

    public void setType(int type) {

        listType = type;
    }

    public int getType() {

        return listType;
    }

    public void updateBrand(List<BrandHuafei> brands){

        listType = TYPE_BRAND;
        mBrandList = brands;
    }

    public void updateCategory(List<CategoryHuafei> categoryList){

        listType = TYPE_CATEGORY;
        mCategoryList = categoryList;
    }

//    public void updateData(DealCategoryAll dcr) {
//        updateTypeData(dcr.getType());
//        updateOriginPlaceData(dcr.getDeparture());
//        updateDateData(dcr.getTimes_drange());
//    }

    public void onItemClick(int position) {
        switch (listType) {
            case TYPE_BRAND:
                typePosition = position;
                break;
            case TYPE_CATEGORY:
                originPlacesPosition = position;
                break;
            case TYPE_DATE:
                datePosition = position;
                break;
            default:
                break;
        }
    }

//    public void updateTypeData(ArrayList<BrandNongji> categoryType) {
//        dataCategoryTypes = categoryType;
//    }

//    private void updateOriginPlaceData(ArrayList<DealCategoryOriginPlace> originPlaces) {
//
//        dataOriginPlaces = originPlaces;
//    }
//
//    private void updateDateData(ArrayList<DealCategoryDate> dateList) {
//
//        dataDates = dateList;
//    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {

        contentListView.setOnItemClickListener(listener);
    }

    public String getItemDisplayText(int position) {

        return contentListAdapter.getItemDisplay(position);
    }

    public Object getItemObject(int position){

        return contentListAdapter.getItem(position);
    }

    public String getItemRequestText(int position) {

//        Object obj = contentListAdapter.getItem(position);
//        switch (listType) {
//            case TYPE_CATEGORY:
//                return ((DealCategoryType) obj).getID() + "";
//            case TYPE_ORIGINPLACE:
//                return ((DealCategoryOriginPlace) obj).getCity();
//            case TYPE_DATE:
//                return ((DealCategoryDate) obj).getTimes();
//            default:
//                return null;
//        }

        return null;
    }

    @Override
    public void showAsDropDown(View anchor) {
        contentListAdapter.notifyDataSetChanged();
        contentListView.setAdapter(contentListAdapter);
        setContentView(contentView);
        super.showAsDropDown(anchor);
    }

    /************************ Adapter *****************************/
    class PopListAdapter extends BaseAdapter {

        public PopListAdapter() {
        }

        @Override
        public int getCount() {
            switch (listType) {
                case TYPE_BRAND:
                    return mBrandList == null ? 0 : mBrandList.size();//CollectionUtil.size(dataCategoryTypes);
                case TYPE_CATEGORY:
                    return mCategoryList == null ? 0 : mCategoryList.size();//CollectionUtil.size(dataOriginPlaces);
                case TYPE_DATE:
                    return 0;//CollectionUtil.size(dataDates);
                default:
                    break;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            switch (listType) {
                case TYPE_BRAND:
                    return mBrandList.get(position);
                case TYPE_CATEGORY:
                    return mCategoryList.get(position);
                case TYPE_DATE:
                    return null;//dataDates.get(position);
                default:
                    return null;
            }
        }

        public String getItemDisplay(int position) {

            switch (listType) {
                case TYPE_BRAND:
                    return mBrandList.get(position).getCn_brand();
                case TYPE_CATEGORY:
                    return mCategoryList.get(position).getName();
//                case TYPE_DATE:
//                    return dataDates.get(position).getDescription();
                default:
                    return null;
            }
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.pop_product_nongji_list_section_item, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.textview1);
                holder.imageView = (ImageView) convertView.findViewById(R.id.mIvCheck);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(getItemDisplay(position));

            if (checkoutPosition(position)) {
                holder.textView.setTextColor(0X89000000);
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.textView.setTextColor(0X89000000);
                holder.imageView.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        private boolean checkoutPosition(int position) {
            switch (listType) {
                case TYPE_BRAND:
                    return position == typePosition;
                case TYPE_CATEGORY:
                    return position == originPlacesPosition;
                case TYPE_DATE:
                    return position == datePosition;
                default:
                    return false;
            }
        }
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    public void initSelected() {
        typePosition = 0;
        originPlacesPosition = 0;
        datePosition = 0;
    }
}