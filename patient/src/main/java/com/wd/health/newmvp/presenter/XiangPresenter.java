package com.wd.health.newmvp.presenter;

import com.wd.health.newmvp.base.BasePresenters;
import com.wd.health.newmvp.model.DataCalls;
import com.wd.health.newmvp.model.IRequests;

import io.reactivex.Observable;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:28
 * 邮箱:1447925431@qq.com
 */
public class XiangPresenter extends BasePresenters {
    public XiangPresenter(DataCalls dataCalls) {
        super(dataCalls);
    }

    @Override
    protected Observable getModel(IRequests iRequests, Object... args) {
        return iRequests.findSickCircleInfo((String)args[0],(String)args[1],(String)args[2]);
    }
}
