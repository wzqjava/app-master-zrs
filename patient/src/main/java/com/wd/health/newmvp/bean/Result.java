package com.wd.health.newmvp.bean;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:26
 * 邮箱:1447925431@qq.com
 */
public class Result<T> {
    /* "message": "查询成功",
          "status": "0000"*/
    public String message;
    public String status;
    public T result;

    public Result() {
    }

    public Result(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
