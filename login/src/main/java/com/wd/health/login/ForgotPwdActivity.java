package com.wd.health.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.login.register.CheckCodePresenter;
import com.wd.health.login.register.SendEmailPresenter;
import com.wd.health.model.DataCall;
import com.wd.health.utils.SpaceFilter;

import java.util.Objects;

@Route(path = Constant.ACTIVITY_URL_FORGOTPWD)
public class ForgotPwdActivity extends BaseActivity implements View.OnClickListener {

    private View mTitleLineWhite;
    private AppCompatImageView mTitleBackIv;            //返回按钮
    private AppCompatTextView mTitleTitleTv;            //标题
    private Toolbar mToolbar;
    private AppCompatEditText mLoginInputNewPwd1;       //邮箱账号
    private AppCompatButton mLoginInputShow1;           //获取验证码
    private AppCompatButton mLoginInputNext;            //下一步
    private AppCompatEditText mLoginInputNewPwd2;       //验证码
    private SendEmailPresenter mSendEmailPresenter;
    private String mEmail;
    private CountDownTimer mTimer;
    private String mCode;
    private CheckCodePresenter mCheckCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.toolbar).keyboardEnable(true).init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_forgot_pwd;
    }

    @Override
    protected void initData() {
        mLoginInputNewPwd1.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputNewPwd2.setFilters(new InputFilter[]{new SpaceFilter()});

        mSendEmailPresenter = new SendEmailPresenter(new SendEmailDataCall());
        mCheckCodePresenter = new CheckCodePresenter(new CheckCodeDataCall());
    }

    @Override
    protected void initView() {

        mTitleLineWhite = (View) findViewById(R.id.title_line_white);
        mTitleLineWhite.setVisibility(View.GONE);

        mTitleBackIv = (AppCompatImageView) findViewById(R.id.title_back_iv);
        mTitleBackIv.setOnClickListener(this);
        mTitleTitleTv = (AppCompatTextView) findViewById(R.id.title_title_tv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLoginInputNewPwd1 = (AppCompatEditText) findViewById(R.id.login_input_new_pwd1);
        mLoginInputShow1 = (AppCompatButton) findViewById(R.id.login_input_show1);
        mLoginInputShow1.setOnClickListener(this);
        mLoginInputNext = (AppCompatButton) findViewById(R.id.login_input_next);
        mLoginInputNext.setOnClickListener(this);
        mLoginInputNewPwd2 = (AppCompatEditText) findViewById(R.id.login_input_new_pwd2);

        mTitleTitleTv.setText(R.string.login_forgot_pwd);
    }

    @Override
    protected void destroyData() {
        if (null != mTimer) {
            mTimer.cancel();
        }
    }

    class CheckCodeDataCall implements DataCall {

        @Override
        public void onSuccess(Object result, String message) {
            if (StringUtils.equals(message, "验证通过")) {
                intentByRouter(Constant.ACTIVITY_URL_NEWPWD);
            } else {
                ToastUtils.showShort(message);
            }
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    class SendEmailDataCall implements DataCall {

        @Override
        public void onSuccess(Object result, String message) {
            ToastUtils.showShort(message);
            mLoginInputShow1.setEnabled(false);
            mLoginInputShow1.setBackgroundResource(R.drawable.shape_blue_btn_unclick_small);
            mTimer = new CountDownTimer(30000, 1000) {

                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTick(long millisUntilFinished) {
                    mLoginInputShow1.setText((millisUntilFinished / 1000) + " ");
                }

                @Override
                public void onFinish() {
                    mLoginInputShow1.setEnabled(true);
                    mLoginInputShow1.setText(R.string.forgot_pwd_get_code);
                    mLoginInputShow1.setBackgroundResource(R.drawable.shape_blue_btn);
                }
            }.start();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        mEmail = Objects.requireNonNull(mLoginInputNewPwd1.getText()).toString();
        mCode = Objects.requireNonNull(mLoginInputNewPwd2.getText()).toString();
        if (i == R.id.login_input_show1) {
            if (!RegexUtils.isEmail(mEmail)) {
                ToastUtils.showShort(R.string.login_correct_email);
                return;
            } else {
                mSendEmailPresenter.reqeust(mEmail);
            }
        } else if (i == R.id.login_input_next) {
            mCheckCodePresenter.reqeust(mEmail, mCode);
        } else if (i == R.id.title_back_iv) {
            ActivityUtils.finishActivity(this);
        }

    }

}
