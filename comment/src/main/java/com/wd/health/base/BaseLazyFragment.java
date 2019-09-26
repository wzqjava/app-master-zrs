package com.wd.health.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 23:01
 * Description  :  懒加载Fragment
 */
public abstract class BaseLazyFragment extends Fragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    public SPUtils mSpUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);
        mSpUtils = SPUtils.getInstance("LeeSP.xml");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(setLayoutView(), container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onStart() {
        super.onStart();
        BusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusUtils.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }

    protected abstract int setLayoutView();


    protected abstract void initView(View view);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(getContext());
    }

    /**
     * @param path   传送Activity的 （带有值的）
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getContext());
    }

    protected abstract void loadData();


}