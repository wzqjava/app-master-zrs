package com.wd.health.model.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/8 15:05
 * 邮箱:1447925431@qq.com
 */
public class VideoBean {

    /**
     * abstracts : 小孩贫血的检查方法，应该是根据孩子贫血的.分类来进行的
     * buyNum : 26
     * categoryId : 1
     * duration : 95
     * id : 1
     * originalUrl : http://172.17.8.100/video/health/original/ek/ek1.mp4
     * price : 100
     * shearUrl : http://172.17.8.100/video/health/cut/ek/ek1.mp4
     * title : 小儿贫血的检查方法有哪些
     * whetherBuy : 2
     * whetherCollection : 2
     */

    private String abstracts;
    private int buyNum;
    private int categoryId;
    private int duration;
    private int id;
    private String originalUrl;
    private int price;
    private String shearUrl;
    private String title;
    private int whetherBuy;
    private int whetherCollection;

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShearUrl() {
        return shearUrl;
    }

    public void setShearUrl(String shearUrl) {
        this.shearUrl = shearUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWhetherBuy() {
        return whetherBuy;
    }

    public void setWhetherBuy(int whetherBuy) {
        this.whetherBuy = whetherBuy;
    }

    public int getWhetherCollection() {
        return whetherCollection;
    }

    public void setWhetherCollection(int whetherCollection) {
        this.whetherCollection = whetherCollection;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "abstracts='" + abstracts + '\'' +
                ", buyNum=" + buyNum +
                ", categoryId=" + categoryId +
                ", duration=" + duration +
                ", id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", price=" + price +
                ", shearUrl='" + shearUrl + '\'' +
                ", title='" + title + '\'' +
                ", whetherBuy=" + whetherBuy +
                ", whetherCollection=" + whetherCollection +
                '}';
    }
}
