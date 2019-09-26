package com.wd.health.main.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.home.HomeFragment;
import com.wd.health.main.R;
import com.wd.health.patient.PatientsFragment;
import com.wd.health.videos.VideoFragment;

import static com.wd.health.home.HomeFragment.TAG_TRANSPARENT;
import static com.wd.health.home.HomeFragment.TAG_WHITE;


/**
 * Created :  LiZhIX
 * Date :  2019/8/4 14:56
 * Description  :  主页面
 */
@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends BaseActivity {

    private long exitTime = 0;
    private AppCompatImageView mMainPatientsIv, mMainCircleIv;
    private RadioButton mMainRgHome, mMainRgVideo, mMainRgPatients;
    private RadioGroup mMainRg;
    private HomeFragment mHomeFragment;
    private PatientsFragment mPatientsFragment;
    private VideoFragment mVideoFragment;
    private Fragment mFragment;//当前显示的Fragment
    private AppCompatImageView mMainShowBottomIv;
    private AppCompatImageView mMainShowTopIv;
    private HideControl mHideControl;
    private Toolbar mToolbar;
    private AppCompatImageView mMainMessage;
    private AppCompatImageView mMainMine;
    private FrameLayout mFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fl, mHomeFragment).commit();
        mFragment = mHomeFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusUtils.removeSticky(TAG_WHITE);
        BusUtils.unregister(this);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.main_toolbar)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        /*
         *  从14开始，library中的资源id就不是final类型的了
         *  所以 这里只能使用if else
         */
        mMainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.main_rg_main) {

                    mMainRgHome.setChecked(true);
                    mMainRgVideo.setChecked(false);
                    mMainRgPatients.setChecked(false);
                    mMainCircleIv.setVisibility(View.GONE);
                    switchFragment(mHomeFragment);
                    mHideControl.endHideTimer();
                    mMainShowBottomIv.setVisibility(View.GONE);
                    mMainShowTopIv.setVisibility(View.GONE);

                } else if (i == R.id.main_rg_patients) {

                    mMainRgHome.setChecked(false);
                    mMainRgVideo.setChecked(false);
                    mMainRgPatients.setChecked(true);
                    mMainCircleIv.setVisibility(View.VISIBLE);
                    switchFragment(mPatientsFragment);
                    mHideControl.endHideTimer();
                    mMainShowBottomIv.setVisibility(View.GONE);
                    mMainShowTopIv.setVisibility(View.GONE);

                } else if (i == R.id.main_rg_video) {

                    mMainRgHome.setChecked(false);
                    mMainRgVideo.setChecked(true);
                    mMainRgPatients.setChecked(false);
                    switchFragment(mVideoFragment);
                    mFrameLayout.setPadding(0, 0, 0, -50);
                    mMainRg.setVisibility(View.GONE);
                    mMainCircleIv.setVisibility(View.GONE);
                    mMainPatientsIv.setVisibility(View.GONE);
                    mMainShowBottomIv.setVisibility(View.VISIBLE);
                    mMainShowTopIv.setVisibility(View.VISIBLE);
                    mMainMessage.setImageResource(R.drawable.common_nav_message_white_n);
                    mToolbar.setBackgroundColor(Color.TRANSPARENT);
                    mToolbar.setVisibility(View.GONE);
                }
            }
        });

        mMainShowBottomIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottom();
                mHideControl.startHideTimer();
            }
        });


        mMainCircleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter(Constant.ACTIVITY_URL_CIRCLE);
            }
        });


        mMainMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("Kong");
            }
        });
        mMainMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter(Constant.ACTIVITY_URL_MINE);
            }
        });

    }

    //修改Home页 顶部颜色  变白
    @BusUtils.Bus(tag = TAG_WHITE, sticky = true)
    public void changeWhite() {
        if (null == mToolbar) {
            mToolbar = findViewById(R.id.main_toolbar);
        } else {
            mToolbar.setBackgroundColor(Color.WHITE);
        }
        if (null == mMainMessage) {
            mMainMessage = findViewById(R.id.main_message);
        } else {
            mMainMessage.setImageResource(R.drawable.common_nav_message_black_n);

        }
    }

    //修改Home页 顶部颜色  变透明
    @BusUtils.Bus(tag = TAG_TRANSPARENT, sticky = true)
    public void changeTransparent() {
        if (null == mToolbar) {
            mToolbar = findViewById(R.id.main_toolbar);
            LogUtils.d(TAG_TRANSPARENT);
        } else {
            mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        if (null == mMainMessage) {
            mMainMessage = findViewById(R.id.main_message);
        } else {
            mMainMessage.setImageResource(R.drawable.common_nav_message_white_n);
        }
    }

    private void showBottom() {
        mMainRg.setVisibility(View.VISIBLE);
        mMainPatientsIv.setVisibility(View.VISIBLE);

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1);
        translateAnimation.setDuration(2000);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        mMainRg.startAnimation(animationSet);
        mMainPatientsIv.startAnimation(animationSet);
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mPatientsFragment = new PatientsFragment();
        mVideoFragment = new VideoFragment();
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView() {
        mMainPatientsIv = findViewById(R.id.main_patients_iv);
        mMainRgHome = findViewById(R.id.main_rg_main);
        mMainRgVideo = findViewById(R.id.main_rg_video);
        mMainRg = findViewById(R.id.main_rg);
        mMainRgPatients = findViewById(R.id.main_rg_patients);
        mToolbar = findViewById(R.id.main_toolbar);
        mMainCircleIv = findViewById(R.id.main_circle_iv);
        mMainMessage = findViewById(R.id.main_message);
        mMainMine = findViewById(R.id.main_mine);
        mMainShowBottomIv = findViewById(R.id.main_show_bottom_iv);
        mMainShowTopIv = findViewById(R.id.main_show_top_iv);
        mFrameLayout = findViewById(R.id.main_fl);
        initFragment();
        mHideControl = new HideControl();
    }

    @Override
    protected void destroyData() {
        mHideControl.endHideTimer();
    }

    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if (mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.main_fl, fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    // 监听返回键，点击两次退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                ToastUtils.showShort(com.wd.health.comment.R.string.base_activity_exit);
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtils.finishAllActivities();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏

        //for new api versions.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    public class HideControl {
        public final static int MSG_HIDE = 0x01;

        private HideHandler mHideHandler;

        public HideControl() {
            mHideHandler = new HideHandler();
        }

        public class HideHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_HIDE:
                        mMainPatientsIv.setVisibility(View.GONE);
                        mMainRg.setVisibility(View.GONE);
                        break;
                }

            }
        }

        private Runnable hideRunable = new Runnable() {

            @Override
            public void run() {
                mHideHandler.obtainMessage(MSG_HIDE).sendToTarget();
            }
        };

        public void startHideTimer() {//开始计时,三秒后执行runable
            mHideHandler.removeCallbacks(hideRunable);
            if (mMainPatientsIv.getVisibility() == View.GONE) {
                mMainPatientsIv.setVisibility(View.VISIBLE);
            }
            if (mMainRg.getVisibility() == View.GONE) {
                mMainRg.setVisibility(View.VISIBLE);
            }
            mHideHandler.postDelayed(hideRunable, 6000);
        }

        public void endHideTimer() {//移除runable,将不再计时
            mHideHandler.removeCallbacks(hideRunable);
        }

        public void resetHideTimer() {//重置计时
            mHideHandler.removeCallbacks(hideRunable);
            mHideHandler.postDelayed(hideRunable, 6000);
        }

    }

}
