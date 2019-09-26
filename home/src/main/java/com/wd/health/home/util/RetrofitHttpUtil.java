package com.wd.health.home.util;


import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.wd.health.config.ConfigApp;
import com.wd.health.home.url.MyUrl;
import com.wd.health.home.url.MyUrlService;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author 荣生
 * @description:网络工具类
 * @date :2019/6/20 14:36
 */
public class RetrofitHttpUtil {

    private final Retrofit rxhttp;
    private final MyUrlService rx;

    private RetrofitHttpUtil() {

        //实例化拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //在拦截器中添加请求头
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        SharedPreferences info = ConfigApp.context.getSharedPreferences("login_info", MODE_PRIVATE);
                        Long userid = info.getLong("userId", 0);
                        String sessionid = info.getString("sessionId", null);
                        if (TextUtils.isEmpty(sessionid)) {
                            return chain.proceed(chain.request());
                        } else {
                            Log.i("doctor",userid+"");
                            Log.i("doctor",sessionid+"");
                            Request build = chain.request().newBuilder()
                                    .addHeader("userId", userid + "")
                                    .addHeader("sessionId", sessionid)
                                    .build();
                            return chain.proceed(build);
                        }

                    }
                }).build();

        rxhttp = new Retrofit.Builder()
                .baseUrl(MyUrl.BASE_URL)
                .client(client)
                //添加rx工厂
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rx = rxhttp.create(MyUrlService.class);
    }

    private static class Holder {
        static RetrofitHttpUtil util = new RetrofitHttpUtil();
    }

    public static RetrofitHttpUtil getInstance() {
        return Holder.util;
    }


    public void postHttpInfo(String url, Map<String, Object> map, MultipartBody.Part file, int flag, final RetrofitBack callback) {

        Observable<ResponseBody> request = null;

        switch (flag) {
            case 1:
                request = rx.postRequest(url, map);
                break;
            case 2:
                request = rx.postRequest(url, file);
                break;
        }


        request .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            callback.success(responseBody.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void putHttpInfo(String url, Map<String, Object> map, int flag, final RetrofitBack callback) {
        Observable<ResponseBody> request = null;

        switch (flag) {
            case 1:
                request = rx.putRequest(url, map);
                break;
        }

        request .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            callback.success(responseBody.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void getHttpInfo(String url, Map<String, Object> map, int flag, final RetrofitBack callback) {

        Observable<ResponseBody> request = null;

        switch (flag) {
            case 1:
                request = rx.getRequest(url);
                break;
            case 2:
                request = rx.getRequest(url,map);
                break;
        }

        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        try {
                            callback.success(responseBody.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    public void sendMessage(String url, Map<String, Object> map,MultipartBody part,final RetrofitBack callback){
        Observable<ResponseBody> request = rx.postRequest(url,map, part);

        request .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            callback.success(responseBody.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //回调数据
    public interface RetrofitBack {

        void success(String success);

        void error(String error);
    }

}
