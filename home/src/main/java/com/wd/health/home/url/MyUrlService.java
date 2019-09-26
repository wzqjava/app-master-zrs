package com.wd.health.home.url;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author 荣生
 * @description:
 * @date :2019/6/20 15:09
 */
public interface MyUrlService {

    @POST
    Observable<ResponseBody> postRequest(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Observable<ResponseBody> postRequest(@Url String url,@QueryMap Map<String, Object> map, @Body MultipartBody body);

    @Multipart
    @POST
    Observable<ResponseBody> postRequest(@Url String url, @Part MultipartBody.Part file);

    @GET
    Observable<ResponseBody> getRequest(@Url String url, @QueryMap Map<String, Object> map);

    @GET
    Observable<ResponseBody> getRequest(@Url String url);

    @PUT
    Observable<ResponseBody> putRequest(@Url String url, @QueryMap Map<String, Object> map);

}
