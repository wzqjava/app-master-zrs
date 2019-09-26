package com.wd.health.model.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/15 09:17
 * 邮箱:1447925431@qq.com
 */
public class SearchBean {

    /**
     * amount : 0
     * collectionNum : 0
     * commentNum : 0
     * detail : 敲代码
     * releaseTime : 1564070400000
     * sickCircleId : 583
     * title : 头疼
     */

    private int amount;
    private int collectionNum;
    private int commentNum;
    private String detail;
    private long releaseTime;
    private int sickCircleId;
    private String title;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getSickCircleId() {
        return sickCircleId;
    }

    public void setSickCircleId(int sickCircleId) {
        this.sickCircleId = sickCircleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
