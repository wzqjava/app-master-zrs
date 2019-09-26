package com.wd.health.presenter;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * 作者:毛佳翔 by Administor on 2019/8/20 15:32
 * 邮箱:1447925431@qq.com
 */
public class CommentPresenter extends BasePresenter<IRequest> {
    public CommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.comment((String)args[0],(String)args[1],(String)args[2]);
    }
}
