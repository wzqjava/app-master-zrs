package com.wd.health.newmvp.model;

import com.wd.health.newmvp.bean.Result;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:18
 * 邮箱:1447925431@qq.com
 */
public interface DataCalls<T> {
    void onSuccess(T t,Object...args);
    void onFail(Result result,Object...args);
}
