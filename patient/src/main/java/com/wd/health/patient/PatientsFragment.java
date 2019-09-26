package com.wd.health.patient;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.health.activity.SearchActivity;
import com.wd.health.adapter.CircleAdapter;
import com.wd.health.adapter.KeShiAdapter;
import com.wd.health.base.BaseFragment;
import com.wd.health.core.exception.ApiException;
import com.wd.health.model.DataCall;
import com.wd.health.model.bean.CircleBean;
import com.wd.health.model.bean.KeShiBean;
import com.wd.health.presenter.CirclePresenter;
import com.wd.health.presenter.KeShiPresenter;

import java.util.List;


/**
 * Created :  LiZhIX
 * Date :  2019/8/5 14:56
 * Description  :  病友圈页面
 */
public class PatientsFragment extends BaseFragment {
    private RecyclerView keshi_recycler;
    private RecyclerView patient_recycler;
    private KeShiAdapter keShiAdapter;
    private CircleAdapter circleAdapter;

    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View mToolbar1;
    private View mToolbar2;

    private ImageView imageView1, imageView2, imageView3, imageView4;
    private EditText edit_query;
    private TextView ke_text;

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected void setViewData(View view) {
        KeShiPresenter keShiPresenter = new KeShiPresenter(new keshi());
        keShiPresenter.reqeust();
        keShiAdapter = new KeShiAdapter(getActivity());
        keshi_recycler.setAdapter(keShiAdapter);

        final CirclePresenter circlePresenter = new CirclePresenter(new circle());
        circlePresenter.reqeust(2);
        circleAdapter = new CircleAdapter(getActivity());
        patient_recycler.setAdapter(circleAdapter);

        keShiAdapter.setOnCallback(new KeShiAdapter.OnCallback() {
            @Override
            public void getData(int id, String i) {
                circlePresenter.reqeust(id);
                circleAdapter.notifyDataSetChanged();
            }
        });
    }


    class circle implements DataCall<List<CircleBean>> {

        @Override
        public void onSuccess(List<CircleBean> result, String message) {
            circleAdapter.clear();
            circleAdapter.addAll(result);
            circleAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    class keshi implements DataCall<List<KeShiBean>> {
        @Override
        public void onSuccess(List<KeShiBean> result, String message) {
            keShiAdapter.addAll(result);
            keShiAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @Override
    protected void initView(View view) {
        keshi_recycler = view.findViewById(R.id.keshi_recycler);
        patient_recycler = view.findViewById(R.id.circle_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        keshi_recycler.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        patient_recycler.setLayoutManager(linearLayoutManager1);

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorAppBlue));

        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
        mToolbar1 = (View) view.findViewById(R.id.toolbar1);
        mToolbar2 = (View) view.findViewById(R.id.toolbar2);

        edit_query = view.findViewById(R.id.edit_query);
        ke_text = view.findViewById(R.id.ke_text);
        //跳转搜索页面
        edit_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });

        imageView1 = view.findViewById(R.id.img_01);
        imageView2 = view.findViewById(R.id.img_02);
        imageView3 = view.findViewById(R.id.img_03);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //张开
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //收缩
                    mToolbar1.setVisibility(View.GONE);
                    mToolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                } else {
                    int alpha = 255 - Math.abs(verticalOffset);
                    if (alpha < 0) {
                        Log.e("alpha", alpha + "");
                        //收缩toolbar
                        mToolbar1.setVisibility(View.GONE);
                        mToolbar2.setVisibility(View.VISIBLE);
                        setToolbar2Alpha(Math.abs(verticalOffset));
                    } else {
                        //张开toolbar
                        mToolbar1.setVisibility(View.VISIBLE);
                        mToolbar2.setVisibility(View.GONE);
                        setToolbar1Alpha(alpha);
                    }
                }
            }
        });
    }

    //设置展开时各控件的透明度
    public void setToolbar1Alpha(int alpha) {
        imageView1.getDrawable().setAlpha(alpha);
        imageView2.getDrawable().setAlpha(alpha);

    }

    //设置闭合时各控件的透明度
    public void setToolbar2Alpha(int alpha) {
        imageView3.getDrawable().setAlpha(alpha);
    }

    @Override
    public int setLayoutView() {
        return R.layout.fragment_patients;
    }
}
