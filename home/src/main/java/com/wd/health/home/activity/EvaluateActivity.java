package com.wd.health.home.activity;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.health.home.R;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.httpbean.EvaluationBean;
import com.wd.health.home.url.MyUrl;

import java.util.HashMap;

/**
 * @author 荣生
 * @description:问诊服务评价页面
 * * @date :2019/8/28 14:15
 */
public class EvaluateActivity extends MyBaseActivity {
    private android.widget.EditText edtext;
    private android.widget.TextView shb;
    private android.widget.TextView pjtext;
    private android.view.View views;
    private android.view.View views1;
    private android.widget.TextView xingji;
    private android.widget.TextView drocterzyd;
    private android.widget.RatingBar ratingBar1;
    private android.widget.ImageView xx;
    private android.widget.TextView manyidu;
    private android.widget.RatingBar ratingBar2;
    private android.widget.ImageView xxx;
    private android.widget.Button fh;
    private android.widget.Button wc;

    int inquiryRecordId;
    int doctorId;
    int result;
    float rating;
    float step;
    int b,b2 ;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        inquiryRecordId = intent.getIntExtra("inquiryRecordId", 0);
        doctorId = intent.getIntExtra("doctorId", 0);

        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("请输入你对医生的评价");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        edtext.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    @Override
    protected void initView() {

        edtext = (EditText) findViewById(R.id.edtext);
        shb = (TextView) findViewById(R.id.shb);
        pjtext = (TextView) findViewById(R.id.pjtext);
        views = (View) findViewById(R.id.views);
        views1 = (View) findViewById(R.id.views1);
        xingji = (TextView) findViewById(R.id.xingji);
        drocterzyd = (TextView) findViewById(R.id.drocterzyd);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
        xx = (ImageView) findViewById(R.id.xx);
        manyidu = (TextView) findViewById(R.id.manyidu);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        xxx = (ImageView) findViewById(R.id.xxx);
        fh = (Button) findViewById(R.id.fh);
        wc = (Button) findViewById(R.id.wc);

        initlisenter();

    }

    public void initlisenter() {


        //第er个星级评价监听
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                b2 = (int)rating;//用(int)强制转换为整型b2
                //第二个星级评价设置表情
                if(b2>0&&b2<=2){
                    xxx.setVisibility(View.VISIBLE);
                    xxx.setImageResource(R.drawable.evaluation_icon_angry_n);
                }else if(b2>2&&b2<=4) {
                    xxx.setVisibility(View.VISIBLE);
                    xxx.setImageResource(R.drawable.evaluation_icon_smile_n);
                }else if (b2==5){
                    xxx.setVisibility(View.VISIBLE);
                    xxx.setImageResource(R.drawable.evaluation_icon_laugh_n);
                }else {
                    xxx.setVisibility(View.GONE);
                }
            }
        });

        //第一个星级评价监听
        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                b = (int)rating;//用(int)强制转换为整型b
                //第一个星级评价设置表情
                if(b>0&&b<=2){
                    xx.setVisibility(View.VISIBLE);
                    xx.setImageResource(R.drawable.evaluation_icon_angry_n);
                }else if(b>2&&b<=4) {
                    xx.setVisibility(View.VISIBLE);
                    xx.setImageResource(R.drawable.evaluation_icon_smile_n);
                }else if (b==5){
                    xx.setVisibility(View.VISIBLE);
                    xx.setImageResource(R.drawable.evaluation_icon_laugh_n);
                }else {
                    xx.setVisibility(View.GONE);
                }

            }
        });


        // 点击完成发送评论
        wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("星级评分条","step="+step+"result="+result+"rating="+rating );
                Toast.makeText(EvaluateActivity.this,"你得到了"+rating+"颗星",Toast.LENGTH_SHORT).show();
                HashMap<String, Object> map = new HashMap<>();
                map.put("inquiryRecordId",inquiryRecordId);
                map.put("doctorId",doctorId);
                map.put("evaluate",edtext.getText());//内容
                map.put("majorDegree",b);//1
                map.put("satisfactionDegree",b2);//2
                prsenter.putMethod(MyUrl.evaluationInquiry,map, EvaluationBean.class);
            }
        });
        //点击返回关闭页面
        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setData(Object data) {
        if(data instanceof EvaluationBean){
            EvaluationBean endWzs=(EvaluationBean)data;
            Toast.makeText(this, endWzs.getMessage(), Toast.LENGTH_SHORT).show();
            if (endWzs.getMessage().equals("评价成功")){
                finish();
            }

        }
    }

    @Override
    protected void destroyData() {

    }
}
