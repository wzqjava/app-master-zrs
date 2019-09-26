package com.wd.health.login.register;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/7 15:07
 * Description  :    注册请求类
 */
public class RegisteredPresenter extends BasePresenter<IRequest> {
    public RegisteredPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.register((String) args[0], (String) args[1],
                (String) args[2], (String) args[3], (String) args[4]);
    }
}
