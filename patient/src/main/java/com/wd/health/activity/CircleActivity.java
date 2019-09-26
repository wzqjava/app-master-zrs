package com.wd.health.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.Constant;
import com.wd.health.adapter.KeShiAdapter;
import com.wd.health.adapter.KeShiQueryAdapter;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.model.DataCall;
import com.wd.health.model.bean.KeShiBean;
import com.wd.health.model.bean.KeShiQueryBean;
import com.wd.health.patient.R;
import com.wd.health.presenter.KeShiPresenter;
import com.wd.health.presenter.KeShiQueryPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_CIRCLE)
public class CircleActivity extends BaseActivity {
    private Switch sick_publish_switch;
    private RadioGroup sick_publish_rg;
    private EditText sick_publish_amount;
    private ImageView sick_publish_imgdepartment;
    private EditText sick_publish_department;
    private ImageView sick_publish_imgsick;
    private EditText sick_publish_sick;
    private ImageView sick_publish_img_starttime;
    private EditText sick_publish_starttime;
    private ImageView sick_publish_img_endtime;
    private EditText sick_publish_endtime;
    private PopupWindow window,window1;
    private RecyclerView recy1,recy2;
    private int falg=1;
    private int ids;
    private KeShiQueryPresenter keShiQueryPresenter;
    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initData() {

        initListener();

    }

    private void  initListener() {
        /*----------------------------------------------------------开关按钮---------------------------------------------------*/
        sick_publish_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sick_publish_rg.setVisibility(View.VISIBLE);
                    sick_publish_amount.setVisibility(View.VISIBLE);//显示
                }else {
                    sick_publish_rg.setVisibility(View.GONE);
                    sick_publish_amount.setVisibility(View.GONE);//隐藏
                }
            }
        });

        /*----------------------------------------------------------点击popwind---------------------------------------------------*/
        sick_publish_imgdepartment.setOnClickListener(new View.OnClickListener() {

            private KeShiPresenter keShiPresenter;

            @Override
            public void onClick(View v) {
                keShiPresenter = new KeShiPresenter(new ks());
                keShiPresenter.reqeust();

                falg=1;
                View view = LayoutInflater.from(CircleActivity.this).inflate(R.layout.alert_dialog1, null);
                window = new PopupWindow(view, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                recy1 = view.findViewById(R.id.sick_publish_recyler);
                window.setOutsideTouchable(true);
                window.showAsDropDown(recy1);

            }
        });

        //点击第二个查询病症
        sick_publish_imgsick.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                keShiQueryPresenter = new KeShiQueryPresenter(new ksq());
                //keShiQueryPresenter.reqeust(2);
                keShiQueryPresenter.reqeust(ids);
                falg=2;
                View view = LayoutInflater.from(CircleActivity.this).inflate(R.layout.alert_dialog2, null);
                window1 = new PopupWindow(view, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                recy2=view.findViewById(R.id.sick_publish_recyler_sick);
                window1.setOutsideTouchable(true);
                window1.showAsDropDown(recy2);
            }
        });

        /*-------------------------------------------------------获取时间------------------------------------------------------*/
        sick_publish_img_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(CircleActivity.this, new OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date, View v) {
                        sick_publish_starttime.setText(getTime(date));
                    }
                }).setTitleText("开始时间")
                        .build();
                pvTime.show(true);
            }
        });
        sick_publish_img_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(CircleActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        sick_publish_endtime.setText(getTime(date));
                    }
                }).setTitleText("结束时间")
                        .build();
                pvTime.show(true);
            }
        });
        /*-------------------------------------------------------获取时间------------------------------------------------------*/

    }

    class ksq implements DataCall<List<KeShiQueryBean>>{
        @Override
        public void onSuccess(List<KeShiQueryBean> result, String message) {
            KeShiQueryAdapter keShiQueryAdapter =new KeShiQueryAdapter(CircleActivity.this);
            keShiQueryAdapter.clert();
            keShiQueryAdapter.addAll(result);
            recy2.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
            recy2.setAdapter(keShiQueryAdapter);
            keShiQueryAdapter.setOnCallback(new KeShiQueryAdapter.onCallback() {
                @Override
                public void getData(String i) {
                    sick_publish_sick.setText(i);
                    window1.dismiss();
                }
            });
            keShiQueryAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    class ks implements DataCall<List<KeShiBean>>{
        @Override
        public void onSuccess(List<KeShiBean> result, String message) {
            KeShiAdapter keShiAdapter = new KeShiAdapter(CircleActivity.this);
            keShiAdapter.addAll(result);
            recy1.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
            recy1.setAdapter(keShiAdapter);
            keShiAdapter.setOnCallback(new KeShiAdapter.OnCallback() {
                @Override
                public void getData(int id, String i) {
                    sick_publish_department.setText(i);
                    ids = id;
                    Toast.makeText(CircleActivity.this,""+ids,Toast.LENGTH_SHORT).show();

                    window.dismiss();
                }
            });
            keShiAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }
    @Override
    protected void initView() {
        sick_publish_switch = (Switch) findViewById(R.id.sick_publish_switch);//点击switch开关
        sick_publish_rg = (RadioGroup) findViewById(R.id.sick_publish_rg);//隐藏显示价格
        sick_publish_amount = (EditText) findViewById(R.id.sick_publish_amount);//自定义价格
        sick_publish_imgdepartment = (ImageView) findViewById(R.id.sick_publish_imgdepartment);//获取科室名字
        sick_publish_department = (EditText) findViewById(R.id.sick_publish_department);//赋值给科室名字
        sick_publish_imgsick = (ImageView) findViewById(R.id.sick_publish_imgsick);//获取病症状态
        sick_publish_sick = (EditText) findViewById(R.id.sick_publish_sick);//赋值病症状态
        sick_publish_img_starttime = (ImageView) findViewById(R.id.sick_publish_img_starttime);
        sick_publish_starttime = (EditText) findViewById(R.id.sick_publish_starttime);
        sick_publish_img_endtime = (ImageView) findViewById(R.id.sick_publish_img_endtime);
        sick_publish_endtime = (EditText) findViewById(R.id.sick_publish_endtime);
    }

    @Override
    protected void destroyData() {

    }

    private String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);*/
        //可根据需要自行截取数据显示
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
