package com.wd.health.home.base;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.health.home.mvp.Contract;
import com.wd.health.home.mvp.m.ModelImpl;
import com.wd.health.home.util.RetrofitHttpUtil;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 荣生
 * @description:
 * @date :2019/7/6 9:14
 */
public abstract class BasePrsenter implements Contract.IPresenter {

    private Contract.IView iView;
    private BaseModel iModel;

    public abstract void getMethod(String url, Class<?> mclass);

    public abstract void getMethod(String url, Map<String, Object> map, Class<?> mclass);

    public abstract void postMethod(String url, Map<String, Object> map, Class<?> mclass);

    public abstract void postMethod(String url, MultipartBody.Part part, Class<?> mclass);

    public abstract void putMethod(String url, Map<String, Object> map, Class<?> mclass);

    @Override
    public void onAttch(Contract.IView iView) {
        this.iView = iView;
        iModel = new ModelImpl();
    }

    @Override
    public void getHttp(String url, Map<String, Object> map, int flag, final Class<?> mclass) {

        RetrofitHttpUtil.RetrofitBack retrofitBack = new RetrofitHttpUtil.RetrofitBack() {
            @Override
            public void success(String success) {
                Gson gson = new Gson();
                Object o = gson.fromJson(success, mclass);
                iView.setData(o);
            }

            @Override
            public void error(String error) {
                Log.e("tag", error);
            }
        };

        switch (flag) {
            case 1:
                iModel.getMethod(url, retrofitBack);
                break;
            case 2:
                iModel.getMethod(url, map, retrofitBack);
                break;
        }


    }

    @Override
    public void postHttp(String url, Map<String, Object> map, MultipartBody.Part part, int flag, final Class<?> mclass) {
        RetrofitHttpUtil.RetrofitBack retrofitBack = new RetrofitHttpUtil.RetrofitBack() {
            @Override
            public void success(String success) {
                Log.i("success",success);
                Gson gson = new Gson();
                Object o = gson.fromJson(success, mclass);
                iView.setData(o);
            }

            @Override
            public void error(String error) {
                Log.e("tag", error);
            }
        };

        switch (flag) {
            case 1:
                iModel.postMethod(url, map, retrofitBack);
                break;
            case 2:
                iModel.postMethod(url, part, retrofitBack);
                break;
        }
    }

    @Override
    public void putHttp(String url, Map<String, Object> map, int flag, final Class<?> mclass) {
        RetrofitHttpUtil.RetrofitBack retrofitBack = new RetrofitHttpUtil.RetrofitBack() {
            @Override
            public void success(String success) {
                Gson gson = new Gson();
                Object o = gson.fromJson(success, mclass);
                iView.setData(o);
            }

            @Override
            public void error(String error) {
                Log.e("tag", error);
            }
        };

        switch (flag) {
            case 1:
                iModel.putMethod(url, map, retrofitBack);
                break;
        }

    }

    @Override
    public void sendMessage(String url, Map<String, Object> map, MultipartBody part, Class<?> mclass) {
        iModel.sendMessage(url, map, part, new RetrofitHttpUtil.RetrofitBack() {
            @Override
            public void success(String success) {
                Log.i("success",success);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    @Override
    public void unAttch() {
        iView = null;
        iModel = null;
    }
}
