package com.wd.health.home.mvp.p;

import com.wd.health.home.base.BasePrsenter;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 荣生
 * @description:
 * @date :2019/6/20 16:27
 */
public class PrsenterImpl extends BasePrsenter {

    @Override
    public void getMethod(String url, Class<?> mclass) {
        getHttp(url,null,1,mclass);
    }

    @Override
    public void getMethod(String url, Map<String, Object> map, Class<?> mclass) {
        getHttp(url,map,2,mclass);
    }

    @Override
    public void postMethod(String url, Map<String, Object> map, Class<?> mclass) {
        postHttp(url,map,null,1,mclass);
    }

    @Override
    public void postMethod(String url, MultipartBody.Part part, Class<?> mclass) {
        postHttp(url,null,part,2,mclass);
    }

    @Override
    public void putMethod(String url, Map<String, Object> map, Class<?> mclass) {
        putHttp(url,map,1,mclass);
    }

}
