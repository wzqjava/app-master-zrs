package com.wd.health.home.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.config.ConfigApp;
import com.wd.health.home.R;
import com.wd.health.home.adapter.DaocterAdapters;
import com.wd.health.home.adapter.WzTitleAdapter;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.httpbean.CurrentCBean;
import com.wd.health.home.httpbean.DoctorIMBean;
import com.wd.health.home.httpbean.DrocterBean;
import com.wd.health.home.httpbean.DepartmentBean;
import com.wd.health.home.httpbean.NoticeReadNumBean;
import com.wd.health.home.url.MyUrl;
import com.wd.health.home.util.CenterLayoutManager;
import com.wd.health.utils.RsaCoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/12 8:46
 */
public class InquiryActivity extends MyBaseActivity {
    private android.support.v7.widget.RecyclerView onerecycel;
    private android.support.design.widget.TabLayout tablaouts;
    private android.widget.ImageView priceup,drocterimg,moreimg,lefth,righth;
    private android.widget.RelativeLayout aaa;
    private android.widget.TextView derctername,dercterzw,address,dercterhp;
    private android.widget.TextView servercenumber,message_num,monery;
    private android.widget.Button zixun;
    private android.support.v7.widget.RecyclerView towrecycel;

    int id,mposition,flag = 1,mposition1 = 1,mposition2 = 1,doctorId=-1;
    DaocterAdapters daocterAdapters;
    ArrayList<DrocterBean.ResultBean> drocterlist = new ArrayList<>();
    ArrayList<DepartmentBean.ResultBean> Wzlist = new ArrayList<>();
    WzTitleAdapter wzTitleAdapter;
    CenterLayoutManager centerLayoutManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_inquiry;
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("", "");
        prsenter.getMethod(MyUrl.Base_Wenz, map, DepartmentBean.class);


