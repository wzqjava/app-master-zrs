package com.wd.health.model.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/20 14:48
 * 邮箱:1447925431@qq.com
 */
public class CommentBean {

    /**
     * commentId : 23
     * commentTime : 1560776000000
     * commentUserId : 10
     * content : 哈哈
     * headPic : http://172.17.8.100/images/health/doctor/image_pic/default/default_image_pic.jpg
     * nickName : ML
     * opinion : 0
     * opposeNum : 0
     * supportNum : 0
     * whetherDoctor : 1
     */

    private int commentId;
    private long commentTime;
    private int commentUserId;
    private String content;
    private String headPic;
    private String nickName;
    private int opinion;
    private int opposeNum;
    private int supportNum;
    private int whetherDoctor;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOpinion() {
        return opinion;
    }

    public void setOpinion(int opinion) {
        this.opinion = opinion;
    }

    public int getOpposeNum() {
        return opposeNum;
    }

    public void setOpposeNum(int opposeNum) {
        this.opposeNum = opposeNum;
    }

    public int getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(int supportNum) {
        this.supportNum = supportNum;
    }

    public int getWhetherDoctor() {
        return whetherDoctor;
    }

    public void setWhetherDoctor(int whetherDoctor) {
        this.whetherDoctor = whetherDoctor;
    }
}
