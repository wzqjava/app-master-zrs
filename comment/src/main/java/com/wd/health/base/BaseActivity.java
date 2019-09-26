package com.wd.health.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.config.ConfigApp;
import com.wd.health.core.db.DaoSession;
import com.wd.health.core.db.UserInfoBeanDao;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 22:41
 * Description  :  Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private long exitTime = 0;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public static SPUtils mSpUtils;
    public UserInfoBeanDao mUserInfoBeanDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置UI
        setContentView(getLayoutRes());
        //设置沉浸式
        initImmersionBar();

        //设置沉浸式
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        //请求读写权限
        requestStoragePermission();
        //请求手机设备信息权限
        requestPhonePermission();
        //路由服务注入
        ARouter.getInstance().inject(this);
        mSpUtils = SPUtils.getInstance("LeeSP.xml");
        ConfigApp myApp = (ConfigApp) getApplication();
        DaoSession daoSession =  myApp.getDaoSession();
        mUserInfoBeanDao = daoSession.getUserInfoBeanDao();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyData();
        hideSoftKeyboard();
    }

    private void requestStoragePermission() {
        if (!PermissionUtils.isGranted(PermissionConstants.STORAGE)) {
            PermissionUtils.permission(PermissionConstants.STORAGE)
                    //当用户点击拒绝以后，每次打开APP都弹出请求权限
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(ShouldRequest shouldRequest) {
                            shouldRequest.again(true);
                            finish();
                        }
                    })
                    .request();
        }

    }

    private void requestPhonePermission() {
        if (!PermissionUtils.isGranted(PermissionConstants.PHONE)) {
            PermissionUtils.permission(PermissionConstants.PHONE)
                    //当用户点击拒绝以后，每次打开APP都弹出请求权限
                    .rationale(new PermissionUtils.OnRationaleListener() {
                        @Override
                        public void rationale(ShouldRequest shouldRequest) {
                            shouldRequest.again(true);
                            finish();
                        }
                    })
                    .request();
        }

    }

    protected abstract void initImmersionBar();

    protected abstract int getLayoutRes();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void destroyData();

    @Override
    public Resources getResources() {
        //禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 延迟执行
     */
    public final boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    public final boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent);
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(this);
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(this);
    }

}
