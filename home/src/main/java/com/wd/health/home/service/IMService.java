package com.wd.health.home.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.wd.health.home.activity.ChatActivity;
import com.wd.health.home.activity.ChatActivity2;
import com.wd.health.home.activity.CurrentCActivity;
import com.wd.health.home.base.BasePrsenter;
import com.wd.health.home.httpbean.CurrentCBean;
import com.wd.health.home.httpbean.EndInquiryBean;
import com.wd.health.home.mvp.Contract;
import com.wd.health.home.mvp.p.PrsenterImpl;
import com.wd.health.home.url.MyUrl;

import cn.jpush.android.service.JCommonService;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/4 14:41
 */
public class IMService extends JCommonService{

    private Context mContext = this;

    public IMService() {
        JMessageClient.registerEventReceiver(this);
    }

    public void onEvent(NotificationClickEvent event) {
        Log.i("tag", "点击接口");
        Intent notificationIntent = new Intent(mContext, CurrentCActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Message message = event.getMessage();
        notificationIntent.putExtra("service", 1);
        mContext.startActivity(notificationIntent);//自定义跳转到指定页面
    }

}
