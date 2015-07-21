package com.easylink.nj.utils;

import com.easylink.nj.bean.product.Cate;
import com.easylink.nj.bean.product.MainCate;
import com.easylink.nj.bean.product.SubCate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/7/22.
 */
public class DataUtil {

    public static List<MainCate> getMainCateList(){

        List<MainCate> mainCates = new ArrayList<MainCate>();
        List<SubCate> subCates = new ArrayList<SubCate>();

        SubCate sc = new SubCate();
        sc.setId("1");
        sc.setName("拖拉机");
        subCates.add(sc);

        sc = new SubCate();
        sc.setId("2");
        sc.setName("收割机");
        subCates.add(sc);
        subCates.add(sc);

        MainCate mc = new MainCate();
        mc.setId("1");
        mc.setName("整机");
        mc.setSubCateList(subCates);

        mainCates.add(mc);
//-----------------------------------------
        subCates = new ArrayList<SubCate>();

        sc = new SubCate();
        sc.setId("1");
        sc.setName("拖拉机配件");
        subCates.add(sc);

        sc = new SubCate();
        sc.setId("2");
        sc.setName("收割机配件");
        subCates.add(sc);
        subCates.add(sc);

        mc = new MainCate();
        mc.setId("2");
        mc.setName("配件");
        mc.setSubCateList(subCates);

        mainCates.add(mc);
        return mainCates;
    }

    public static List<Cate> getProductCate(){

        List<Cate> cateList = new ArrayList<Cate>();
        Cate cate = new Cate();
        cate.setType(Cate.TYPE_MAIN);
        cate.setId("1");
        cate.setName("整机");
        cateList.add(cate);

        cate = new Cate();
        cate.setType(Cate.TYPE_SUB);
        cate.setId("13");
        cate.setName("拖拉机");
        cateList.add(cate);

        cate = new Cate();
        cate.setType(Cate.TYPE_SUB);
        cate.setId("14");
        cate.setName("收割机");
        cateList.add(cate);

        cate = new Cate();
        cate.setType(Cate.TYPE_MAIN);
        cate.setId("2");
        cate.setName("配件");
        cateList.add(cate);

        cate = new Cate();
        cate.setType(Cate.TYPE_SUB);
        cate.setId("13");
        cate.setName("拖拉机配件");
        cateList.add(cate);

        cate = new Cate();
        cate.setType(Cate.TYPE_SUB);
        cate.setId("14");
        cate.setName("收割机配件");
        cateList.add(cate);

        return cateList;
    }
}
