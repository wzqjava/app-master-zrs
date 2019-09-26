package com.wd.health.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.adapter.CommentAdapter;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.model.DataCall;
import com.wd.health.model.Result;
import com.wd.health.model.bean.CommentBean;
import com.wd.health.model.bean.DetailsBean;
import com.wd.health.patient.R;
import com.wd.health.presenter.CommentPresenter;
import com.wd.health.presenter.DetailsPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DetailsActivity extends BaseActivity {
    private TextView d_title;
    private TextView d_treatmentProcess;
    private TextView d_treatmentStartTime;
    private TextView d_department;
    private TextView d_detail;
    private ImageView d_picture;
    private TextView d_collectionNum;
    private TextView d_commentNum;
    private TextView d_disease;
    private TextView details_name;
    private SimpleDraweeView d_adoptHeadPic;
    private TextView d_adoptNickName;
    private TextView d_adoptTime;
    private TextView d_adoptComment;

    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private ViewStub mView_Stub;//第一个ViewStub
    private ViewStub mView_Stub2;//第二个ViewStub
    private boolean COME_ONE = false;
    private boolean isViewStub;

    private ImageView sick_sofa, sick_none_comment;

    private ImageView img1;
    private ImageView sickDetailsMessage;
    private String id;
    private RecyclerView mRecy;

    private CommentAdapter commentAdapter;
    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_details;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        details_name.setText(name);
        id = intent.getStringExtra("id");
        DetailsPresenter detailsPresenter = new DetailsPresenter(new details());
        detailsPresenter.reqeust(id, "149", "1565231885371149");

        initListener();
    }

    class details implements DataCall<DetailsBean> {

        @Override
        public void onSuccess(DetailsBean result, String message) {
            d_title.setText(result.getTitle());
            d_collectionNum.setText(String.valueOf(result.getCollectionNum()));
            d_commentNum.setText(String.valueOf(result.getCommentNum()));
            d_department.setText(String.valueOf(result.getDepartment()));
            d_detail.setText(String.valueOf(result.getDetail()));
            d_disease.setText(String.valueOf(result.getDisease()));
            Uri uri = Uri.parse(String.valueOf(result.getPicture()));
            d_picture.setImageURI(uri);
            d_treatmentProcess.setText(String.valueOf(result.getTreatmentProcess()));
            long time = result.getTreatmentEndTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            String day = simpleDateFormat.format(new Date(time));
            d_treatmentStartTime.setText(day);
            Uri uri1 = Uri.parse(String.valueOf(result.getAdoptHeadPic()));
            d_adoptHeadPic.setImageURI(uri1);
            d_adoptNickName.setText(String.valueOf(result.getAdoptNickName()));
            long time1 = result.getTreatmentEndTime();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            String format = simpleDateFormat1.format(new Date(time1));
            d_adoptTime.setText(format);
            d_adoptComment.setText(String.valueOf(result.getAdoptComment()));

        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {

        details_name = findViewById(R.id.details_name);
        d_title = findViewById(R.id.d_title);
        d_detail = findViewById(R.id.d_detail);
        d_collectionNum = findViewById(R.id.d_collectionNum);
        d_commentNum = findViewById(R.id.d_commentNum);
        d_department = findViewById(R.id.d_department);
        d_picture = findViewById(R.id.d_picture);
        d_disease = findViewById(R.id.d_disease);
        d_treatmentProcess = findViewById(R.id.d_treatmentProcess);
        d_treatmentStartTime = findViewById(R.id.d_treatmentStartTime);
        d_adoptTime = findViewById(R.id.d_adoptTime);
        d_adoptHeadPic = findViewById(R.id.d_adoptHeadPic);
        d_adoptNickName = findViewById(R.id.d_adoptNickName);
        d_adoptComment = findViewById(R.id.d_adoptComment);


        img1 = findViewById(R.id.d_img1);
        sickDetailsMessage = (ImageView) findViewById(R.id.d_img2);
        mView_Stub = (ViewStub) findViewById(R.id.view_Stub);
        mView_Stub2 = (ViewStub) findViewById(R.id.sick_details_alert);

    }


    class comment implements DataCall<List<CommentBean>>{

        @Override
        public void onSuccess(List<CommentBean> result, String message) {
            if (result.size()==0){
                sick_sofa.setVisibility(View.VISIBLE);
            }else {
                commentAdapter = new CommentAdapter(DetailsActivity.this);
                commentAdapter.addAll(result);
                mRecy.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    public void initListener() {
        sickDetailsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentPresenter commentPresenter = new CommentPresenter(new comment());
                commentPresenter.reqeust(id,"149","1565231885371149");
                final LinearLayout alertwindow = (LinearLayout) mView_Stub2.inflate();
                alertwindow.setVisibility(View.VISIBLE);
                 sick_sofa = alertwindow.findViewById(R.id.sick_sofa);
                    sick_none_comment = alertwindow.findViewById(R.id.sick_none_comment);
                    mRecy = alertwindow.findViewById(R.id.sick_comment_recycler);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
                    mRecy.setLayoutManager(linearLayoutManager);
                    findViewById(R.id.sick_comment_close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertwindow.setVisibility(View.GONE);

                        }
                    });
            }
        });
        mView_Stub2.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                isViewStub = true;
            }
        });


        //viewStub
        preferences = getSharedPreferences("yindaoye", MODE_PRIVATE);
        edit = preferences.edit();
        boolean yindao = preferences.getBoolean("yindao", false);

      /*  if (yindao){
            mView_Stub.setVisibility(View.GONE);
            COME_ONE = true;
        }
        if (COME_ONE == false){
            View view = mView_Stub.inflate();
            view.setVisibility(View.VISIBLE);
            view.findViewById(R.id.i1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView_Stub.setVisibility(View.GONE);
                    edit.putBoolean("yindao",true);
                    edit.commit();
                }
            });
        }*/

    }

    @Override
    protected void destroyData() {

    }
}
