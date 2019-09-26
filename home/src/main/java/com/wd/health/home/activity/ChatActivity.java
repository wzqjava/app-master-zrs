package com.wd.health.home.activity;

import android.content.Intent;

import android.os.Handler;

import android.support.v7.widget.LinearLayoutManager;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.wd.health.home.R;
import com.wd.health.home.adapter.ChatingAdapter;
import com.wd.health.home.base.MyBaseActivity;

import com.wd.health.home.httpbean.MessageBean;
import com.wd.health.home.url.MyUrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;


/**
 * 张荣生
 * 历史记录界面
 */
public class ChatActivity extends MyBaseActivity {

    private String sendName,doctorName;
    List<MessageBean.ResultBean> list;
    Handler handler = new Handler();
    private ChatingAdapter adapter;
    private TextView name;
    private XRecyclerView recy;
    private int page,inquiryId,doctorId;
    private LinearLayoutManager manager;
    private boolean isRefresh;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        sendName = intent.getStringExtra("name");
        doctorName = intent.getStringExtra("doctorName");
        inquiryId = intent.getIntExtra("inquiryId", -1);
        doctorId = intent.getIntExtra("doctorId", -1);
        int state = intent.getIntExtra("state", 0);

        name = (TextView) findViewById(R.id.name);
        recy = (XRecyclerView) findViewById(R.id.recy);
        LinearLayout chat_out = (LinearLayout) findViewById(R.id.chat_out);

        if(state == 1){
            chat_out.setVisibility(View.GONE);
        }

        name.setText(sendName);
        manager = new LinearLayoutManager(this);
        recy.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ChatingAdapter(list, this);
        adapter.setHasStableIds(true);
        recy.setItemViewCacheSize(0);
        recy.setAdapter(adapter);
        recy.setLoadingMoreEnabled(false);
        recy.setPullRefreshEnabled(true);

        recy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                RefreshMethod();
                recy.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });

    }

    @Override
    protected void initData() {
        //判断是否刷新
        isRefresh = false;
        page = 1;

        Map<String, Object> map = new HashMap<>();
        map.put("inquiryId", inquiryId);
        map.put("page", page);
        map.put("count", 10);
        //请求历史记录
        prsenter.getMethod(MyUrl.HistoricalNews, map, MessageBean.class);


    }

    @Override
    public void setData(Object data) {

        if (data instanceof MessageBean) {
            if (isRefresh) {
                setRefreshData(data);
            } else {
                List<MessageBean.ResultBean> result = ((MessageBean) data).getResult();
                Collections.reverse(result);
                list.addAll(result);
                if (adapter.getItemCount() > 0) {
                    recy.scrollToPosition(adapter.getItemCount());
                }
            }
        }

    }


    //下拉刷新代码块
    private void RefreshMethod() {
        isRefresh = true;
        page++;
        Map<String, Object> map = new HashMap<>();
        map.put("inquiryId", inquiryId);
        map.put("page", page);
        map.put("count", 10);
        prsenter.getMethod(MyUrl.HistoricalNews, map, MessageBean.class);
    }

    //获取下拉刷新数据
    private void setRefreshData(Object data){
        List<MessageBean.ResultBean> result = ((MessageBean) data).getResult();
        Collections.reverse(result);
        List<MessageBean.ResultBean> newList = new ArrayList<>();
        newList.addAll(result);
        newList.addAll(list);
        list.clear();
        list.addAll(newList);
        adapter = new ChatingAdapter(list, this);
        recy.setAdapter(adapter);
    }

    @Override
    protected void destroyData() {
        handler = null;
        JMessageClient.unRegisterEventReceiver(this);
    }

}
