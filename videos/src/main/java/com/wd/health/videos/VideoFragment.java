package com.wd.health.videos;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.dueeeke.videoplayer.player.VideoView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.wd.health.adapter.VideoAdapter;
import com.wd.health.adapter.VideoTabAdapter;
import com.wd.health.base.BaseFragment;
import com.wd.health.core.exception.ApiException;
import com.wd.health.model.DataCall;
import com.wd.health.model.bean.FindVideoListBean;
import com.wd.health.model.bean.VideoBean;
import com.wd.health.presenter.FindVideoCategoryListPresenter;
import com.wd.health.presenter.VideoPresenter;
import com.wd.health.utils.SpacesItemDecoration;
import com.wd.health.utils.ViewPagerLayoutManager;
import com.wd.health.utils.interfaces.OnViewPagerListener;
import com.wd.health.widget.controller.TikTokController;

import java.util.HashMap;
import java.util.List;


public class VideoFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mVideoListRv;
    private RecyclerView mVideoTabRv;
    private FindVideoCategoryListPresenter mFindVideoCategoryListPresenter;
    private VideoTabAdapter mVideoTabAdapter;
    private VideoPresenter mVideoPresenter;
    private static final int categoryId = 1;
    private static final int page = 1;
    private static final int count = 5;
    private VideoAdapter mVideoAdapter;
    private ViewPagerLayoutManager mManager;
    private int mCurrentPosition;
    private View mChildItem;
    private VideoView mVideoView;
    private RelativeLayout mVideoNotInternet;

    @Override
    public void onResume() {
        super.onResume();
        if (null != mVideoView) {
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mVideoView) {
            mVideoView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mVideoAdapter) {
            mVideoAdapter.timerStop();
        }
        if (null != mVideoView) {
            mVideoView.release();
        }
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.toolbar)
                .transparentBar()
                .autoDarkModeEnable(true)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .keyboardEnable(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected void setViewData(View view) {

        mVideoTabRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        boolean connected = NetworkUtils.isConnected();
        if (connected) {
            mVideoNotInternet.setVisibility(View.GONE);
            mFindVideoCategoryListPresenter = new FindVideoCategoryListPresenter(new FindVideoListDataCall());
            mFindVideoCategoryListPresenter.reqeust();
            setTabWidth();
            mVideoTabAdapter = new VideoTabAdapter(getActivity());
            mVideoTabRv.setAdapter(mVideoTabAdapter);
            mVideoPresenter = new VideoPresenter(new VideoListDataCall());
            mVideoPresenter.reqeust(categoryId, page, count);
            initVideo();
        } else {
            mVideoNotInternet.setVisibility(View.VISIBLE);
        }

    }

    private void initVideo() {

        TikTokController controller = new TikTokController(getActivity());
        mVideoView.setVideoController(controller);
        mVideoAdapter = new VideoAdapter(getActivity());
        mManager = new ViewPagerLayoutManager(getActivity(), LinearLayoutManager.VERTICAL);
        mVideoListRv.setLayoutManager(mManager);
        mVideoListRv.setAdapter(mVideoAdapter);
        //监听子页面状态
        mVideoListRv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                VideoView videoView = view.findViewById(R.id.video_item_vv);
                if (videoView != null && !videoView.isFullScreen()) {
                    videoView.release();
                }
            }
        });
    }

    private void setTabWidth() {
        HashMap<String, Integer> spacesVelue = new HashMap<>();
        spacesVelue.put(SpacesItemDecoration.TOP_SPACE, 30); //item上边距
        spacesVelue.put(SpacesItemDecoration.BOTTOM_SPACE, 30); //item下边距
        spacesVelue.put(SpacesItemDecoration.LEFT_SPACE, 60); //item左边距
        spacesVelue.put(SpacesItemDecoration.RIGHT_SPACE, 60); //item 右边距
        mVideoTabRv.addItemDecoration(new SpacesItemDecoration(spacesVelue, true)); //true代表有边距，false表示没有边距
    }

    @Override
    protected void initView(View view) {
        mVideoListRv = (RecyclerView) view.findViewById(R.id.video_list_rv);
        mVideoTabRv = (RecyclerView) view.findViewById(R.id.video_tab_rv);
        mChildItem = LayoutInflater.from(getActivity()).inflate(R.layout.video_item, null);
        mVideoView = mChildItem.findViewById(R.id.video_item_vv);
        mVideoNotInternet = (RelativeLayout) view.findViewById(R.id.video_not_internet);
        mVideoNotInternet.setOnClickListener(this);

    }

    @Override
    public int setLayoutView() {
        return R.layout.fragment_video;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.video_not_internet) {
            mVideoNotInternet.setVisibility(View.GONE);
            mFindVideoCategoryListPresenter = new FindVideoCategoryListPresenter(new FindVideoListDataCall());
            mFindVideoCategoryListPresenter.reqeust();
            mVideoTabRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            setTabWidth();
            mVideoTabAdapter = new VideoTabAdapter(getActivity());
            mVideoTabRv.setAdapter(mVideoTabAdapter);
            mVideoPresenter = new VideoPresenter(new VideoListDataCall());
            mVideoPresenter.reqeust(categoryId, page, count);
            initVideo();
        }
    }

    class FindVideoListDataCall implements DataCall<List<FindVideoListBean>> {

        @Override
        public void onSuccess(List<FindVideoListBean> result, String message) {
            mVideoTabAdapter.addList(result);
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }

    class VideoListDataCall implements DataCall<List<VideoBean>> {

        @Override
        public void onSuccess(final List<VideoBean> result, String message) {
            LogUtils.d(result);
            mVideoAdapter.addAll(result);
            mManager.setOnViewPagerListener(new OnViewPagerListener() {
                @Override
                public void onInitComplete() {
                    boolean wifiAvailable = NetworkUtils.isWifiConnected();
                    if (wifiAvailable) {
                        mVideoView.start();
                    }
                }

                @Override
                public void onPageRelease(boolean isNext, int position) {
                    if (mCurrentPosition == position) {
                        mVideoView.release();
                    }
                }

                @Override
                public void onPageSelected(int position, boolean isBottom) {
                    if (mCurrentPosition == position) return;
                    mVideoView.start();
                    mCurrentPosition = position;
                }
            });
        }

        @Override
        public void onFaild(ApiException e) {

        }
    }


}
