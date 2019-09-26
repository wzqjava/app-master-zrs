package com.wd.health.model;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:13
 * Description  :
 */
public class Result<T> {
    public String status = " -1";
    public String message = "请求失败";
    public T result;

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

}