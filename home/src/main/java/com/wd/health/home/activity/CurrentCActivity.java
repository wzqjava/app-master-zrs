package com.wd.health.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.wd.health.Constant;
import com.wd.health.home.R;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.httpbean.CurrentCBean;
import com.wd.health.home.httpbean.EndInquiryBean;
import com.wd.health.home.url.MyUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 荣生
 * @description:继续问诊页面
 * @date :2019/8/22 14:30
 */
@Route(path = Constant.ACTIVITY_URL_CURRENTC)
public class CurrentCActivity extends MyBaseActivity implements View.OnClickListener {

    private ImageView ccImagePic;
    private android.widget.TextView doctorName,jobTitle,department,inquiryTime;
    private android.widget.Button continueButton,endButton;
    private String userName,doctorname;
    private int recordId=-1,doctorId,service;
    private ImageView noInfoIv;
    private TextView noInfoTv;
    private android.widget.LinearLayout hasInfo;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_currentc;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        service = intent.getIntExtra("service", 0);
        prsenter.getMethod(MyUrl.findCurrentInquiryRecord, CurrentCBean.class);
    }

    @Override
    protected void initView() {

        ccImagePic = (ImageView) findViewById(R.id.cc_imagePic);
        doctorName = (TextView) findViewById(R.id.doctorName);
        jobTitle = (TextView) findViewById(R.id.jobTitle);
        department = (TextView) findViewById(R.id.department);
        inquiryTime = (TextView) findViewById(R.id.inquiryTime);
        continueButton = (Button) findViewById(R.id.continue_button);
        endButton = (Button) findViewById(R.id.end_button);

        continueButton.setOnClickListener(this);
        endButton.setOnClickListener(this);

        noInfoIv = (ImageView) findViewById(R.id.no_info_iv);
        noInfoTv = (TextView) findViewById(R.id.no_info_tv);
        hasInfo = (LinearLayout) findViewById(R.id.has_info);
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void setData(Object data) {

        if(data instanceof CurrentCBean){
            CurrentCBean.ResultBean result = ((CurrentCBean) data).getResult();

            if(result == null){
                noInfoIv.setVisibility(View.VISIBLE);
                noInfoTv.setVisibility(View.VISIBLE);
                hasInfo.setVisibility(View.GONE);
            }else {

                String imagePic = result.getImagePic();
                Glide.with(this).load(imagePic).into(ccImagePic);
                doctorName.setText(result.getDoctorName());
                jobTitle.setText(result.getJobTitle());
                department.setText(result.getDepartment());
                long Time = result.getInquiryTime();
                String s = TimeUtils.millis2String(Time);
                inquiryTime.setText("问诊时间   "+s);
                userName = result.getUserName();
                recordId = result.getRecordId();
                doctorId = result.getDoctorId();
                doctorname=result.getDoctorName();

                if(service==1){
                    startIntent();
                }

            }


        }else if(data instanceof EndInquiryBean){
            String message = ((EndInquiryBean) data).getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //继续问诊
        if (id == R.id.continue_button) {
            if(null !=userName){
                startIntent();
            }
        }

        //结束问诊
        if (id == R.id.end_button) {
            if(-1 !=recordId){
                Map<String,Object> map=new HashMap<>();
                map.put("recordId",recordId);
                prsenter.putMethod(MyUrl.endInquiry,map, EndInquiryBean.class);
            }
        }

    }

    //跳转到聊天界面
    private void startIntent() {
        Intent intent = new Intent(this, ChatActivity2.class);
        intent.putExtra("name", userName);
        intent.putExtra("inquiryId", recordId);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("doctorName", doctorname);
        startActivity(intent);
    }
}
