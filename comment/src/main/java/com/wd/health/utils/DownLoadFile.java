package com.wd.health.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created :  LiZhIX
 * Date :  2019/8/5 13:57
 * Description  :   文件断点续传下载
 */
public class DownLoadFile {

    private static final String SP_NAME = "download_file";
    private static final String CURR_LENGTH = "curr_length";    //已经下载的长度
    private DownListener mDownListener;                         //接口回调
    private String downPath;                                    //下载路径
    private String mFilePath;                                   //文件保存本地的路径
    private final int mThreadCount = 3;                         //规定开启的线程数
    private volatile int runningThreadCount;                    //正在运行的线程数
    private volatile int mCurrentLength;                        //已经下载的长度
    private final String DOWN_INIT = "1";                       //定义下载的状态
    private final String DOWN_START = "2";
    private final String DOWN_PAUSE = "3";
    private String stateDownload = DOWN_INIT;                   //当前线程状态
    private int mFileLength;                                    //文件的总长度
    private Thread[] mThread;                                   //线程数组
    private Context mContext;

    public DownLoadFile(String path, String url, Context context) {
        this.mFilePath = path;
        this.downPath = url;
        this.mContext = context;
        runningThreadCount = 0;
    }

    public void setmDownListener(DownListener mDownListener) {
        this.mDownListener = mDownListener;
    }

    public interface DownListener {
        void onProgress(int progress);

        void onSuccess(String success);

        void onError(String error);
    }

    public void downLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //判断线程数组是否存在
                if (mThread == null)
                    mThread = new Thread[mThreadCount];
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(mFilePath, "rwd");
                    URL url = new URL(downPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection.getResponseCode() == 200) {
                        mFileLength = connection.getContentLength();
                        randomAccessFile.setLength(mFileLength);
                        randomAccessFile.close();
                        //每个线程下载的分块
                        int threadIndex = mFileLength / mThreadCount;
                        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                        //这个参数没有其他功能只是用来记录进度条的进度
                        mCurrentLength = sp.getInt(CURR_LENGTH, 0);
                        for (int i = 0; i < mThread.length; i++) {
                            //每个线程开始下载的标记
                            int start = sp.getInt(SP_NAME + (i + 1), i * threadIndex);
                            //每个线程结束下载的标记
                            int end = (i + 1) * threadIndex - 1;
                            //将最后一个线程结束位置扩大，防止文件下载不完全，大了不影响，小了文件失效
                            if (i + 1 == mThreadCount) {
                                end = end * 2;
                            }
                            mThread[i] = new DownThread(start, end, i + 1);
                            mThread[i].start();
                        }
                    } else {
                        handler.sendEmptyMessage(FAILURE);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAILURE);
                }
            }
        }).start();

    }

    class DownThread extends Thread {
        private boolean isGoOn = true;  //判断是否下载
        private int endIndex;           //结束节点
        private int currentIndex;       //已经下载到的节点
        private int startIndex;         //开始节点
        private int threadId;           //线程ID

        public DownThread(int startIndex, int endIndex, int threadId) {
            this.endIndex = endIndex;
            this.currentIndex = startIndex;
            this.startIndex = startIndex;
            this.threadId = threadId;
            runningThreadCount++;
        }

        //取消下载
        public void cancle() {
            isGoOn = false;
        }

        @Override
        public void run() {
            try {
                SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                RandomAccessFile randomAccessFile = new RandomAccessFile(mFilePath, "rwd");
                URL url = new URL(downPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                if (connection.getResponseCode() == 206) {
                    if (!isGoOn) {
                        return;
                    }
                    randomAccessFile.seek(startIndex);
                    int leng = 0;
                    byte[] bytes = new byte[1024];
                    while ((leng = connection.getInputStream().read(bytes)) != -1) {
                        if (mDownListener != null) {
                            mCurrentLength += leng;
                            int progress = (int) ((float) mCurrentLength / (float) mFileLength * 100);
                            handler.sendEmptyMessage(progress);
                        }
                        randomAccessFile.write(bytes, 0, leng);
                        //记载指针指到最后那个位置
                        currentIndex += leng;
                        synchronized (DOWN_PAUSE) {
                            if (stateDownload.equals(DOWN_PAUSE)) {
                                DOWN_PAUSE.wait();
                            }
                        }
                    }
                    randomAccessFile.close();
                    runningThreadCount--;
                    if (!isGoOn) {
                        if (currentIndex < endIndex) {
                            sp.edit().putInt(SP_NAME + threadId, mCurrentLength).apply();
                            sp.edit().putInt(CURR_LENGTH, currentIndex).apply();
                        }
                        return;
                    }
                    if (runningThreadCount == 0) {
                        sp.edit().clear().apply();
                        handler.sendEmptyMessage(SUCCESS);
                        handler.sendEmptyMessage(100);
                        mThread = null;
                    }
                } else {//如果不是206下发失败
                    sp.edit().clear().apply();
                    handler.sendEmptyMessage(FAILURE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(FAILURE);
            }
        }
    }

    private final int SUCCESS = 0x00000101;
    private final int FAILURE = 0x00000102;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (mDownListener != null) {
                if (msg.what == SUCCESS) {
                    mDownListener.onSuccess("下载成功");
                } else if (msg.what == FAILURE) {
                    mDownListener.onError("下载失败");
                } else {
                    mDownListener.onProgress(msg.what);
                }
            }
        }
    };

    //暂停
    public void pause() {
        if (mThread != null) {//如果不等于null 说明线程在跑 run方法在执行
            stateDownload = DOWN_PAUSE;
        }
    }

    //继续下载
    public void start() {
        if (mThread != null)
            synchronized (DOWN_PAUSE) {
                stateDownload = DOWN_START;
                DOWN_PAUSE.notifyAll();
            }
    }

    //这个activity退出要销毁线程
    public void onDestroy() {
        if (mThread != null)
            mThread = null;
    }
}
