package com.wd.health.presenter;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * 作者:毛佳翔 by Administor on 2019/8/21 18:15
 * 邮箱:1447925431@qq.com
 */
public class KeShiQueryPresenter extends BasePresenter<IRequest> {
    public KeShiQueryPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.keshiquery((int)args[0]);
    }
}
