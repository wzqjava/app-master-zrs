package com.wd.health.core.http;

import com.wd.health.model.Result;
import com.wd.health.model.bean.CircleBean;
import com.wd.health.model.bean.CommentBean;
import com.wd.health.model.bean.DetailsBean;
import com.wd.health.model.bean.KeShiBean;
import com.wd.health.model.bean.FindVideoListBean;
import com.wd.health.model.bean.KeShiQueryBean;
import com.wd.health.model.bean.SearchBean;
import com.wd.health.model.bean.UserInfoBean;
import com.wd.health.model.bean.VideoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:11
 * Description  :
 */
public interface IRequest {

    /**
     * 登录
     *
     * @param email 注册邮箱
     * @param pwd   密码
     */
    @FormUrlEncoded
    @POST(ApiService.LOGIN_URL)
    Observable<Result<UserInfoBean>> login(@Field("email") String email,
                                           @Field("pwd") String pwd);

    /**
     * 注册
     *
     * @param email          邮箱
     * @param code           验证码
     * @param pwd1           密码
     * @param pwd2           密码
     * @param invitationCode 邀请码
     */
    @FormUrlEncoded
    @POST(ApiService.REGISTER_URL)
    Observable<Result> register(@Field("email") String email,
                                @Field("code") String code,
                                @Field("pwd1") String pwd1,
                                @Field("pwd2") String pwd2,
                                @Field("invitationCode") String invitationCode
    );

    /**
     * 发送邮箱
     *
     * @param email 邮箱账号
     */
    @FormUrlEncoded
    @POST(ApiService.SEND_EMAIL_URL)
    Observable<Result> sendOutEmailCode(@Field("email") String email);

    /**
     * 检验验证码
     *
     * @param email 邮箱账号
     * @param code  验证码
     */
    @FormUrlEncoded
    @POST(ApiService.CHECK_CODE_URL)
    Observable<Result> checkCode(@Field("email") String email,
                                 @Field("code") String code);


    /*
     * 查询科室列表
     *
     * */
    @GET(ApiService.KE_SHI_URL)
    Observable<Result<List<KeShiBean>>> keshi();

    /*
     *
     * 查询病友圈列表
     *
     * */
    @GET(ApiService.CIRCLE_URL)
    Observable<Result<List<CircleBean>>> circle(@Query("departmentId") int departmentId);

    /*
     * 查询病友圈详情
     *
     * */
    @GET(ApiService.XIANGQING_URL)
    Observable<Result<DetailsBean>> xiangqing(@Query("sickCircleId") String sickCircleId,
                                              @Header("userId") String userId,
                                              @Header("sessionId") String sessionId);

    /**
     * 根据视频类目查询视频列表
     *
     * @param categoryId 健康视频类目id
     * @param page       当前页
     * @param count      当前显示条数
     */
    @GET(ApiService.VIDEO_URL)
    Observable<Result<List<VideoBean>>> findVideoVoList(@Query("categoryId") int categoryId,
                                                        @Query("page") int page,
                                                        @Query("count") int count);

    /**
     * 查询健康讲堂类目
     */
    @GET(ApiService.QUERY_URL)
    Observable<Result<List<FindVideoListBean>>> findVideoCategoryList();

    /*
     * 根据关键词查询病友圈
     *
     * */
    @GET(ApiService.SEARCH_URL)
    Observable<Result<List<SearchBean>>> search(@Query("keyWord") String keyWord);


    /*
     *
     * 查询病友圈评论列表
     * */

    @GET(ApiService.COMMENT_URL)
    Observable<Result<List<CommentBean>>> comment(@Query("sickCircleId") String sickCircleId,
                                                  @Header("userId") String userId,
                                                  @Header("sessionId") String sessionId);


    /*
     *
     * 根据科室查询对应病症
     * */

    @GET(ApiService.KE_SHI_QUERY_URL)
    Observable<Result<List<KeShiQueryBean>>> keshiquery(@Query("departmentId") int departmentId);
}

