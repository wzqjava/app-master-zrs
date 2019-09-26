package com.wd.health.main.mine;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.Constant;
import com.wd.health.base.BaseActivity;
import com.wd.health.main.R;

//TODO  需要修改入口
@Route(path = Constant.ACTIVITY_URL_MINE)
public class MineActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatImageView mTitleBackIv;        //返回
    private AppCompatImageView mTitleMessage;       //消息
    private SimpleDraweeView mMineUserHeaderIv;     //用户头像
    private AppCompatTextView mMineUserNameTv;      //用户名
    private AppCompatButton mMineUserSignBt;        //签到按钮
    private RelativeLayout mMineUserNowRl;          //当前问诊
    private RelativeLayout mMineUserHistroyRl;      //历史问诊
    private RelativeLayout mMineUserArchivesRl;     //我的档案
    private RelativeLayout mMineUserWalletRl;       //我的钱包
    private RelativeLayout mMineUserCollectionRl;   //我的收藏
    private RelativeLayout mMineUserAdviceRl;       //被采纳建议
    private RelativeLayout mMineUserVideoRl;        //我的视频
    private RelativeLayout mMineUserCircleRl;       //我的病友圈
    private RelativeLayout mMineUserAttentionRl;    //我的关注
    private RelativeLayout mMineUserTaskRl;         //我的任务
    private RelativeLayout mMineUserSettingsRl;     //设置管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).transparentBar().titleBar(R.id.toolbar).init();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        mTitleBackIv = (AppCompatImageView) findViewById(R.id.title_back_iv);
        mTitleBackIv.setOnClickListener(this);
        mTitleMessage = (AppCompatImageView) findViewById(R.id.title_message);
        mTitleMessage.setOnClickListener(this);
        mMineUserHeaderIv = (SimpleDraweeView) findViewById(R.id.mine_user_header_iv);
        mMineUserNameTv = (AppCompatTextView) findViewById(R.id.mine_user_name_tv);
        mMineUserSignBt = (AppCompatButton) findViewById(R.id.mine_user_sign_bt);
        mMineUserSignBt.setOnClickListener(this);
        mMineUserNowRl = (RelativeLayout) findViewById(R.id.mine_user_now_rl);
        mMineUserNowRl.setOnClickListener(this);
        mMineUserHistroyRl = (RelativeLayout) findViewById(R.id.mine_user_histroy_rl);
        mMineUserHistroyRl.setOnClickListener(this);
        mMineUserArchivesRl = (RelativeLayout) findViewById(R.id.mine_user_archives_rl);
        mMineUserArchivesRl.setOnClickListener(this);
        mMineUserWalletRl = (RelativeLayout) findViewById(R.id.mine_user_wallet_rl);
        mMineUserWalletRl.setOnClickListener(this);
        mMineUserCollectionRl = (RelativeLayout) findViewById(R.id.mine_user_collection_rl);
        mMineUserCollectionRl.setOnClickListener(this);
        mMineUserAdviceRl = (RelativeLayout) findViewById(R.id.mine_user_advice_rl);
        mMineUserAdviceRl.setOnClickListener(this);
        mMineUserVideoRl = (RelativeLayout) findViewById(R.id.mine_user_video_rl);
        mMineUserVideoRl.setOnClickListener(this);
        mMineUserCircleRl = (RelativeLayout) findViewById(R.id.mine_user_circle_rl);
        mMineUserCircleRl.setOnClickListener(this);
        mMineUserAttentionRl = (RelativeLayout) findViewById(R.id.mine_user_attention_rl);
        mMineUserAttentionRl.setOnClickListener(this);
        mMineUserTaskRl = (RelativeLayout) findViewById(R.id.mine_user_task_rl);
        mMineUserTaskRl.setOnClickListener(this);
        mMineUserSettingsRl = (RelativeLayout) findViewById(R.id.mine_user_settings_rl);
        mMineUserSettingsRl.setOnClickListener(this);
    }

    @Override
    protected void destroyData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.mine_user_sign_bt) {

        }
        if (id == R.id.title_back_iv) {
            ActivityUtils.finishActivity(this);
        }
        if (id == R.id.mine_user_archives_rl) {
//            intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        }
        if (id == R.id.mine_user_settings_rl) {
//            intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        }

        if( id == R.id.mine_user_now_rl){
              intentByRouter(Constant.ACTIVITY_URL_CURRENTC);
        }

        if( id==R.id.mine_user_histroy_rl){
              intentByRouter(Constant.ACTIVITY_URL_SPECIALH);
        }


    }
}
