package com.wd.health.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.login.register.RegisteredPresenter;
import com.wd.health.login.register.SendEmailPresenter;
import com.wd.health.model.DataCall;
import com.wd.health.utils.RsaCoder;
import com.wd.health.utils.SpaceFilter;

import java.util.Objects;

@Route(path = Constant.ACTIVITY_URL_REGISTER)
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText mLoginInputEmailEt;           //邮箱账号
    private AppCompatButton mLoginInputShow1;               //获取验证码
    private AppCompatEditText mLoginInputCodeEt;            //验证码
    private AppCompatEditText mLoginInputPwd1Et;            //密码
    private AppCompatImageView mLoginInputPwd1ShowIv;       //密码显示
    private AppCompatEditText mLoginInputPwd2Et;            //确认密码
    private AppCompatImageView mLoginInputPwd2ShowIv;       //确认密码显示
    private AppCompatEditText mLoginInputInviteEt;          //邀请码
    private AppCompatButton mRegisteredRegistereBt;         //注册
    private boolean pas1Visibile = false;                   //密码显示隐藏
    private boolean pas2Visibile = false;                   //再次输入密码显示隐藏
    private SendEmailPresenter mSendEmailPresenter;
    private CountDownTimer mTimer;
    private RegisteredPresenter mRegisteredPresenter;
    private String mEmail;
    private String mCode;
    private String mPwd1;
    private String mPwd2;
    private String mInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initData() {

        mLoginInputEmailEt.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputCodeEt.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputPwd1Et.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputPwd2Et.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputInviteEt.setFilters(new InputFilter[]{new SpaceFilter()});

        //发送邮箱验证码
        mSendEmailPresenter = new SendEmailPresenter(new SendEmailDataCall());
        //发送注册请求
        mRegisteredPresenter = new RegisteredPresenter(new RegisteredDataCall());
    }

    @Override
    protected void initView() {

        mLoginInputEmailEt = (AppCompatEditText) findViewById(R.id.login_input_email_et);
        mLoginInputShow1 = (AppCompatButton) findViewById(R.id.login_input_show1);
        mLoginInputShow1.setOnClickListener(this);
        mLoginInputCodeEt = (AppCompatEditText) findViewById(R.id.login_input_code_et);
        mLoginInputPwd1Et = (AppCompatEditText) findViewById(R.id.login_input_pwd1_et);
        mLoginInputPwd1ShowIv = (AppCompatImageView) findViewById(R.id.login_input_pwd1_show_iv);
        mLoginInputPwd1ShowIv.setOnClickListener(this);
        mLoginInputPwd2Et = (AppCompatEditText) findViewById(R.id.login_input_pwd2_et);
        mLoginInputPwd2ShowIv = (AppCompatImageView) findViewById(R.id.login_input_pwd2_show_iv);
        mLoginInputPwd2ShowIv.setOnClickListener(this);
        mLoginInputInviteEt = (AppCompatEditText) findViewById(R.id.login_input_invite_et);
        mRegisteredRegistereBt = (AppCompatButton) findViewById(R.id.registered_registere_bt);
        mRegisteredRegistereBt.setOnClickListener(this);
    }

    @Override
    protected void destroyData() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        mEmail = Objects.requireNonNull(mLoginInputEmailEt.getText()).toString();
        mCode = Objects.requireNonNull(mLoginInputCodeEt.getText()).toString();
        mPwd1 = Objects.requireNonNull(mLoginInputPwd1Et.getText()).toString();
        mPwd2 = Objects.requireNonNull(mLoginInputPwd2Et.getText()).toString();
        mInvite = Objects.requireNonNull(mLoginInputInviteEt.getText()).toString();
        int i = v.getId();
        if (i == R.id.login_input_show1) {
            if (!RegexUtils.isEmail(mEmail)) {
                ToastUtils.showShort(R.string.login_correct_email);
                return;
            } else {
                mSendEmailPresenter.reqeust(mEmail);
            }
        }
        if (i == R.id.login_input_pwd1_show_iv) {
            if (pas1Visibile) {//密码显示，则隐藏
                mLoginInputPwd1Et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLoginInputPwd1ShowIv.setImageResource(R.drawable.login_icon_hide_password_n);
                pas1Visibile = false;
            } else {//密码隐藏则显示
                mLoginInputPwd1Et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mLoginInputPwd1ShowIv.setImageResource(R.drawable.login_icon_show_password);
                pas1Visibile = true;
            }
        }
        if (i == R.id.login_input_pwd2_show_iv) {
            if (pas2Visibile) {//密码显示，则隐藏
                mLoginInputPwd2Et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLoginInputPwd2ShowIv.setImageResource(R.drawable.login_icon_hide_password_n);
                pas2Visibile = false;
            } else {//密码隐藏则显示
                mLoginInputPwd2Et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mLoginInputPwd2ShowIv.setImageResource(R.drawable.login_icon_show_password);
                pas2Visibile = true;
            }
        }
        if (i == R.id.registered_registere_bt) {
            if (!RegexUtils.isEmail(mEmail)) {
                ToastUtils.showShort(R.string.login_correct_email);
                return;
            }
            if (!StringUtils.equals(mPwd1, mPwd2)) {
                ToastUtils.showShort(R.string.registered_pwd_false);
                LogUtils.d(mPwd1, mPwd2);
                return;
            }
            if (StringUtils.isEmpty(mInvite)) {
                try {
                    String mpdws = RsaCoder.encryptByPublicKey(mPwd1);
                    mRegisteredPresenter.reqeust(mEmail, mCode, mpdws, mpdws, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String mpdws = RsaCoder.encryptByPublicKey(mPwd1);
                    mRegisteredPresenter.reqeust(mEmail, mCode, mpdws, mpdws, mInvite);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    class RegisteredDataCall implements DataCall {

        @Override
        public void onSuccess(Object result, String message) {
            ToastUtils.showShort(message);
            if (StringUtils.equals(message, "注册成功")) {
                Bundle bundle = new Bundle();
                bundle.putString("mail", mEmail);
                bundle.putString("pwd", mPwd1);
                intentByRouter(Constant.ACTIVITY_URL_LOGIN, bundle);
                ActivityUtils.finishActivity(RegisteredActivity.this);
            }
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }
}
