package com.wd.health.model.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/7 21:14
 * 邮箱:1447925431@qq.com
 */
public class CircleBean {
   /*  "amount": 10,
             "collectionNum": 0,
             "commentNum": 1,
             "detail": "特别疼",
             "releaseTime": 1565020800000,
             "sickCircleId": 1003,
             "title": "肚子疼"*/
    public int amount;
    public int collectionNum;
    public int commentNum;
    public String detail;
    public long releaseTime;
    public String sickCircleId;
    public String title;

    public CircleBean(int amount, int collectionNum, int commentNum, String detail, long releaseTime, String sickCircleId, String title) {
        this.amount = amount;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
        this.detail = detail;
        this.releaseTime = releaseTime;
        this.sickCircleId = sickCircleId;
        this.title = title;
    }

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

    public String getSickCircleId() {
        return sickCircleId;
    }

    public void setSickCircleId(String sickCircleId) {
        this.sickCircleId = sickCircleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


