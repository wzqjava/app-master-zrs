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
 * Date :  2019/8/2 22:53
 * Description  :  Fragment基础类
 */
public abstract class BaseFragment extends Fragment {

    public SPUtils mSpUtils;
    private View mInflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);
        mSpUtils = SPUtils.getInstance("LeeSP.xml");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null == mInflate){
            mInflate = inflater.inflate(setLayoutView(), container, false);
        }else{
            ViewGroup viewGroup = (ViewGroup) mInflate.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mInflate);
            }
        }

        return mInflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置沉浸式
        initImmersionBar();
        initView(view);
        setViewData(view);
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

    protected abstract void initImmersionBar();

    protected abstract void setViewData(View view);

    protected abstract void initView(View view);

    public abstract int setLayoutView();

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(getContext());
    }

    /**
     * @param path   传送Activity的 （带有值的）
     * @param bundle 传值
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getContext());
    }
}
