package com.wd.health.core.exception;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:07
 * Description  :   封装了自定义的异常
 */
public class ApiException extends Exception {
    private String code;                //提示的代码
    private String displayMessage;      //提示的消息

    public ApiException(String code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(String code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
