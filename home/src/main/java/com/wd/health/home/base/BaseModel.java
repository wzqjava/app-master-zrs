package com.wd.health.home.base;


import com.wd.health.home.mvp.Contract;
import com.wd.health.home.util.RetrofitHttpUtil;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 荣生
 * @description:
 * @date :2019/7/6 8:53
 */
public abstract class BaseModel implements Contract.IModel {

    private RetrofitHttpUtil util;

    public abstract void getMethod(String url, RetrofitHttpUtil.RetrofitBack back);

    public abstract void getMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack back);

    public abstract void postMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack back);

    public abstract void postMethod(String url, MultipartBody.Part part, RetrofitHttpUtil.RetrofitBack back);

    public abstract void putMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack retrofitBack);

    @Override
    public void getHttp(String url, Map<String, Object> map, int flag, RetrofitHttpUtil.RetrofitBack back) {
        getRxHttp();
        util.getHttpInfo(url, map, flag, back);
    }

    @Override
    public void postHttp(String url, Map<String, Object> map, MultipartBody.Part part, int flag, RetrofitHttpUtil.RetrofitBack back) {
        getRxHttp();
        util.postHttpInfo(url, map, part, flag, back);
    }

    @Override
    public void putHttp(String url, Map<String, Object> map, int flag, RetrofitHttpUtil.RetrofitBack back) {
        getRxHttp();
        util.putHttpInfo(url, map, flag, back);
    }

    @Override
    public void sendMessage(String url, Map<String, Object> map, MultipartBody part, RetrofitHttpUtil.RetrofitBack back) {
        getRxHttp();
        util.sendMessage(url, map, part, back);
    }

    private void getRxHttp() {
        if (util == null) {
            util = RetrofitHttpUtil.getInstance();
        }
    }



}
