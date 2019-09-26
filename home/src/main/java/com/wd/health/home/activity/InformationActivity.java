package com.wd.health.home.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.wd.health.home.R;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.customview.RewritePopwindow;
import com.wd.health.home.httpbean.InformationXqBean;
import com.wd.health.home.httpbean.NoticeReadNumBean;
import com.wd.health.home.url.MyUrl;
import com.wd.health.home.util.TimeUtil;
import com.wd.health.home.util.WechatShareManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/19 19:20
 */
public class InformationActivity extends MyBaseActivity implements View.OnClickListener {


    private TextView titles,zname,times;
    private WebView web;
    private ImageView detailCollectImg,detailShareImg;

    private RewritePopwindow mPopwindow;
    private WechatShareManager wechatShareManager;
    private InformationXqBean.ResultBean result;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_information;
    }

    @Override
    protected void initData() {

        wechatShareManager = new WechatShareManager(getApplicationContext());

        Intent intent = getIntent();
        String infoId = intent.getStringExtra("infoId");
        // Toast.makeText(this, infoId, Toast.LENGTH_SHORT).show();
        Log.e("TAG", infoId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("infoId", infoId);
        prsenter.getMethod(MyUrl.InformationXq, map, InformationXqBean.class);
    }

    @Override
    protected void initView() {
        titles = (TextView) findViewById(R.id.titles);
        zname = (TextView) findViewById(R.id.zname);
        times = (TextView) findViewById(R.id.times);
        web = (WebView) findViewById(R.id.web);
        detailCollectImg = (ImageView) findViewById(R.id.detail_collect_img);
        detailShareImg = (ImageView) findViewById(R.id.detail_share_img);

        detailCollectImg.setOnClickListener(this);
        detailShareImg.setOnClickListener(this);
    }

    @Override
    public void setData(Object o) {
        if (o instanceof InformationXqBean) {
            InformationXqBean xqingBean = (InformationXqBean) o;
            result = xqingBean.getResult();
            titles.setText(result.getTitle());
            String timeAgo = TimeUtils.millis2String(result.getReleaseTime());
            times.setText(timeAgo);
            zname.setText(result.getSource());
            //解析webview中的h5代码
            Log.e("TAG", result.getContent());

            web.getSettings().setDefaultTextEncodingName("UTF -8");
            String content = result.getContent().replace("<img", "<img style=max-width:100%;height:auto");

            web.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
        }
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            mPopwindow.dismiss();
            mPopwindow.backgroundAlpha(InformationActivity.this, 1f);
            int i = v.getId();
            if (i == R.id.weixinghaoyou) {
                Toast.makeText(InformationActivity.this, "微信好友", Toast.LENGTH_SHORT).show();
                wechatShareManager.shareUrlToWx("https://www.jianshu.com/p/af8ba09571a7",SendMessageToWX.Req.WXSceneSession,"维度健康","王康康是沙雕");
            } else if (i == R.id.pengyouquan) {
                wechatShareManager.shareUrlToWx("https://www.jianshu.com/p/af8ba09571a7",SendMessageToWX.Req.WXSceneTimeline ,"维度健康","王康康是沙雕");
               Toast.makeText(InformationActivity.this, "朋友圈", Toast.LENGTH_SHORT).show();
            } else if (i == R.id.qqhaoyou) {
                Toast.makeText(InformationActivity.this, "QQ好友", Toast.LENGTH_SHORT).show();

            } else if (i == R.id.qqkongjian) {
                Toast.makeText(InformationActivity.this, "QQ空间", Toast.LENGTH_SHORT).show();

            } else {

            }
        }
    };

    @Override
    protected void destroyData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.detail_collect_img) {
        } else if (id == R.id.detail_share_img) {
            mPopwindow = new RewritePopwindow(InformationActivity.this, itemsOnClick);
            mPopwindow.showAtLocation(v,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }
}
