package com.wd.health.home.util;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

/**
 * @author 荣生
 * @description:录音工具类
 * @date :2019/8/10 8:56
 */
public class MyMediaRecorder {

    private static MyMediaRecorder instance;

    private MediaRecorder mMediaRecorder;

    private String filePath;

    private MyMediaRecorder() {

    }

    //双重锁单例模式
    public static synchronized MyMediaRecorder getInstance() {
        if (null == instance) {
            synchronized (MyMediaRecorder.class) {
                if (null == instance) {
                    instance = new MyMediaRecorder();
                }
            }
        }
        return instance;
    }

    /**
     * 开始录音
     *
     * @param path
     */
    public void startRecord(String path) {
        if (null == mMediaRecorder) {
            mMediaRecorder = new MediaRecorder();
        }
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            filePath = path;
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束录音
     */
    public void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            filePath = "";
        } catch (RuntimeException e) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            filePath = "";
        }
    }

}
