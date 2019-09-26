package com.wd.health.home.httpbean;

/**
 * @author 荣生
 * @description:当前咨询 gson 类
 * @date :2019/8/22 14:07
 */
public class CurrentCBean {


    /**
     * result : {"department":"骨科","departmentId":2,"doctorId":25,"doctorName":"陈焮","evaluateStatus":1,"imagePic":"http://172.17.8.100/images/health/doctor/system_image_pic/system_image3.jpg","inquiryTime":1566443268000,"jiGuangPwd":"mc9OBmTh+caydUtQ6ON1sDqs2uDoD6iveK3+viKA5Z5XEER+NbR6AwafMeDZzTrg3JeozuusO7ia4LuL0yjTQPrSyMDFop5rE8tjPMKHED/bKNVki/qqgQ+4s/0IoFeOiFBp9sK89UzICbYqqWRS4uw1O0XVN5hXY25yCmcGsi4=","jobTitle":"主任医师","recordId":2252,"userName":"nAUOJd1874710336"}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * department : 骨科
         * departmentId : 2
         * doctorId : 25
         * doctorName : 陈焮
         * evaluateStatus : 1
         * imagePic : http://172.17.8.100/images/health/doctor/system_image_pic/system_image3.jpg
         * inquiryTime : 1566443268000
         * jiGuangPwd : mc9OBmTh+caydUtQ6ON1sDqs2uDoD6iveK3+viKA5Z5XEER+NbR6AwafMeDZzTrg3JeozuusO7ia4LuL0yjTQPrSyMDFop5rE8tjPMKHED/bKNVki/qqgQ+4s/0IoFeOiFBp9sK89UzICbYqqWRS4uw1O0XVN5hXY25yCmcGsi4=
         * jobTitle : 主任医师
         * recordId : 2252
         * userName : nAUOJd1874710336
         */

        private String department;
        private int departmentId;
        private int doctorId;
        private String doctorName;
        private int evaluateStatus;
        private String imagePic;
        private long inquiryTime;
        private String jiGuangPwd;
        private String jobTitle;
        private int recordId;
        private String userName;

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public int getEvaluateStatus() {
            return evaluateStatus;
        }

        public void setEvaluateStatus(int evaluateStatus) {
            this.evaluateStatus = evaluateStatus;
        }

        public String getImagePic() {
            return imagePic;
        }

        public void setImagePic(String imagePic) {
            this.imagePic = imagePic;
        }

        public long getInquiryTime() {
            return inquiryTime;
        }

        public void setInquiryTime(long inquiryTime) {
            this.inquiryTime = inquiryTime;
        }

        public String getJiGuangPwd() {
            return jiGuangPwd;
        }

        public void setJiGuangPwd(String jiGuangPwd) {
            this.jiGuangPwd = jiGuangPwd;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
