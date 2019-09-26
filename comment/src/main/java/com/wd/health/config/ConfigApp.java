package com.wd.health.config;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.health.core.db.DaoMaster;
import com.wd.health.core.db.DaoSession;
import com.wd.health.core.screen.ScreenAdapter;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created :  LiZhIX
 * Date :  2019/8/2 17:20
 * Description  :   App配置
 */
public class ConfigApp extends Application {

    private static ConfigApp sInstance;

    private Boolean isDebug = true;
    private static int MAX_MEM = 30 * ByteConstants.MB;
    //屏幕适配的基准
    private static final int MATCH_BASE_WIDTH = 0;
    private static final int MATCH_BASE_HEIGHT = 1;
    //适配单位
    private static final int MATCH_UNIT_DP = 0;
    private static final int MATCH_UNIT_PT = 1;
    //适配尺寸
    private static final float designSize = 360;
    //  context 全局唯一的上下文
    public static Context context;
    private DaoSession mDaoSession;

    //微信APPID
    private static final String APP_ID = "wxe3fcbe8a55cd33ff";

    public static String APP_KEY="b5f102cc307091e167ce52e0";
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI mIwxapi;

    @Override
    public void onCreate() {
        super.onCreate();

        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);


        sInstance = this;
        context = this;
        //初始化屏幕适配
        ScreenAdapter.register(this, designSize, MATCH_BASE_WIDTH, MATCH_UNIT_DP);
        //初始化Fresco
        Fresco.initialize(this, getConfigureCaches(this));
        //阿里路由初始化
        arouter();
        //数据库初始化
        initGreenDao();

        initPlayer();
        new Thread(new Runnable() {
            @Override
            public void run() {

                regToWx();
                //logutils设置
                initLogUtils();
                //初始化内存泄露检查工具
                initLeakCanary();
                //崩溃工具相关
                initCrash();
            }
        }).start();
    }

    private void initPlayer() {
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create(context))
                //使用MediaPlayer解码
//                .setPlayerFactory(AndroidMediaPlayerFactory.create(context))
                .build());
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    private void initLeakCanary() {
        if (isDebug()) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogUtils.e(crashInfo);
                AppUtils.relaunchApp();
            }
        });
    }

    private void initLogUtils() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(isDebug())// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(isDebug())// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(1)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList arrayList) {
                        return "LogUtils Formatter ArrayList { " + arrayList.toString() + " }";
                    }
                })
                .setFileWriter(null);
        LogUtils.i(config.toString());

    }

    private boolean isDebug() {
        if (isDebug == null) isDebug = AppUtils.isAppDebug();
        return isDebug;
    }

    public static Context getContext() {
        return context;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void arouter() {
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);//阿里路由初始化
    }

    private ImagePipelineConfig getConfigureCaches(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        return builder.build();
    }

    public static ConfigApp getInstance() {
        return sInstance;
    }

    private void regToWx() {

        mIwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        mIwxapi.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                mIwxapi.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }
}
