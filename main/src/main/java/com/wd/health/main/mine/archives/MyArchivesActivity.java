package com.wd.health.main.mine.archives;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.base.BaseActivity;
import com.wd.health.main.R;

public class MyArchivesActivity extends BaseActivity implements View.OnClickListener {


    private AppCompatImageView mTitleBackIv;
    private AppCompatTextView mTitleTitleTv;
    private AppCompatImageView mNoInfoIv;
    private AppCompatTextView mNoInfoTv;
    private AppCompatButton mNoInfoBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.colorAppWhite).autoDarkModeEnable(true).init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_archives;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        mTitleBackIv = (AppCompatImageView) findViewById(R.id.title_back_iv);
        mTitleBackIv.setOnClickListener(this);
        mTitleTitleTv = (AppCompatTextView) findViewById(R.id.title_title_tv);
        mNoInfoIv = (AppCompatImageView) findViewById(R.id.no_info_iv);
        mNoInfoTv = (AppCompatTextView) findViewById(R.id.no_info_tv);
        mNoInfoBt = (AppCompatButton) findViewById(R.id.no_info_bt);
        mNoInfoBt.setOnClickListener(this);
        mTitleTitleTv.setText(R.string.mine_user_archives);
    }

    @Override
    protected void destroyData() {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back_iv) {
            ActivityUtils.finishActivity(this);
        }
    }
}
