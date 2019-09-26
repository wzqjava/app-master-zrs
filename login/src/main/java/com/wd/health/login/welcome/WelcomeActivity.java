package com.wd.health.login.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.login.LoginActivity;
import com.wd.health.login.R;

@Route(path = Constant.ACTIVITY_URL_WELCOME)
public class WelcomeActivity extends BaseActivity {

    private ConstraintLayout mWelcomeCl;
    private AppCompatTextView mWelcomeTv;
    private MyThread mMyThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        mWelcomeCl.setBackgroundResource(R.drawable.start_page);
        mWelcomeTv.setText(R.string.welcome_tv);
        myThread();

    }

    @Override
    protected void initView() {
        mWelcomeCl = (ConstraintLayout) findViewById(R.id.welcome_cl);
        mWelcomeTv = (AppCompatTextView) findViewById(R.id.welcome_tv);
    }

    @Override
    protected void destroyData() {
        mMyThread.close();
    }

    private void myThread() {
        mMyThread = new MyThread();
        mMyThread.start();
    }

    class MyThread extends Thread {
        private boolean mRunning = false;

        @Override
        public void run() {
            mRunning = true;
            while (mRunning) {
                SystemClock.sleep(2000);
                mRunning = false;
                date();
            }
        }

        public void close() {
            mRunning = false;
        }
    }

    //记录是否第一次进入App
    private void date() {
        boolean islogin = mSpUtils.getBoolean("islogin", true);
        if (islogin) {
            //第一次进入跳转
            intentByRouter(Constant.ACTIVITY_URL_GUIDEPAGE);
            ActivityUtils.finishActivity(this);
            mSpUtils.put("islogin", false);
        } else {
            //第二次进入跳转
            intentByRouter(Constant.ACTIVITY_URL_LOGIN);
            ActivityUtils.finishActivity(this);
            Intent in = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }
    }
}
