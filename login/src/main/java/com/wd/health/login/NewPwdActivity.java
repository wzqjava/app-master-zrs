package com.wd.health.login;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.login.register.CheckCodePresenter;
import com.wd.health.model.DataCall;
import com.wd.health.utils.RsaCoder;
import com.wd.health.utils.SpaceFilter;

import java.util.Objects;

/**
 * Created :  LiZhIX
 * Date :  2019/8/5 20:56
 * Description  :  新密码页面
 */
@Route(path = Constant.ACTIVITY_URL_NEWPWD)
public class NewPwdActivity extends BaseActivity implements View.OnClickListener {

    private View mTitleLineWhite;
    private AppCompatImageView mTitleBackIv;             //返回按钮
    private AppCompatTextView mTitleTitleTv;             //标题
    private Toolbar mToolbar;
    private AppCompatEditText mLoginInputNewPwd1;        //输入新密码确认密码
    private AppCompatImageView mLoginInputShow1;         //重置密码显示隐藏1
    private AppCompatEditText mLoginInputNewPwd2;        //再次确认新密码密码
    private AppCompatImageView mLoginInputShow2;         //重置密码显示隐藏2
    private AppCompatButton mLoginInputComplete;         //完成按钮
    private boolean pas1Visibile = false;                //密码显示隐藏
    private boolean pas2Visibile = false;                //再次输入密码显示隐藏
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
        return R.layout.activity_new_pwd;
    }

    @Override
    protected void initData() {
        mLoginInputNewPwd1.setFilters(new InputFilter[]{new SpaceFilter()});
        mLoginInputNewPwd2.setFilters(new InputFilter[]{new SpaceFilter()});
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
        mLoginInputShow1 = (AppCompatImageView) findViewById(R.id.login_input_show1);
        mLoginInputShow1.setOnClickListener(this);
        mLoginInputNewPwd2 = (AppCompatEditText) findViewById(R.id.login_input_new_pwd2);
        mLoginInputShow2 = (AppCompatImageView) findViewById(R.id.login_input_show2);
        mLoginInputShow2.setOnClickListener(this);
        mLoginInputComplete = (AppCompatButton) findViewById(R.id.login_input_complete);
        mLoginInputComplete.setOnClickListener(this);

        mTitleTitleTv.setText(R.string.login_correct_new_pwd);
    }

    @Override
    protected void destroyData() {

    }

    class CheckCodeDataCall implements DataCall {

        @Override
        public void onSuccess(Object result, String message) {
            ToastUtils.showShort(message);
            if (StringUtils.equals(message, "修改成功")) {
                Bundle bundle = new Bundle();
                bundle.putString("newPwd", Objects.requireNonNull(mLoginInputNewPwd2.getText()).toString());
                intentByRouter(Constant.ACTIVITY_URL_LOGIN, bundle);
                ActivityUtils.finishActivity(NewPwdActivity.this);
            }
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String pwd1 = Objects.requireNonNull(mLoginInputNewPwd1.getText()).toString();
        String pwd2 = Objects.requireNonNull(mLoginInputNewPwd1.getText()).toString();
        if (id == R.id.title_back_iv) {
            ActivityUtils.finishActivity(this);
        } else if (id == R.id.login_input_show1) {
            if (pas1Visibile) {//密码显示，则隐藏
                mLoginInputNewPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLoginInputShow1.setImageResource(R.drawable.login_icon_hide_password_n);
                pas1Visibile = false;
            } else {//密码隐藏则显示
                mLoginInputNewPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mLoginInputShow1.setImageResource(R.drawable.login_icon_show_password);
                pas1Visibile = true;
            }
        } else if (id == R.id.login_input_show2) {
            if (pas2Visibile) {//密码显示，则隐藏
                mLoginInputNewPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mLoginInputShow2.setImageResource(R.drawable.login_icon_hide_password_n);
                pas2Visibile = false;
            } else {//密码隐藏则显示
                mLoginInputNewPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mLoginInputShow2.setImageResource(R.drawable.login_icon_show_password);
                pas2Visibile = true;
            }
        } else if (id == R.id.login_input_complete) {
            try {
                mCheckCodePresenter.reqeust(RsaCoder.encryptByPublicKey(pwd1), RsaCoder.encryptByPublicKey(pwd2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
