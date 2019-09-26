package com.wd.health.home.mvp.v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wd.health.home.base.BasePrsenter;
import com.wd.health.home.mvp.Contract;
import com.wd.health.home.mvp.p.PrsenterImpl;

/**
 * @author 荣生
 * @description:
 * @date :2019/7/6 9:36
 */
public abstract class BaseActivity extends AppCompatActivity implements Contract.IView {


    public BasePrsenter prsenter;

    public abstract int initLayout();
    public abstract void  initView();
    public abstract void  initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        prsenter = new PrsenterImpl();
        prsenter.onAttch(this);
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prsenter.unAttch();
    }
}
