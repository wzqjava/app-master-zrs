package com.wd.health.home.httpbean;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/22 11:03
 */
public class DoctorIMBean {


    /**
     * doctorUserName : Wk18+CkpPSMvY/NGcKEiLaiSZR1YCKFkX9E9GmfsdddlAodfWs4bN80nzB425IikZJjF3yYzg1J9jkQ2ZBjdT7BhhfBJjQX26skwWMuv0tVhE2kNKMul/2y0r7lJgfDPkZI1sGWmXpGHqB3NmKDMQESMefU2qPUjx0kmSWTaOTI=
     * message : 查询成功
     * status : 0000
     */

    private String doctorUserName;
    private String message;
    private String status;

    public String getDoctorUserName() {
        return doctorUserName;
    }

    public void setDoctorUserName(String doctorUserName) {
        this.doctorUserName = doctorUserName;
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
}
