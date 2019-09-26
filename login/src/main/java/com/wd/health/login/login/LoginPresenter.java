package com.wd.health.login.login;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/7 8:56
 * Description  :      登录请求类
 */
public class LoginPresenter extends BasePresenter<IRequest> {

    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.login((String) args[0], (String) args[1]);
    }
}
