package com.wd.health.presenter;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/14 16:45
 * Description  :      查询健康讲堂类目 请求类
 */
public class FindVideoCategoryListPresenter extends BasePresenter<IRequest> {

    public FindVideoCategoryListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.findVideoCategoryList();
    }
}