        //适配器1-------------------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        onerecycel.setLayoutManager(linearLayoutManager);
        wzTitleAdapter = new WzTitleAdapter(Wzlist, this);
        wzTitleAdapter.setColor(id);
        onerecycel.setAdapter(wzTitleAdapter);
        wzTitleAdapter.setOnItemclick(new WzTitleAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                mposition = position;
                mposition1 = 1;
                mposition2 = 1;
                getdorcters(position);
                wzTitleAdapter.notifyDataSetChanged();
            }
        });

        //适配器二---------------------------------------------------

        centerLayoutManager = new CenterLayoutManager(this);
        centerLayoutManager.setOrientation(CenterLayoutManager.HORIZONTAL);
        towrecycel.setLayoutManager(centerLayoutManager);
        daocterAdapters = new DaocterAdapters(drocterlist, this);
        towrecycel.setAdapter(daocterAdapters);
        //点击医生
        daocterAdapters.setOnItemclick(new DaocterAdapters.OnItemclick() {
            @Override
            public void getposition(int position) {
                doctorId=-1;
                if (drocterlist.size() != 0) {
                    mposition1 = position;
                    mposition2 = position;
                    doctorId = drocterlist.get(position).getDoctorId();

                    centerLayoutManager.smoothScrollToPosition(towrecycel, null, position);
                    derctername.setText(drocterlist.get(position).getDoctorName());
                    Glide.with(InquiryActivity.this).load(drocterlist.get(position).getImagePic()).into(drocterimg);
                    dercterzw.setText(drocterlist.get(position).getInauguralHospital());
                    dercterhp.setText("好评率 " + drocterlist.get(position).getPraise());
                    servercenumber.setText("服务患者数 " + drocterlist.get(position).getServerNum() + "");
                    monery.setText(drocterlist.get(position).getServicePrice() + "H币/次");
                    daocterAdapters.notifyDataSetChanged();
                    //3087ea
                }

            }
        });


        //tablaout 数据设置
        tablaouts.addTab(tablaouts.newTab().setText("综合"));
        tablaouts.addTab(tablaouts.newTab().setText("好评"));
        tablaouts.addTab(tablaouts.newTab().setText("咨询数"));
        tablaouts.addTab(tablaouts.newTab().setText("价格"));
        initlisenter();
    }

    @Override
    protected void initView() {

        onerecycel = (RecyclerView) findViewById(R.id.onerecycel);
        tablaouts = (TabLayout) findViewById(R.id.tablaouts);
        priceup = (ImageView) findViewById(R.id.priceup);
        aaa = (RelativeLayout) findViewById(R.id.aaa);
        drocterimg = (ImageView) findViewById(R.id.drocterimg);
        derctername = (TextView) findViewById(R.id.derctername);
        dercterzw = (TextView) findViewById(R.id.dercterzw);
        address = (TextView) findViewById(R.id.address);
        dercterhp = (TextView) findViewById(R.id.dercterhp);
        servercenumber = (TextView) findViewById(R.id.servercenumber);
        moreimg = (ImageView) findViewById(R.id.moreimg);
        monery = (TextView) findViewById(R.id.monery);
        zixun = (Button) findViewById(R.id.zixun);
        lefth = (ImageView) findViewById(R.id.lefth);
        message_num = findViewById(R.id.message_num);
        towrecycel = (RecyclerView) findViewById(R.id.towrecycel);
        righth = (ImageView) findViewById(R.id.righth);
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void setData(Object data) {
        if (data instanceof DepartmentBean) {
            DepartmentBean DepartmentBean = (DepartmentBean) data;
            List<DepartmentBean.ResultBean> result = DepartmentBean.getResult();
            Wzlist.clear();
            Wzlist.addAll(result);
            getdorcters(id);
            wzTitleAdapter.notifyDataSetChanged();


        }else if (data instanceof DrocterBean) {
            drocterlist.clear();
            daocterAdapters.notifyDataSetChanged();
            DrocterBean drocterBean = (DrocterBean) data;
            List<DrocterBean.ResultBean> result = drocterBean.getResult();
            if(result.size()>=0){
                doctorId = result.get(0).getDoctorId();
            }
            //Toast.makeText(this, result.get(0).getDoctorName(), Toast.LENGTH_SHORT).show();
            drocterlist.addAll(result);
            if (drocterlist.size() == 1) {
                daocterAdapters.setColor(0);
                setdata(0);
            } else if (drocterlist.size() > 1) {
                daocterAdapters.setColor(1);
                setdata(1);
            } else {
                Toast.makeText(this, "没有相关科室医生入住哦", Toast.LENGTH_SHORT).show();
            }
            daocterAdapters.notifyDataSetChanged();
        }else if (data instanceof DoctorIMBean){

            String message = ((DoctorIMBean) data).getMessage();
            if(message.equals("H币不足")){
                Toast.makeText(this, "H币不足", Toast.LENGTH_SHORT).show();
            }else if(message.equals("有正在沟通中的咨询")){
                Toast.makeText(this, "有正在沟通中的咨询", Toast.LENGTH_SHORT).show();
            } else{
                prsenter.getMethod(MyUrl.findCurrentInquiryRecord, CurrentCBean.class);
            }

        }else if(data instanceof CurrentCBean){
            CurrentCBean.ResultBean result = ((CurrentCBean) data).getResult();
            String imagePic = result.getImagePic();
            long Time = result.getInquiryTime();
            String s = TimeUtils.millis2String(Time);
            String userName = result.getUserName();
            String doctorName = result.getDoctorName();
            int recordId = result.getRecordId();
            doctorId = result.getDoctorId();

            Intent intent = new Intent(this, ChatActivity2.class);
            intent.putExtra("name", userName);
            intent.putExtra("inquiryId", recordId);
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("doctorName", doctorName);
            JMessageClient.deleteSingleConversation(userName, ConfigApp.APP_KEY);
            startActivity(intent);
        }else if(data instanceof NoticeReadNumBean){
            List<NoticeReadNumBean.ResultBean> result = ((NoticeReadNumBean) data).getResult();
            for (NoticeReadNumBean.ResultBean ss:result) {
                int notReadNum = ss.getNotReadNum();
                NoRead_num+=notReadNum;
            }

            if(NoRead_num !=0){
                message_num.setVisibility(View.VISIBLE);
                message_num.setText(NoRead_num+"");
            }

        }
    }

    public void getdorcters(int id) {
        HashMap<String, Object> map1 = new HashMap<>();
        int id1 = Wzlist.get(id).getId();
        map1.put("deptId", id1);
        map1.put("condition", flag);
        if (flag == 4) {
            map1.put("sortBy", flag);
        }
        map1.put("page", 1);
        map1.put("count", 8);
        prsenter.getMethod(MyUrl.Base_Dorcter, map1, DrocterBean.class);

    }

    public void setdata(int index) {
        doctorId=drocterlist.get(index).getDoctorId();
        derctername.setText(drocterlist.get(index).getDoctorName());
        Glide.with(InquiryActivity.this).load(drocterlist.get(index).getImagePic()).into(drocterimg);
        dercterzw.setText(drocterlist.get(index).getInauguralHospital());
        dercterhp.setText("好评率 " + drocterlist.get(index).getPraise());
        servercenumber.setText("服务患者数 " + drocterlist.get(index).getServerNum() + "");
        monery.setText(drocterlist.get(index).getServicePrice() + "H币/次");

    }

    public void initlisenter() {
        //点击咨询按钮
        zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(-1 != doctorId){
                    Log.i("doctorId",doctorId+"");
                    Map<String,Object> map=new HashMap<>();
                    map.put("doctorId",doctorId);
                    prsenter.putMethod(MyUrl.consultDoctor, map, DoctorIMBean.class);
                }
            }
        });

        //点击跳转详情页面
        moreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //右侧按钮
        righth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置数据

                if (mposition2 < drocterlist.size() - 2) {
                    mposition2++;
                    //调用适配器的改变当前选中的条目颜色
                    daocterAdapters.setColor(mposition2);
                }
                if (drocterlist.size() == 0) {
                    Toast.makeText(InquiryActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    doctorId=-1;
                } else if (drocterlist.size() == 1) {
                    setdata(0);
                } else {
                    setdata(mposition2);
                }

                //设置滑动
                if (drocterlist.size() > 3) {
                    if (mposition1 > drocterlist.size() - 3) {
                    } else {
                        mposition1++;
                    }

                    centerLayoutManager.smoothScrollToPosition(towrecycel, null, mposition1);
                }

            }
        });
        //点击按钮向左滑动
        lefth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置数据
                if (mposition2 >= 2) {
                    mposition2--;
                    //调用适配器的改变当前选中的条目颜色
                    daocterAdapters.setColor(mposition2);
                }

                if (drocterlist.size() == 0) {
                    Toast.makeText(InquiryActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    doctorId=-1;
                } else if (drocterlist.size() == 1) {
                    setdata(0);
                } else {
                    setdata(mposition2);
                }

                //设置滑动

                if (mposition1 > 1) {
                    mposition1--;
                    centerLayoutManager.smoothScrollToPosition(towrecycel, null, mposition1);
                }


            }
        });


        //tab点击监听切换数据
        tablaouts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("综合")) {
                    flag = 1;
                    getdorcters(mposition);
                } else if (tab.getText().equals("好评")) {
                    flag = 2;
                    getdorcters(mposition);
                } else if (tab.getText().equals("咨询数")) {
                    flag = 3;
                    getdorcters(mposition);
                } else {
                    flag = 4;
                    getdorcters(mposition);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
