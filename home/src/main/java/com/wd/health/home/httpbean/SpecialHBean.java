package com.wd.health.home.httpbean;

import java.util.List;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/23 20:00
 */
public class SpecialHBean {


    /**
     * result : [{"departmentId":0,"doctorId":76,"doctorName":"程金柱","evaluateStatus":1,"imagePic":"http://172.17.8.100/images/health/doctor/image_pic/2019-08-23/Lewefa20190823193520.jpg","inquiryTime":1566550818000,"jiGuangPwd":"KpGCC+GVfdXcImxA3PaKqH0qhTKpP1GstJ1V1e2cjP+kYJ58YVXrIUNtcUWWZMywX12WR7geWBfnB8t8guHCCOPsGGWJB0DX7C1nv0XHo73qmAkHg/WAlkiKloa9sFSyY5ECoLKEps/rlmnqzKHL96iQ7/69rMxM4E4lluXEyFw=","jobTitle":"主任医师","recordId":2308,"userName":"pTyANW1790076682"},{"departmentId":0,"doctorId":53,"doctorName":"李","evaluateStatus":1,"imagePic":"http://172.17.8.100/images/health/doctor/image_pic/2019-08-13/t5gLFC20190813220232.png","inquiryTime":1566550402000,"jiGuangPwd":"R+0jdN3P4MXHPMFVe9cX5MbX5ulIXHJkfigPLKEeTBY5lUgxJWUNg0js1oGtbsKiLFL4ScqdmUbtHXIfrgQnWrwTNjf09OJLycbeJ+ka4+CV7I1eEqG8DtZPnQoCyxjoYMjO4soDl6EX9YgqaZp3DlUH4pXrYHYz58YyFkSeJEk=","jobTitle":"主治医师","recordId":2306,"userName":"fukSRx1826906365"},{"departmentId":0,"doctorId":76,"doctorName":"程金柱","evaluateStatus":1,"imagePic":"http://172.17.8.100/images/health/doctor/image_pic/2019-08-23/Lewefa20190823193520.jpg","inquiryTime":1566549993000,"jiGuangPwd":"KpGCC+GVfdXcImxA3PaKqH0qhTKpP1GstJ1V1e2cjP+kYJ58YVXrIUNtcUWWZMywX12WR7geWBfnB8t8guHCCOPsGGWJB0DX7C1nv0XHo73qmAkHg/WAlkiKloa9sFSyY5ECoLKEps/rlmnqzKHL96iQ7/69rMxM4E4lluXEyFw=","jobTitle":"主任医师","recordId":2305,"userName":"pTyANW1790076682"},{"departmentId":0,"doctorId":58,"doctorName":"王康康","evaluateStatus":1,"inquiryTime":1566545703000,"jiGuangPwd":"LvsWhMJfCZI5hFbte7xd/xSDF4UakCUhEfECUc/VK+kX1WeGin+OUYJ6k+9qxh7CpADw8f/0PuzADRDJv55ZkJ6KbducMz0P6Cm8wUROXW7YD8Ikm/IO6T2fJG51M477Weh3FyYZh+m8UCHK27CgjWR4G8xrpY2EEZlvZesyfAs=","jobTitle":"主任","recordId":2294,"userName":"mwhv0B2291160065"},{"departmentId":0,"doctorId":76,"doctorName":"程金柱","evaluateStatus":1,"imagePic":"http://172.17.8.100/images/health/doctor/image_pic/2019-08-23/Lewefa20190823193520.jpg","inquiryTime":1566544993000,"jiGuangPwd":"KpGCC+GVfdXcImxA3PaKqH0qhTKpP1GstJ1V1e2cjP+kYJ58YVXrIUNtcUWWZMywX12WR7geWBfnB8t8guHCCOPsGGWJB0DX7C1nv0XHo73qmAkHg/WAlkiKloa9sFSyY5ECoLKEps/rlmnqzKHL96iQ7/69rMxM4E4lluXEyFw=","jobTitle":"主任医师","recordId":2292,"userName":"pTyANW1790076682"}]
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
         * departmentId : 0
         * doctorId : 76
         * doctorName : 程金柱
         * evaluateStatus : 1
         * imagePic : http://172.17.8.100/images/health/doctor/image_pic/2019-08-23/Lewefa20190823193520.jpg
         * inquiryTime : 1566550818000
         * jiGuangPwd : KpGCC+GVfdXcImxA3PaKqH0qhTKpP1GstJ1V1e2cjP+kYJ58YVXrIUNtcUWWZMywX12WR7geWBfnB8t8guHCCOPsGGWJB0DX7C1nv0XHo73qmAkHg/WAlkiKloa9sFSyY5ECoLKEps/rlmnqzKHL96iQ7/69rMxM4E4lluXEyFw=
         * jobTitle : 主任医师
         * recordId : 2308
         * userName : pTyANW1790076682
         */

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
