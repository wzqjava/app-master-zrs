package com.wd.health.home.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.health.config.ConfigApp;
import com.wd.health.home.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/27 14:21
 */
public class WechatShareManager {

    private final IWXAPI wxapi;
    private Context context;

    public WechatShareManager(Context context){
        wxapi = ConfigApp.mIwxapi;
        this.context=context;
    }

    /**
     * 判断是否安装微信
     */
    public boolean isWeiXinAppInstall() {
        if (wxapi.isWXAppInstalled()) {
            return true;
        } else {
            Toast.makeText(context,"请安装微信", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 分享文本类型
     *
     * @param text 文本内容
     * @param type 微信会话或者朋友圈等
     */
    public void shareTextToWx(String text, int type) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

       //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
        req.message = msg;
        req.scene = type;

       //调用api接口，发送数据到微信
        wxapi.sendReq(req);
    }

    /**
     * 分享网页类型
     *
     * @param text 文本内容
     * @param type 微信会话或者朋友圈等
     */
    public void shareUrlToWx(String url,int type,String title,String content){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl =url;

       //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title =title;
        msg.description =content;
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy);
        msg.thumbData =bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message =msg;
        req.scene =type;

      //调用api接口，发送数据到微信
        wxapi.sendReq(req);
    }

    //获取网页上的图片
    private InputStream getImageStream(String url) throws IOException{
        URL tmpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) tmpUrl.openConnection();
        InputStream inputStream = conn.getInputStream();
        return inputStream;
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 10 && options != 10) {
            output.reset(); //清空output
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        if (needRecycle) {
            bmp.recycle();
        }
        return output.toByteArray();

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}



