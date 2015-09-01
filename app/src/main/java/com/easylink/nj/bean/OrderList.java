package com.easylink.nj.bean;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/8/31.
 */
public class OrderList {

    /**
     * sum : 1
     * list : [{"id":"20001212","account_id":"2","created":"1440845343","changed":"1440845343","status":"1","name":"kevin","phone":"12345678","address":"beijingshichangpingqu","ishow":"1","status_name":"已联系-确认购买","list":[{"id":"39","norder_id":"20001212","modname":"product","modid":"1401","num":"1","price":"10万","name":"小麦收割机","model":"谷王TB60","ishow":"1","cmoddata":{"id":"1401","price":"10万","name":"小麦收割机","model":"谷王TB60","cate_id":"14","company_id":"3"},"cate_name":"收割机","brand_id":"3","brand_name":"中联重科","title":"中联重科谷王TB60小麦收割机"}]}]
     * ps : 10
     * p : 1
     */

    private String sum;
    private int ps;
    private int p;
    private List<Orders> list;

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setList(List<Orders> list) {
        this.list = list;
    }

    public String getSum() {
        return sum;
    }

    public int getPs() {
        return ps;
    }

    public int getP() {
        return p;
    }

    public List<Orders> getList() {
        return list;
    }

    public static class Orders {

        /**
         * id : 20001212
         * account_id : 2
         * created : 1440845343
         * changed : 1440845343
         * status : 1
         * name : kevin
         * phone : 12345678
         * address : beijingshichangpingqu
         * ishow : 1
         * status_name : 已联系-确认购买
         * list : [{"id":"39","norder_id":"20001212","modname":"product","modid":"1401","num":"1","price":"10万","name":"小麦收割机","model":"谷王TB60","ishow":"1","cmoddata":{"id":"1401","price":"10万","name":"小麦收割机","model":"谷王TB60","cate_id":"14","company_id":"3"},"cate_name":"收割机","brand_id":"3","brand_name":"中联重科","title":"中联重科谷王TB60小麦收割机"}]
         */

        private String id;
        private String account_id;
        private String created;
        private String changed;
        private String status;
        private String name;
        private String phone;
        private String address;
        private String ishow;
        private String status_name;
        private String money;
        private List<Item> list;

        public void setId(String id) {
            this.id = id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setChanged(String changed) {
            this.changed = changed;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setIshow(String ishow) {
            this.ishow = ishow;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public void setList(List<Item> list) {
            this.list = list;
        }

        public String getId() {
            return id;
        }

        public String getAccount_id() {
            return account_id;
        }

        public String getCreated() {
            return created;
        }

        public String getChanged() {
            return changed;
        }

        public String getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getIshow() {
            return ishow;
        }

        public String getStatus_name() {
            return status_name;
        }

        public String getMoney() {
            return money;
        }

        public List<Item> getList() {
            return list;
        }

        public static class Item {

            /**
             * id : 39
             * norder_id : 20001212
             * modname : product
             * modid : 1401
             * num : 1
             * price : 10万
             * name : 小麦收割机
             * model : 谷王TB60
             * ishow : 1
             * cmoddata : {"id":"1401","price":"10万","name":"小麦收割机","model":"谷王TB60","cate_id":"14","company_id":"3"}
             * cate_name : 收割机
             * brand_id : 3
             * brand_name : 中联重科
             * title : 中联重科谷王TB60小麦收割机
             */

            private String id;
            private String norder_id;
            private String modname;
            private String mainpic;
            private String modid;
            private String num;
            private String price;
            private String name;
            private String model;
            private String ishow;
            private CmoddataEntity cmoddata;
            private String cate_name;
            private String brand_id;
            private String brand_name;
            private String title;

            public void setId(String id) {
                this.id = id;
            }

            public void setNorder_id(String norder_id) {
                this.norder_id = norder_id;
            }

            public void setModname(String modname) {
                this.modname = modname;
            }

            public void setMainpic(String mainpic) {
                this.mainpic = mainpic;
            }

            public void setModid(String modid) {
                this.modid = modid;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setIshow(String ishow) {
                this.ishow = ishow;
            }

            public void setCmoddata(CmoddataEntity cmoddata) {
                this.cmoddata = cmoddata;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public String getNorder_id() {
                return norder_id;
            }

            public String getModname() {
                return modname;
            }

            public String getMainpic() {
                return mainpic;
            }

            public String getModid() {
                return modid;
            }

            public String getNum() {
                return num;
            }

            public String getPrice() {
                return price;
            }

            public String getName() {
                return name;
            }

            public String getModel() {
                return model;
            }

            public String getIshow() {
                return ishow;
            }

            public CmoddataEntity getCmoddata() {
                return cmoddata;
            }

            public String getCate_name() {
                return cate_name;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public String getTitle() {
                return title;
            }

            public static class CmoddataEntity {

                /**
                 * id : 1401
                 * price : 10万
                 * name : 小麦收割机
                 * model : 谷王TB60
                 * cate_id : 14
                 * company_id : 3
                 */

                private String id;
                private String price;
                private String name;
                private String model;
                private String cate_id;
                private String company_id;

                public void setId(String id) {
                    this.id = id;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                public void setCate_id(String cate_id) {
                    this.cate_id = cate_id;
                }

                public void setCompany_id(String company_id) {
                    this.company_id = company_id;
                }

                public String getId() {
                    return id;
                }

                public String getPrice() {
                    return price;
                }

                public String getName() {
                    return name;
                }

                public String getModel() {
                    return model;
                }

                public String getCate_id() {
                    return cate_id;
                }

                public String getCompany_id() {
                    return company_id;
                }
            }
        }
    }
}
