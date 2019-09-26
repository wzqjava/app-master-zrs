package com.wd.health.core.http;


import com.wd.health.config.ConfigApp;
import com.wd.health.core.db.UserInfoBeanDao;
import com.wd.health.model.bean.UserInfoBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:14
 * Description  :   网络请求
 */
public class NetWorkManager {

    private static volatile NetWorkManager singleton;
    private static final int RESPONSE_CODE = 5;
    private Retrofit mRetrofit;
    private static int status = 1;

    private NetWorkManager() {
        init();
    }

    private void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        UserInfoBeanDao mUserInfoBeanDao = ConfigApp.getInstance().getDaoSession().getUserInfoBeanDao();
                        ArrayList<UserInfoBean> mList = new ArrayList<>(mUserInfoBeanDao.loadAll());
                        for (int i = 0; i < mList.size(); i++) {
                            UserInfoBean bean = mList.get(i);
                            if (null == bean) {
                                return chain.proceed(chain.request());
                            } else {
                                if (bean.getStatus() == status) {
                                    Request request = chain.request();
                                    Request.Builder header = request.newBuilder()
                                            .addHeader("userId", String.valueOf(mList.get(i).getId()))
                                            .addHeader("sessionId", mList.get(i).getSessionId());
                                    Request build = header.build();
                                    Response response = chain.proceed(build);
                                    return response;
                                }
                            }
                        }
                        return chain.proceed(chain.request());
                    }
                })
                .connectTimeout(RESPONSE_CODE, TimeUnit.SECONDS)
                .writeTimeout(RESPONSE_CODE, TimeUnit.SECONDS)
                .readTimeout(RESPONSE_CODE, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static NetWorkManager getInstance() {
        if (singleton == null) {
            synchronized (NetWorkManager.class) {
                if (singleton == null) {
                    singleton = new NetWorkManager();
                }
            }
        }
        return singleton;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
