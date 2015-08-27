package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/8/27.
 * <p/>
 * 只是为了生成json串
 */
public class PostOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name = TextUtil.TEXT_EMPTY;
    private String phone = TextUtil.TEXT_EMPTY;
    private String address = TextUtil.TEXT_EMPTY;
    private List<OrderItem> orderjs = new ArrayList<>();

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = TextUtil.filterNull(phone);
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = TextUtil.filterNull(address);
    }

    public List<OrderItem> getOrderjs() {

        return orderjs;
    }

    public void setOrderjs(List<OrderItem> orderjs) {

        this.orderjs = orderjs == null ? new ArrayList<OrderItem>() : orderjs;
    }

    private class OrderItem {

        private String modname = TextUtil.TEXT_EMPTY;
        private String modid = TextUtil.TEXT_EMPTY;
        private String num = TextUtil.TEXT_EMPTY;

        public String getModname() {

            return modname;
        }

        public void setModname(String modname) {

            this.modname = TextUtil.filterNull(modname);
        }

        public String getModid() {

            return modid;
        }

        public void setModid(String modid) {

            this.modid = TextUtil.filterNull(modid);
        }

        public String getNum() {

            return num;
        }

        public void setNum(String num) {

            this.num = TextUtil.filterNull(num);
        }
    }
}
