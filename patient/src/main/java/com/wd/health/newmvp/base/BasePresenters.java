package com.wd.health.newmvp.base;

import com.wd.health.newmvp.bean.Result;
import com.wd.health.newmvp.model.DataCalls;
import com.wd.health.newmvp.model.IRequests;
import com.wd.health.newmvp.util.HttpUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者:毛佳翔 by Administor on 2019/8/13 10:21
 * 邮箱:1447925431@qq.com
 */
public abstract class BasePresenters {
    private DataCalls dataCalls;

    public BasePresenters(DataCalls dataCalls) {
        this.dataCalls = dataCalls;
    }
    boolean isRunning;
    public void requestData(Object...args){
        if (isRunning){
            return;
        }
        isRunning = true;
        IRequests iRequests = HttpUtil.getInstance().create(IRequests.class);
        getModel(iRequests,args)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result o) throws Exception {
                         if (o.status.equals("0000")){
                             dataCalls.onSuccess(o.result);
                         }else {
                             dataCalls.onFail(o);
                         }
                    }
                });
    }

    protected abstract Observable getModel(IRequests iRequests,Object...args);
}
