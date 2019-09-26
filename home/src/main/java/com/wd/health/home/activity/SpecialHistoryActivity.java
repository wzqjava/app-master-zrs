package com.wd.health.home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.Constant;
import com.wd.health.home.R;
import com.wd.health.home.adapter.SpecialHistoryAdapter;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.httpbean.SpecialHBean;
import com.wd.health.home.url.MyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/23 19:08
 */
@Route(path = Constant.ACTIVITY_URL_SPECIALH)
public class SpecialHistoryActivity extends MyBaseActivity {
    private XRecyclerView historyRecy;
    private List<SpecialHBean.ResultBean> list;
    private SpecialHistoryAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_specialhistory;
    }

    @Override
    protected void initData() {
        Map<String,Object> map=new HashMap<>();
        map.put("page",1);
        map.put("count",5);
        prsenter.getMethod(MyUrl.findHistoryInquiryRecord,map, SpecialHBean.class);
    }

    @Override
    protected void initView() {
        list=new ArrayList<>();
        historyRecy = (XRecyclerView) findViewById(R.id.history_recy);

        historyRecy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpecialHistoryAdapter(this,list);
        adapter.setHistoryBack(new SpecialHistoryAdapter.HistoryBack() {
            @Override
            public void play(int i) {
                Intent intent = new Intent(SpecialHistoryActivity.this, ChatActivity.class);
                intent.putExtra("name", list.get(i).getUserName());
                intent.putExtra("inquiryId", list.get(i).getRecordId());
                intent.putExtra("doctorId", list.get(i).getDoctorId());
                intent.putExtra("state",1);
                startActivity(intent);
            }
        });


        adapter.setEvaluateBack(new SpecialHistoryAdapter.EvaluateBack() {
            @Override
            public void play(int inquiryRecordId, int doctorId) {
                Intent intent = new Intent(SpecialHistoryActivity.this, EvaluateActivity.class);
                intent.putExtra("inquiryRecordId",inquiryRecordId);
                intent.putExtra("doctorId",doctorId);
                startActivity(intent);
            }
        });

        historyRecy.setAdapter(adapter);
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void setData(Object data) {

        if(data instanceof  SpecialHBean){
            List<SpecialHBean.ResultBean> result = ((SpecialHBean) data).getResult();
            list.addAll(result);
            adapter.notifyDataSetChanged();
        }

    }
}
