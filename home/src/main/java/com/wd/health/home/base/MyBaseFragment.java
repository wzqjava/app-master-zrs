package com.wd.health.home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wd.health.base.BaseFragment;
import com.wd.health.config.ConfigApp;
import com.wd.health.core.db.DaoSession;
import com.wd.health.core.db.UserInfoBeanDao;
import com.wd.health.home.mvp.Contract;
import com.wd.health.home.mvp.p.PrsenterImpl;
import com.wd.health.model.bean.UserInfoBean;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/6 17:23
 */
public abstract class MyBaseFragment extends BaseFragment implements Contract.IView {

    public BasePrsenter prsenter;
    public UserInfoBeanDao mUserInfoBeanDao;
    public UserInfoBean userInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        prsenter = new PrsenterImpl();
        prsenter.onAttch(this);
        initUserInfo();
        super.onCreate(savedInstanceState);
    }


    /**
     * 获取当当前登录个人信息
     */
    protected void initUserInfo() {
        ConfigApp myApp = (ConfigApp) getActivity().getApplication();
        DaoSession daoSession = myApp.getDaoSession();
        mUserInfoBeanDao = daoSession.getUserInfoBeanDao();
        Query<UserInfoBean> build = mUserInfoBeanDao.queryBuilder()
                .where(UserInfoBeanDao.Properties.Status.eq(1))
                .build();
        List<UserInfoBean> list = build.list();
        userInfo = list.get(list.size() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prsenter.unAttch();
    }

}
