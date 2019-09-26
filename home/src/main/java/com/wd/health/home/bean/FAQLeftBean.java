package com.wd.health.home.bean;

/**
 * @author 荣生
 * @description: 常见问题页面 一级列表
 * @date :2019/8/8 14:29
 */
public class FAQLeftBean {

    private String name;
    private int id;

    public FAQLeftBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
