package com.wd.health.main.mine.settings;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.base.BaseActivity;
import com.wd.health.main.R;


public class MySettingsActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatImageView mTitleBackIv;
    private AppCompatTextView mTitleTitleTv;
    private SimpleDraweeView mSettingsUserIv;
    private AppCompatTextView mSettingsUserName;
    private AppCompatImageView mSettingsUserNext;
    private RelativeLayout mSettingsUserRl;
    private RelativeLayout mSettingsPwdRl;
    private AppCompatTextView mSettingsCacheNum;
    private RelativeLayout mSettingsCacheRl;
    private RelativeLayout mSettingsBrightnessRl;
    private RelativeLayout mSettingsVersionRl;
    private RelativeLayout mSettingsHelpRl;
    private RelativeLayout mSettingsAboutRl;
    private RelativeLayout mSettingsInviteRl;

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
        return R.layout.activity_my_settings;
    }

    @Override
    protected void initData() {
        mTitleTitleTv.setText(R.string.mine_set_settings);
    }

    @Override
    protected void initView() {

        mTitleBackIv = (AppCompatImageView) findViewById(R.id.title_back_iv);
        mTitleBackIv.setOnClickListener(this);
        mTitleTitleTv = (AppCompatTextView) findViewById(R.id.title_title_tv);
        mSettingsUserName = (AppCompatTextView) findViewById(R.id.settings_user_name);
        mSettingsUserName.setOnClickListener(this);
        mSettingsUserNext = (AppCompatImageView) findViewById(R.id.settings_user_next);
        mSettingsUserNext.setOnClickListener(this);
        mSettingsUserRl = (RelativeLayout) findViewById(R.id.settings_user_rl);
        mSettingsUserRl.setOnClickListener(this);
        mSettingsPwdRl = (RelativeLayout) findViewById(R.id.settings_pwd_rl);
        mSettingsPwdRl.setOnClickListener(this);
        mSettingsCacheNum = (AppCompatTextView) findViewById(R.id.settings_cache_num);
        mSettingsCacheNum.setOnClickListener(this);
        mSettingsCacheRl = (RelativeLayout) findViewById(R.id.settings_cache_rl);
        mSettingsCacheRl.setOnClickListener(this);
        mSettingsBrightnessRl = (RelativeLayout) findViewById(R.id.settings_brightness_rl);
        mSettingsBrightnessRl.setOnClickListener(this);
        mSettingsVersionRl = (RelativeLayout) findViewById(R.id.settings_version_rl);
        mSettingsVersionRl.setOnClickListener(this);
        mSettingsHelpRl = (RelativeLayout) findViewById(R.id.settings_help_rl);
        mSettingsHelpRl.setOnClickListener(this);
        mSettingsAboutRl = (RelativeLayout) findViewById(R.id.settings_about_rl);
        mSettingsAboutRl.setOnClickListener(this);
        mSettingsInviteRl = (RelativeLayout) findViewById(R.id.settings_invite_rl);
        mSettingsInviteRl.setOnClickListener(this);
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
