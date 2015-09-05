package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/9/5.
 */
public class ProductPps {

    //[{"kkid":"1","name":"上部","list":[{"id":"1539","name":"约翰迪尔 拖拉机 轴承","model":"0x3轴承","title":"约翰迪尔0x3轴承约翰迪尔 拖拉机 轴承","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/product\/201509\/1539.jpg","kkid":"1","price":"283","stock":"10","url":"http:\/\/nongji.suanduoyi.com\/api\/h5\/product\/1539"}]},{"kkid":"3","name":"下部","list":[{"id":"1539","name":"约翰迪尔 拖拉机 轴承","model":"0x3轴承","title":"约翰迪尔0x3轴承约翰迪尔 拖拉机 轴承","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/product\/201509\/1539.jpg","kkid":"3","price":"283","stock":"10","url":"http:\/\/nongji.suanduoyi.com\/api\/h5\/product\/1539"}]}]
    private String kkid = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;
    private List<Pps> list;

    public String getKkid() {

        return kkid;
    }

    public void setKkid(String kkid) {

        this.kkid = TextUtil.filterNull(kkid);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public List<Pps> getList() {

        return list;
    }

    public void setList(List<Pps> list) {

        this.list = list;
    }
}
