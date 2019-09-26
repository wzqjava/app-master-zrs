package com.wd.health.home.bean;


import cn.jpush.im.android.api.model.Message;

/**
 * @author 荣生
 * @description:自定义 消息类
 * @date :2019/8/5 10:43
 */
public class DialogueBean {

    private Message message;
    private int flag;

    public DialogueBean(Message message, int flag) {
        this.message = message;
        this.flag = flag;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
