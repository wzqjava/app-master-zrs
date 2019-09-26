package com.wd.health.home.url;

/**
 * @author 荣生
 * @description:
 * @date :2019/6/20 14:44
 */
public class MyUrl {

    //接口头部
    public static final String BASE_URL="http://172.17.8.100/health/";
//    public static final String BASE_URL="http:/mobile.bwstudent.com/health/";

    //首页banner搜索
    public static final String BANNER_SHOW="share/v1/bannersShow";

    //热门搜索
    public static final String SERACH="share/v1/popularSearch";

    //查询科室列表
    public static final String DEPARTMENT_SHOW="share/knowledgeBase/v1/findDepartment";

    //查询资讯标题
    public static final String PLATELIST="share/information/v1/findInformationPlateList";

    //根据咨询标题查询列表
    public static final String FORMATION="share/information/v1/findInformationList";

    //查询常见一级药品
    public static final String DrugsCategory="share/knowledgeBase/v1/findDrugsCategoryList";

    //查询二级病症
    public static final String DiseaseCategory="share/knowledgeBase/v1/findDiseaseCategory";

    //查询病症详情
    public static final String DiseaseKnowledge="share/knowledgeBase/v1/findDiseaseKnowledge";

    //查询常见一级病症
    public static final String Base_Wenz="share/knowledgeBase/v1/findDepartment";

    //查询二级药品
    public static final String DrugsKnowledgeList="share/knowledgeBase/v1/findDrugsKnowledgeList";

    //查询药品详情
    public static final String DrugXq="share/knowledgeBase/v1/findDrugsKnowledge";

    //查询资讯详情
    public static final String InformationXq="share/information/v1/findInformation";

    //查询医生列表
    public static final String Base_Dorcter="user/inquiry/v1/findDoctorList";

    //查询医生详情
    public static final String DorcterXq="user/inquiry/v1/findDoctorInfo";

    //咨询医生
    public static final String consultDoctor="user/inquiry/verify/v1/consultDoctor";

    //关注医生
    public static final String followDoctor="user/inquiry/verify/v1/followDoctor";

    //取消关注医生
    public static final String cancelFollow="user/inquiry/verify/v1/cancelFollow";

    //结束问诊
    public static final String endInquiry="user/inquiry/verify/v1/endInquiry";

    //用户评论问诊服务
    public static final String evaluationInquiry="user/inquiry/verify/v1/evaluationInquiry";

    //送礼物
    public static final String handselGift="user/inquiry/verify/v1/handselGift";

    //用户查看当前问诊
    public static final String findCurrentInquiryRecord="user/inquiry/verify/v1/findCurrentInquiryRecord";

    //查看历史问诊
    public static final String findHistoryInquiryRecord="user/inquiry/verify/v1/findHistoryInquiryRecord";

    //发送消息
    public static final String pushMessage="user/inquiry/verify/v1/pushMessage";

    //历史记录
    public static final String HistoricalNews="user/inquiry/verify/v1/findInquiryRecordList";

    //用户未读消息
    public static final String NoticeReadNum="user/verify/v1/findUserNoticeReadNum";



}
