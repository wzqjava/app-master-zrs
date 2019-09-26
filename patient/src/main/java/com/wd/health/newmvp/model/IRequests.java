package com.wd.health.newmvp.model;

import com.wd.health.newmvp.bean.DetaBean;
import com.wd.health.newmvp.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:15
 * 邮箱:1447925431@qq.com
 */
public interface IRequests {
    @GET("health/user/sickCircle/v1/findSickCircleInfo")
    Observable<Result<DetaBean>>findSickCircleInfo(@Header("userId")String userId, @Header("sessionId")String sessionId, @Query("sickCircleId")String sickCircleId);

}
