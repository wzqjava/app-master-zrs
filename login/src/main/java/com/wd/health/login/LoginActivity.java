package com.wd.health.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.config.ConfigApp;
import com.wd.health.core.exception.ApiException;
import com.wd.health.login.login.LoginPresenter;
import com.wd.health.model.DataCall;
import com.wd.health.model.bean.UserInfoBean;
import com.wd.health.utils.RsaCoder;
import com.wd.health.utils.SpaceFilter;

import java.util.List;
import java.util.Objects;

/**
 * Created :  LiZhIX
 * Date :  2019/8/5 14:56
 * Description  :  登录页面
 */
@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText mLoginInputEmailEt;       //邮箱账号
    private AppCompatEditText mLoginInputPwdEt;         //密码
    private AppCompatImageView mLoginInputShowIv;       //密码显示隐藏
    private AppCompatTextView mLoginForgotPwd;          //忘记密码
    private AppCompatTextView mLoginRegistereNow;       //立即注册
    private AppCompatImageView mLoginWechat;            //微信登录
    private AppCompatButton mLoginBt;                   //登录
    private boolean pasVisibile = false;                //密码显示隐藏按钮
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mLoginInputEmailEt.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputPwdEt.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginPresenter = new LoginPresenter(new LoginDataCall());
        Bundle bundle = LoginActivity.this.getIntent().getExtras();
        if (bundle != null) {
            String mail = bundle.getString("mail");
            String pwd = bundle.getString("pwd");
            String newPwd = bundle.getString("newPwd");

            mLoginInputEmailEt.setText(mail);
            mLoginInputPwdEt.setText(pwd);

            mLoginInputPwdEt.setText(newPwd);
        }

    }

    @Override
    protected void initView() {

        mLoginInputEmailEt = (AppCompatEditText) findViewById(R.id.login_input_email_et);
        mLoginInputPwdEt = (AppCompatEditText) findViewById(R.id.login_input_pwd_et);
        mLoginInputShowIv = (AppCompatImageView) findViewById(R.id.login_input_show_iv);
        mLoginInputShowIv.setOnClickListener(this);
        mLoginForgotPwd = (AppCompatTextView) findViewById(R.id.login_forgot_pwd);
        mLoginForgotPwd.setOnClickListener(this);
        mLoginRegistereNow = (AppCompatTextView) findViewById(R.id.login_registere_now);
        mLoginRegistereNow.setOnClickListener(this);
        mLoginWechat = (AppCompatImageView) findViewById(R.id.login_wechat);
        mLoginWechat.setOnClickListener(this);
        mLoginBt = (AppCompatButton) findViewById(R.id.login_input_login_bt);
        mLoginBt.setOnClickListener(this);
    }

    class LoginDataCall implements DataCall<UserInfoBean> {

        @Override
        public void onSuccess(UserInfoBean result, String message) {
            //TODO  Toast格式  message无法添加到textview中
//            View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_toast, null);
//            AppCompatTextView toastTv = view.findViewById(R.id.toast_tv);
//            toastTv.setText(message);
//            ToastUtils.showCustomShort(R.layout.layout_toast);

            ToastUtils.showShort(message);


            if (StringUtils.equals(message, "登录成功")) {
                List<UserInfoBean> userInfoBeans = mUserInfoBeanDao.loadAll();
                SharedPreferences login_info = ConfigApp.context.getSharedPreferences("login_info", MODE_PRIVATE);
                SharedPreferences.Editor edit = login_info.edit();

                for (int i = 0; i < userInfoBeans.size(); i++) {
                    UserInfoBean userInfoBean = userInfoBeans.get(i);
                    userInfoBean.setStatus(1);
                }
                mUserInfoBeanDao.insertOrReplaceInTx(userInfoBeans);
                //登录返回，证明登录成功，可存放数据库
                Long id = result.getId();
                String sessionId = result.getSessionId();
                result.setSessionId(sessionId);
                result.setStatus(1);
                result.setId(id);
                //存到SharedPreferences
                edit.putLong("userId",id);
                edit.putString("sessionId",sessionId);
                edit.commit();

                //存数据库
                mUserInfoBeanDao.insertOrReplaceInTx(result);

//                intentByRouter(Constant.ACTIVITY_URL_MAIN);
                intentByRouter(Constant.ACTIVITY_URL_MAIN);
            }
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_input_show_iv) {

            if (pasVisibile) {//密码显示，则隐藏
                mLoginInputPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLoginInputShowIv.setImageResource(R.drawable.login_icon_hide_password_n);
                pasVisibile = false;
            } else {//密码隐藏则显示
                mLoginInputPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mLoginInputShowIv.setImageResource(R.drawable.login_icon_show_password);
                pasVisibile = true;
            }

        }
        if (v.getId() == R.id.login_forgot_pwd) {
            intentByRouter(Constant.ACTIVITY_URL_FORGOTPWD);
        }
        if (v.getId() == R.id.login_registere_now) {
            intentByRouter(Constant.ACTIVITY_URL_REGISTER);
        }
        if (v.getId() == R.id.login_wechat) {
            //TODO  微信登录
            wxLogin();
        }
        if (v.getId() == R.id.login_input_login_bt) {

            String email = Objects.requireNonNull(mLoginInputEmailEt.getText()).toString();
            String pwd = Objects.requireNonNull(mLoginInputPwdEt.getText()).toString();
            if (!RegexUtils.isEmail(email)) {
                ToastUtils.showShort(R.string.login_correct_email);
                return;
            }
            if (StringUtils.isEmpty(pwd)) {
                ToastUtils.showShort(R.string.login_pwd_et);
                return;
            }
            try {
                mLoginPresenter.reqeust(email, RsaCoder.encryptByPublicKey(pwd));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void wxLogin() {
        if (!ConfigApp.mIwxapi.isWXAppInstalled()) {
            ToastUtils.showShort(R.string.no_wechat);
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "diandi_wx_login";
        ConfigApp.mIwxapi.sendReq(req);
    }

}

