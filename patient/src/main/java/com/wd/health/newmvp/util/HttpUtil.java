package com.wd.health.newmvp.util;

import com.wd.health.core.http.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:00
 * 邮箱:1447925431@qq.com
 */
public class HttpUtil {
    private static HttpUtil instance;
    private Retrofit retrofit;
    //单例
    public static HttpUtil getInstance(){
        if (instance==null){
            instance =new HttpUtil();
        }
        return instance;
    }
    private HttpUtil(){
        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

}
