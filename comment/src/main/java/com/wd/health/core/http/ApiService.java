package com.wd.health.core.http;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:11
 * Description  :   请求路径类
 */
public class ApiService {
    //BaseUrl
//    public static final String BASE_URL = "http://mobile.bwstudent.com";
    public static final String BASE_URL = "http://172.17.8.100";
    //发送邮箱验证码
    public static final String SEND_EMAIL_URL = "health/user/v1/sendOutEmailCode";
    //登录
    public static final String LOGIN_URL = "health/user/v1/login";
    //注册
    public static final String REGISTER_URL = "health/user/v1/register";
    //检验验证码
    public static final String CHECK_CODE_URL = "health/user/v1/checkCode";
    //修改密码
    public static final String UPDATE_PWD_URL = "health/user/verify/v1/updateUserPwd";

    //查询科室
    public static final String KE_SHI_URL = "health/share/knowledgeBase/v1/findDepartment";

    //根据科室查询对应病症
    public static final String KE_SHI_QUERY_URL = "health/share/knowledgeBase/v1/findDiseaseCategory";
    //查询病友圈列表
    public static final String CIRCLE_URL = "health/user/sickCircle/v1/findSickCircleList?page=2&count=10";
    //查询病友圈详情
    public static final String XIANGQING_URL = "health/user/sickCircle/v1/findSickCircleInfo";
    //根据视频类目查询视频列表
    public static final String VIDEO_URL = "health/user/video/v1/findVideoVoList";
    //查询健康讲堂类目
    public static final String QUERY_URL = "health/user/video/v1/findVideoCategoryList";
    //根据关键词查询病友圈
    public static final String SEARCH_URL = "health/user/sickCircle/v1/searchSickCircle";
    //查询病友圈评论列表
    public static final String COMMENT_URL = "health/user/sickCircle/v1/findSickCircleCommentList?page=2&count=5";

}
