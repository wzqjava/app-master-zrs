package com.wd.health.home.httpbean;

import java.util.List;

/**
 * description: dell
 * author: 张荣生
 * date: 2019/8/8 19:08
 * update: $date$
 * 二级药品 gson 类
 */
public class YaoErBean {
    /**
     * result : [{"drugsCategoryId":1,"id":1,"name":" [同仁堂]牛黄解毒片(薄膜衣片) ","picture":"https://imgq.ddky.com/c/product/328654/big/z_1.jpg?t=9898&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100"},{"drugsCategoryId":1,"id":2,"name":" [以岭]连花清瘟胶囊 ","picture":"https://imgq.ddky.com/c/product/500186/big/z_1.jpg?t=1534842302335&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100"},{"drugsCategoryId":1,"id":3,"name":" [仁和]抗病毒口服液 ","picture":"https://imgq.ddky.com/c/product/509512/big/z_1.jpg?t=1489473551400&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100"},{"drugsCategoryId":1,"id":4,"name":" [仁和可立克]风热感冒颗粒 ","picture":"https://imgq.ddky.com/c/product/128150/big/z_1.jpg?t=1489743264412&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100"},{"drugsCategoryId":1,"id":5,"name":" [济川]蒲地蓝消炎口服液 ","picture":"https://imgq.ddky.com/c/product/500578/big/z_1.jpg?t=9898&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * drugsCategoryId : 1
         * id : 1
         * name :  [同仁堂]牛黄解毒片(薄膜衣片)
         * picture : https://imgq.ddky.com/c/product/328654/big/z_1.jpg?t=9898&watermark%2F1%2Fimage%2FaHR0cHM6Ly9pbWdxLmRka3kuY29tL2Mvd2F0ZXJQaWMvMTA4MC5wbmc%3D%2Fdissolve%2F80%2Fgravity%2FCenter%2Fdx%2F0%2Fdy%2F0%7CimageMogr2%2Fauto-orient%2Fthumbnail%2F240x240%21%2Fquality%2F100
         */

        private int drugsCategoryId;
        private int id;
        private String name;
        private String picture;

        public int getDrugsCategoryId() {
            return drugsCategoryId;
        }

        public void setDrugsCategoryId(int drugsCategoryId) {
            this.drugsCategoryId = drugsCategoryId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
