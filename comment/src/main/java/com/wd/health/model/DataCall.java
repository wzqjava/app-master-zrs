package com.wd.health.model;

import com.wd.health.core.exception.ApiException;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:06
 * Description  :   封装请求成功和失败的接口
 */
public interface DataCall<T> {

    void onSuccess(T result, String message);

    void onFaild(ApiException e);
}