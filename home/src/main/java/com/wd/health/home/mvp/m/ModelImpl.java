package com.wd.health.home.mvp.m;

import com.wd.health.home.base.BaseModel;
import com.wd.health.home.util.RetrofitHttpUtil;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 荣生
 * @description:
 * @date :2019/6/20 16:28
 */
public class ModelImpl extends BaseModel {

    @Override
    public void getMethod(String url, RetrofitHttpUtil.RetrofitBack back) {
        getHttp(url,null,1,back);
    }

    @Override
    public void getMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack back) {
        getHttp(url,map,2,back);
    }


    @Override
    public void postMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack back) {
        postHttp(url,map,null,1,back);
    }

    @Override
    public void postMethod(String url, MultipartBody.Part part, RetrofitHttpUtil.RetrofitBack back) {
        postHttp(url,null,part,2,back);
    }

    @Override
    public void putMethod(String url, Map<String, Object> map, RetrofitHttpUtil.RetrofitBack retrofitBack) {
        putHttp(url,map,1,retrofitBack);
    }

}
