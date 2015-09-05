package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * Created by yihaibin on 15/9/5.
 */
public class Pps {

   // {"id":"1539","name":"约翰迪尔 拖拉机 轴承","model":"0x3轴承","title":"约翰迪尔0x3轴承约翰迪尔 拖拉机 轴承",
   // "mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/product\/201509\/1539.jpg",
   // "kkid":"1",
   // "price":"283","stock":"10","url":"http:\/\/nongji.suanduoyi.com\/api\/h5\/product\/1539"}
    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;
    private String model = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String mainpic = TextUtil.TEXT_EMPTY;
    private String kkid = TextUtil.TEXT_EMPTY;
    private String price =TextUtil.TEXT_EMPTY;
    private String stock = TextUtil.TEXT_EMPTY;
    private String url = TextUtil.TEXT_EMPTY;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = TextUtil.filterNull(model);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public String getMainpic() {

        return mainpic;
    }

    public void setMainpic(String mainpic) {

        this.mainpic = TextUtil.filterNull(mainpic);
    }

    public String getKkid() {

        return kkid;
    }

    public void setKkid(String kkid) {

        this.kkid = TextUtil.filterNull(kkid);
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price = TextUtil.filterNull(price);
    }

    public String getStock() {

        return stock;
    }

    public void setStock(String stock) {

        this.stock = TextUtil.filterNull(stock);
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = TextUtil.filterNull(url);
    }
}
