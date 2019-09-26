package com.wd.health.home.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.base.BaseActivity;
import com.wd.health.config.ConfigApp;
import com.wd.health.core.db.DaoSession;
import com.wd.health.core.db.UserInfoBeanDao;
import com.wd.health.home.httpbean.NoticeReadNumBean;
import com.wd.health.home.mvp.Contract;
import com.wd.health.home.mvp.p.PrsenterImpl;
import com.wd.health.home.url.MyUrl;
import com.wd.health.model.bean.UserInfoBean;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/6 15:01
 */
public abstract class MyBaseActivity extends BaseActivity implements Contract.IView {

    public BasePrsenter prsenter;
    private UserInfoBean userInfoBean;
    public UserInfoBean userInfo;
    public int  NoRead_num=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        prsenter = new PrsenterImpl();
        prsenter.onAttch(this);
        initUserInfo();
        super.onCreate(savedInstanceState);

    }

    /**
     * 获取当当前登录个人信息
     */
    protected void initUserInfo() {
        ConfigApp myApp = (ConfigApp) getApplication();
        DaoSession daoSession = myApp.getDaoSession();
        mUserInfoBeanDao = daoSession.getUserInfoBeanDao();
        Query<UserInfoBean> build = mUserInfoBeanDao.queryBuilder()
                .where(UserInfoBeanDao.Properties.Status.eq(1))
                .build();
        List<UserInfoBean> list = build.list();
        userInfo = list.get(list.size() - 1);

        //查询未读
        prsenter.getMethod(MyUrl.NoticeReadNum, NoticeReadNumBean.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prsenter.unAttch();
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
    }
}
