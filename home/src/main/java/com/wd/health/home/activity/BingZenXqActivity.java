package com.wd.health.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.health.home.R;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.httpbean.BingZengXqBean;
import com.wd.health.home.httpbean.DrugsBean;
import com.wd.health.home.url.MyUrl;

import java.util.HashMap;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/25 19:32
 */
public class BingZenXqActivity extends MyBaseActivity {

    private int id;
    // 传进来的flag值 1:为病症数据  2:为药品数据
    private int flag;
    private String names;
    private android.widget.LinearLayout drugsLayout,diseaseLayout;
    private android.widget.TextView drugsName,drugsComponent,drugsTaboo,drugsConsumption;
    private android.widget.TextView drugsCharacter,drugsFunction,drugsPacking,drugsReaction;
    private android.widget.TextView drugsStorage,drugsMatter,drugsSymbol,bl;
    private android.widget.TextView bztext,zz,yyj,xyzl,zyzl;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_bing_zen_xq;
    }

    @Override
    protected void initData() {
        // 接收传值
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        id = intent.getIntExtra("id", 0);
        names = intent.getStringExtra("names");
        HashMap<String, Object> map = new HashMap<>();
        // 1:为病症数据 2:为药品数据
        if (flag == 1) {
            diseaseLayout.setVisibility(View.VISIBLE);
            drugsLayout.setVisibility(View.GONE);
            bztext.setText(names);
            map.put("id", id);
            prsenter.getMethod(MyUrl.DiseaseKnowledge, map, BingZengXqBean.class);
        } else {
            drugsLayout.setVisibility(View.VISIBLE);
            diseaseLayout.setVisibility(View.GONE);
            drugsName.setText(names);
            map.put("id", id);
            prsenter.getMethod(MyUrl.DrugXq, map, DrugsBean.class);
        }
    }

    @Override
    protected void initView() {

        drugsLayout = (LinearLayout) findViewById(R.id.drugs_layout);
        drugsName = (TextView) findViewById(R.id.drugs_name);
        drugsComponent = (TextView) findViewById(R.id.drugs_component);
        drugsTaboo = (TextView) findViewById(R.id.drugs_taboo);
        drugsFunction = (TextView) findViewById(R.id.drugs_function);
        drugsConsumption = (TextView) findViewById(R.id.drugs_consumption);
        drugsCharacter = (TextView) findViewById(R.id.drugs_character);
        drugsPacking = (TextView) findViewById(R.id.drugs_packing);
        drugsReaction = (TextView) findViewById(R.id.drugs_reaction);
        drugsStorage = (TextView) findViewById(R.id.drugs_storage);
        drugsMatter = (TextView) findViewById(R.id.drugs_matter);
        drugsSymbol = (TextView) findViewById(R.id.drugs_symbol);
        diseaseLayout = (LinearLayout) findViewById(R.id.disease_layout);
        bztext = (TextView) findViewById(R.id.bztext);
        bl = (TextView) findViewById(R.id.bl);
        zz = (TextView) findViewById(R.id.zz);
        yyj = (TextView) findViewById(R.id.yyj);
        xyzl = (TextView) findViewById(R.id.xyzl);
        zyzl = (TextView) findViewById(R.id.zyzl);
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void setData(Object o) {
        // 病症详情
        if (o instanceof BingZengXqBean) {
            BingZengXqBean bingZengXqBean = (BingZengXqBean) o;
            BingZengXqBean.ResultBean result = bingZengXqBean.getResult();
            bl.setText(result.getPathology());
            zz.setText(result.getSymptom());
            yyj.setText(result.getBenefitTaboo());
            zyzl.setText(result.getChineseMedicineTreatment());
            xyzl.setText(result.getWesternMedicineTreatment());
        } else if (o instanceof DrugsBean) {
            // 药品详情
            DrugsBean drugsBean = (DrugsBean) o;
            DrugsBean.ResultBean result = drugsBean.getResult();
            drugsSymbol.setText(result.getApprovalNumber());
            drugsMatter.setText(result.getMindMatter());
            drugsTaboo.setText(result.getTaboo());
            drugsPacking.setText(result.getPacking());
            drugsComponent.setText(result.getComponent());
            drugsConsumption.setText(result.getUsage());
            drugsStorage.setText(result.getStorage());
            drugsCharacter.setText(result.getShape());
            drugsReaction.setText(result.getEffect());
        }
    }
}
