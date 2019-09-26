package com.wd.health.core.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.wd.health.comment.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:08
 * Description  :   异常工具类
 */
public class CustomException {
    /**
     * 未知错误
     */
    public static final String UNKNOWN = "1000";

    /**
     * 解析错误
     */
    public static final String PARSE_ERROR = "1001";

    /**
     * 网络错误
     */
    public static final String NETWORK_ERROR = "1002";

    /**
     * 协议错误
     */
    public static final String HTTP_ERROR = "1003";

    /**
     * 处理系统异常，封装成ApiException
     * Throwable包含Error和Exception
     */
    public static ApiException handleException(Throwable e) {

        e.printStackTrace();//打印异常

        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, R.string.utils_exception_parsing_exception + "");
            return ex;
        } else if (e instanceof ConnectException || e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage());
            return ex;
        }
    }
}