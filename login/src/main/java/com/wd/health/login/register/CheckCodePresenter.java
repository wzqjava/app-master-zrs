package com.wd.health.login.register;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/8 17:06
 * Description  :    检查邮箱代码请求类
 */
public class CheckCodePresenter extends BasePresenter<IRequest> {

    public CheckCodePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.checkCode((String) args[0], (String) args[1]);
    }
}
