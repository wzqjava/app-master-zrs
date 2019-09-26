package com.wd.health.home.httpbean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/22 21:28
 * update: $date$
 */
public class EvaluationBean {

    /**
     * message : 该问诊已结束,不能重复操作
     * status : 8001
     */

    private String message;
    private String status;

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
}
