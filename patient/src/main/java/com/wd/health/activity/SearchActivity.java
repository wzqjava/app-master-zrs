package com.wd.health.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.adapter.SearchAdapter;
import com.wd.health.base.BaseActivity;
import com.wd.health.core.exception.ApiException;
import com.wd.health.model.DataCall;
import com.wd.health.model.bean.SearchBean;
import com.wd.health.patient.PatientsFragment;
import com.wd.health.patient.R;
import com.wd.health.presenter.SearchPresenter;

import java.util.List;

public class SearchActivity extends BaseActivity {
    private ImageView search_back;
    private EditText search_edit;
    private TextView search_query;
    private RecyclerView search_recycler;
    private SearchPresenter searchPresenter;
    private SearchAdapter searchAdapter;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        searchPresenter = new SearchPresenter(new search());
        search_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search_edit.getText().toString();
                searchPresenter.reqeust(s);
            }
        });
        searchAdapter = new SearchAdapter(this);
        search_recycler.setAdapter(searchAdapter);


    }

    class search implements DataCall<List<SearchBean>>{
        @Override
        public void onSuccess(List<SearchBean> result, String message) {
            searchAdapter.clear();
            searchAdapter.addAll(result);
            searchAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    @Override
    protected void initView() {
        search_back = findViewById(R.id.search_back);
        search_edit = findViewById(R.id.search_edit);
        search_query = findViewById(R.id.search_query);
        search_recycler = findViewById(R.id.search_recycler);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        search_recycler.setLayoutManager(linearLayoutManager);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }

    @Override
    protected void destroyData() {

    }
}
