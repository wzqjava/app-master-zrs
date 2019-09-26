package com.wd.health.login.register;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/7 13:55
 * Description  :   发送邮箱请求类
 */
public class SendEmailPresenter extends BasePresenter<IRequest> {
    public SendEmailPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.sendOutEmailCode((String) args[0]);
    }
}
