package com.wd.health.presenter;

import com.wd.health.base.BasePresenter;
import com.wd.health.core.http.IRequest;
import com.wd.health.model.DataCall;

import io.reactivex.Observable;

/**
 * Created :  LiZhIX
 * Date :  2019/8/15 9:25
 * Description  :   查询视频列表请求类
 */
public class VideoPresenter extends BasePresenter<IRequest> {

    public VideoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable mObservable(Object... args) {
        return iRequest.findVideoVoList((int) args[0], (int) args[1], (int) args[2]);
    }
}
