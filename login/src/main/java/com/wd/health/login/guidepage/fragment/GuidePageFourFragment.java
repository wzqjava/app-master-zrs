package com.wd.health.login.guidepage.fragment;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.wd.health.base.BaseFragment;
import com.wd.health.login.R;

/**
 * Created :  LiZhIX
 * Date :  2019/8/8 8:40
 * Description  :    引导页4页面
 */
public class GuidePageFourFragment extends BaseFragment {

    private AppCompatTextView mGuidePageTv;
    private AppCompatImageView mGuidePageIv;

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected void setViewData(View view) {
        mGuidePageTv.setText(R.string.guide_page_four);
        mGuidePageIv.setImageResource(R.drawable.guide_pages_four);
    }

    @Override
    protected void initView(View view) {
        mGuidePageTv = view.findViewById(R.id.guide_page_4_tv);
        mGuidePageIv = view.findViewById(R.id.guide_page_4_iv);
    }

    @Override
    public int setLayoutView() {
        return R.layout.fragment_guide_page_four;
    }
}
