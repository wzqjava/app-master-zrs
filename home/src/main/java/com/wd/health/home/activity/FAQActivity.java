package com.wd.health.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.Constant;
import com.wd.health.home.R;
import com.wd.health.home.adapter.FAQDiseaseAdapter;
import com.wd.health.home.adapter.FAQLeftAdapter;
import com.wd.health.home.adapter.PlateAdapter;
import com.wd.health.home.adapter.YaoPinErAdapter;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.bean.FAQLeftBean;
import com.wd.health.home.httpbean.DepartmentBean;
import com.wd.health.home.httpbean.DiseaseCategoryBean;
import com.wd.health.home.httpbean.DrugsCategoryBean;
import com.wd.health.home.httpbean.YaoErBean;
import com.wd.health.home.url.MyUrl;
import com.wd.health.view.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/8 9:29
 */
@Route(path = Constant.ACTIVITY_URL_FAQ)
public class FAQActivity extends MyBaseActivity {
    private TabLayout tab;
    private RecyclerView faq_left,faq_disease;
    private int flag;
    private List<FAQLeftBean> left_list;
    private FAQLeftAdapter leftAdapter;
    List<DiseaseCategoryBean.ResultBean> disease_list;
    List<YaoErBean.ResultBean> drug_list;
    private XRecyclerView faq_drug;
    private FAQDiseaseAdapter faqDiseaseAdapter;
    private YaoPinErAdapter yaoPinErAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_faq;
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        flag = intent.getIntExtra("select", 0);

        tab.addTab(tab.newTab().setText("常见病症"));
        tab.addTab(tab.newTab().setText("常见药品"));

        left_list = new ArrayList<>();
        leftAdapter = new FAQLeftAdapter(this, left_list);
        faq_left.setAdapter(leftAdapter);

        tab.getTabAt(flag).select();

        disease_list = new ArrayList<>();
        faqDiseaseAdapter = new FAQDiseaseAdapter(this, disease_list);
        faq_disease.setAdapter(faqDiseaseAdapter);

        drug_list = new ArrayList<>();
        yaoPinErAdapter = new YaoPinErAdapter(drug_list, this);
        faq_drug.setAdapter(yaoPinErAdapter);

        initAdapterClick();

        if (flag == 0) {
            prsenter.getMethod(MyUrl.DEPARTMENT_SHOW, DepartmentBean.class);
        } else {
            prsenter.getMethod(MyUrl.DrugsCategory, DrugsCategoryBean.class);
        }

    }

    private void initAdapterClick() {

        leftAdapter.setLeftChange(new PlateAdapter.PlateBack() {
            @Override
            public void changed(int i) {
                if (flag == 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("departmentId", left_list.get(i).getId());
                    prsenter.getMethod(MyUrl.DiseaseCategory, map, DiseaseCategoryBean.class);
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("drugsCategoryId", left_list.get(i).getId());
                    map.put("page", "1");
                    map.put("count", "30");
                    prsenter.getMethod(MyUrl.DrugsKnowledgeList, map, YaoErBean.class);
                }
                leftAdapter.notifyDataSetChanged();
            }
        });


        faqDiseaseAdapter.getDiseaseBack(new FAQDiseaseAdapter.DiseaseBack() {
            @Override
            public void play(int i, String s) {
                Intent intent1 = new Intent(FAQActivity.this, BingZenXqActivity.class);
                intent1.putExtra("flag",1);
                intent1.putExtra("id",i);
                intent1.putExtra("names",s);
                startActivity(intent1);
            }
        });


        yaoPinErAdapter.getYaoPinBack(new YaoPinErAdapter.YaoPinBack() {
            @Override
            public void play(int i, String s) {
                Intent intent1 = new Intent(FAQActivity.this, BingZenXqActivity.class);
                intent1.putExtra("flag",2);
                intent1.putExtra("id",i);
                intent1.putExtra("names",s);
                startActivity(intent1);
            }
        });


        tab.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    prsenter.getMethod(MyUrl.DEPARTMENT_SHOW, DepartmentBean.class);
                    flag = 0;
                } else {
                    prsenter.getMethod(MyUrl.DrugsCategory, DrugsCategoryBean.class);
                    flag = 1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void initView() {
        tab = findViewById(R.id.faq_tab);
        faq_left = findViewById(R.id.faq_left);
        faq_disease = findViewById(R.id.faq_disease);
        faq_drug = (XRecyclerView) findViewById(R.id.faq_drug);

        faq_left.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager disease_manager = new GridLayoutManager(this, 2);
        faq_disease.setLayoutManager(disease_manager);

        GridLayoutManager drug_manager = new GridLayoutManager(this, 3);
        faq_drug.setLayoutManager(drug_manager);

    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void setData(Object data) {

        if (data instanceof DepartmentBean) {
            setDepartmentData(data);
        } else if (data instanceof DrugsCategoryBean) {
            setDrugsCategoryData(data);
        } else if (data instanceof DiseaseCategoryBean) {
            setDiseaseCategoryData(data);
        } else if (data instanceof YaoErBean) {
            setYaoErData(data);
        }

    }

    private void setYaoErData(Object data) {
        drug_list.clear();
        faq_disease.setVisibility(View.GONE);
        faq_drug.setVisibility(View.VISIBLE);
        List<YaoErBean.ResultBean> result = ((YaoErBean) data).getResult();
        drug_list.addAll(result);
        yaoPinErAdapter.notifyDataSetChanged();
    }

    private void setDiseaseCategoryData(Object data) {
        faq_disease.setVisibility(View.VISIBLE);
        faq_drug.setVisibility(View.GONE);
        disease_list.clear();
        List<DiseaseCategoryBean.ResultBean> result = ((DiseaseCategoryBean) data).getResult();
        disease_list.addAll(result);
        faqDiseaseAdapter.notifyDataSetChanged();
    }

    private void setDrugsCategoryData(Object data) {
        left_list.clear();
        List<DrugsCategoryBean.ResultBean> result = ((DrugsCategoryBean) data).getResult();
        for (int i = 0; i < result.size(); i++) {
            left_list.add(new FAQLeftBean(result.get(i).getName(), result.get(i).getId()));
        }
        leftAdapter.notifyDataSetChanged();

        Map<String, Object> map = new HashMap<>();
        map.put("drugsCategoryId", left_list.get(0).getId());
        map.put("page", "1");
        map.put("count", "30");
        prsenter.getMethod(MyUrl.DrugsKnowledgeList, map, YaoErBean.class);
    }

    private void setDepartmentData(Object data) {
        left_list.clear();
        List<DepartmentBean.ResultBean> result = ((DepartmentBean) data).getResult();
        for (int i = 0; i < result.size(); i++) {
            left_list.add(new FAQLeftBean(result.get(i).getDepartmentName(), result.get(i).getId()));
        }
        leftAdapter.notifyDataSetChanged();
        Map<String, Object> map = new HashMap<>();
        map.put("departmentId", left_list.get(0).getId());
        prsenter.getMethod(MyUrl.DiseaseCategory, map, DiseaseCategoryBean.class);
    }

}
