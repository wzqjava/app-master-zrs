package com.wd.health.login.guidepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.login.R;
import com.wd.health.login.guidepage.adapter.MyFragmentPagerAdapter;
import com.wd.health.login.guidepage.fragment.GuidePageFiveFragment;
import com.wd.health.login.guidepage.fragment.GuidePageFourFragment;
import com.wd.health.login.guidepage.fragment.GuidePageOneFragment;
import com.wd.health.login.guidepage.fragment.GuidePageThreeFragment;
import com.wd.health.login.guidepage.fragment.GuidePageTwoFragment;

import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_GUIDEPAGE)
public class GuidePageActivity extends BaseActivity {

    private ViewPager mGuideVp;
    private RadioButton mGuideRb1;
    private RadioButton mGuideRb2;
    private RadioButton mGuideRb3;
    private RadioButton mGuideRb4;
    private RadioButton mGuideRb5;
    private RadioGroup mGuideRg;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initData() {
        initFragment();
        mGuideVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mGuideRg.check(R.id.guide_rb1);
                        mGuideRb1.setBackgroundResource(R.drawable.shape_point);
                        mGuideRb2.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb3.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb4.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb5.setBackgroundResource(R.drawable.shape_point_n);
                        break;
                    case 1:
                        mGuideRg.check(R.id.guide_rb2);
                        mGuideRb2.setBackgroundResource(R.drawable.shape_point);
                        mGuideRb1.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb3.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb4.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb5.setBackgroundResource(R.drawable.shape_point_n);
                        break;
                    case 2:
                        mGuideRg.check(R.id.guide_rb3);
                        mGuideRb3.setBackgroundResource(R.drawable.shape_point);
                        mGuideRb2.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb1.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb4.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb5.setBackgroundResource(R.drawable.shape_point_n);
                        break;
                    case 3:
                        mGuideRg.check(R.id.guide_rb4);
                        mGuideRb4.setBackgroundResource(R.drawable.shape_point);
                        mGuideRb2.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb3.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb1.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb5.setBackgroundResource(R.drawable.shape_point_n);
                        break;
                    case 4:
                        mGuideRg.check(R.id.guide_rb5);
                        mGuideRb5.setBackgroundResource(R.drawable.shape_point);
                        mGuideRb2.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb3.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb4.setBackgroundResource(R.drawable.shape_point_n);
                        mGuideRb1.setBackgroundResource(R.drawable.shape_point_n);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        GuidePageOneFragment guidePageOneFragment = new GuidePageOneFragment();
        GuidePageTwoFragment guidePageTwoFragment = new GuidePageTwoFragment();
        GuidePageThreeFragment guidePageThreeFragment = new GuidePageThreeFragment();
        GuidePageFourFragment guidePageFourFragment = new GuidePageFourFragment();
        GuidePageFiveFragment guidePageFiveFragment = new GuidePageFiveFragment();
        mFragments.add(guidePageOneFragment);
        mFragments.add(guidePageTwoFragment);
        mFragments.add(guidePageThreeFragment);
        mFragments.add(guidePageFourFragment);
        mFragments.add(guidePageFiveFragment);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mGuideVp.setAdapter(adapter);
    }

    @Override
    protected void initView() {

        mGuideVp = (ViewPager) findViewById(R.id.guide_vp);
        mGuideRb1 = (RadioButton) findViewById(R.id.guide_rb1);
        mGuideRb2 = (RadioButton) findViewById(R.id.guide_rb2);
        mGuideRb3 = (RadioButton) findViewById(R.id.guide_rb3);
        mGuideRb4 = (RadioButton) findViewById(R.id.guide_rb4);
        mGuideRb5 = (RadioButton) findViewById(R.id.guide_rb5);
        mGuideRg = (RadioGroup) findViewById(R.id.guide_rg);
    }

    @Override
    protected void destroyData() {

    }
}
