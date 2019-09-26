package com.wd.health.login.guidepage.fragment;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.wd.health.Constant;
import com.wd.health.base.BaseFragment;
import com.wd.health.login.R;

/**
 * Created :  LiZhIX
 * Date :  2019/8/8 8:50
 * Description  :    引导页5页面
 */
public class GuidePageFiveFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatTextView mGuidePageTv;
    private AppCompatImageView mGuidePageIv;
    private AppCompatButton mGuidePageBt;

    @Override
    protected void initImmersionBar() {

    }

    @Override
    protected void setViewData(View view) {
        mGuidePageTv.setText(R.string.guide_page_five);
        mGuidePageIv.setImageResource(R.drawable.guide_pages_five);

    }

    @Override
    protected void initView(View view) {
        mGuidePageTv = view.findViewById(R.id.guide_page_5_tv);
        mGuidePageIv = view.findViewById(R.id.guide_page_5_iv);
        mGuidePageBt = view.findViewById(R.id.guide_page_5_bt);
        mGuidePageBt.setOnClickListener(this);
    }

    @Override
    public int setLayoutView() {
        return R.layout.fragment_guide_page_five;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.guide_page_5_bt) {
            intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        }
    }
}
