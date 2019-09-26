package com.wd.health.model.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/7 20:16
 * 邮箱:1447925431@qq.com
 */
public class KeShiBean {
    /* "departmentName": "内科",
             "id": 7,
             "pic": "http://172.17.8.100/images/health/department_pic/nk.jpg",
             "rank": 1*/
    public String departmentName;
    public int id;
    public String pic;
    public int rank;

    public KeShiBean(String departmentName, int id, String pic, int rank) {
        this.departmentName = departmentName;
        this.id = id;
        this.pic = pic;
        this.rank = rank;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
