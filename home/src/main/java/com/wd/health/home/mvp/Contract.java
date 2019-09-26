package com.wd.health.home.mvp;

import com.wd.health.home.util.RetrofitHttpUtil;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 荣生
 * @description:
 * @date :2019/6/20 15:35
 */
public interface Contract  {

    public interface IView{
        public void setData(Object data);
    }

    public interface IModel{

        public void getHttp(String url, Map<String, Object> map, int flag, RetrofitHttpUtil.RetrofitBack back);

        public void postHttp(String url, Map<String, Object> map, MultipartBody.Part part, int flag, RetrofitHttpUtil.RetrofitBack back);

        public void putHttp(String url, Map<String, Object> map, int flag, RetrofitHttpUtil.RetrofitBack back);


        public void sendMessage(String url, Map<String, Object> map, MultipartBody part, RetrofitHttpUtil.RetrofitBack back);

    }

    public interface IPresenter{

        public void onAttch(IView iView);

        public void getHttp(String url, Map<String, Object> map, int flag, Class<?> mclass);

        public void postHttp(String url, Map<String, Object> map, MultipartBody.Part part, int flag, Class<?> mclass);

        public void sendMessage(String url, Map<String, Object> map, MultipartBody part,Class<?> mclass);

        public void putHttp(String url, Map<String, Object> map, int flag, Class<?> mclass);

        public void unAttch();

    }

}
