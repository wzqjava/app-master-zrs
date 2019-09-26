package com.wd.health.home.httpbean;

import java.util.List;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/26 20:58
 */
public class NoticeReadNumBean {


    /**
     * result : [{"notReadNum":0,"noticeType":1},{"notReadNum":0,"noticeType":2},{"notReadNum":4,"noticeType":3}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * notReadNum : 0
         * noticeType : 1
         */

        private int notReadNum;
        private int noticeType;

        public int getNotReadNum() {
            return notReadNum;
        }

        public void setNotReadNum(int notReadNum) {
            this.notReadNum = notReadNum;
        }

        public int getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(int noticeType) {
            this.noticeType = noticeType;
        }
    }
}
