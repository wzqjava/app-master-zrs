package com.wd.health.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.util.HashMap;

/**
 * Created :  LiZhIX
 * Date :  2019/8/15 15:30
 * Description  :   图片处理类
 */
public class ImageUtils {

    //获得第一帧图片
    public static Bitmap getNetVideoBitmap(String videoUrl) {

        /*String pathvideo = "你的网络视频路径";
        //加载视频第一帧                                   使用方法
        Bitmap bitmap = getNetVideoBitmap(pathvideo);
        //对应的ImageView赋值图片
        holder.img.setImageBitmap(bitmap);*/


        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
