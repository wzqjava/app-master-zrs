package com.wd.health.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dueeeke.videoplayer.player.VideoView;
import com.wd.health.model.bean.VideoBean;
import com.wd.health.utils.ImageUtils;
import com.wd.health.videos.R;
import com.wd.health.widget.controller.TikTokController;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/12 09:40
 * 邮箱:1447925431@qq.com
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context mContext;
    private List<VideoBean> mVideoBeanList;
    private CountDownTimer mTimer;

    public VideoAdapter(Context context) {
        mContext = context;
        mVideoBeanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.video_item, viewGroup, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final String mToast1 = "试看";
        final String mToast2 = "15s";
        final String mToast3 = ",购买观看完整视频";

        SpannableString sStr = new SpannableString(mToast2);
        sStr.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mVideoItemToast.setText(mToast1 + sStr + mToast3);

        TikTokController controller = new TikTokController(mContext);
        holder.mVideoView.setVideoController(controller);           //设置控制器，如需定制可继承BaseVideoController

        VideoBean bean = mVideoBeanList.get(i);
        Bitmap bitmap = ImageUtils.getNetVideoBitmap(bean.getOriginalUrl());

        holder.mDraweeView.setImageBitmap(bitmap);
        //判断视频是否购买
        if (1 == bean.getWhetherBuy()) {
            holder.mVideoView.setUrl(bean.getOriginalUrl());

        } else {
            holder.mVideoView.setUrl(bean.getShearUrl());

        }
        holder.mVideoItemTitleTv.setText(bean.getTitle());
        holder.mVideoItemDescribeTv.setText(bean.getAbstracts());
        holder.mVideoItemBuyNum.setText(bean.getBuyNum() + "人购买");
        holder.mVideoItemProgress.setProgress(bean.getDuration());

        holder.mVideoItemPlayerIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.mVideoItemPlayerIv.setVisibility(View.GONE);
                holder.mDraweeView.setVisibility(View.GONE);
                holder.mVideoView.setVisibility(View.VISIBLE);
                holder.mVideoView.start();

                mTimer = new CountDownTimer(15000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        holder.mVideoItemToast.setText(mToast1 + millisUntilFinished / 1000 + "s" + mToast3);
                    }

                    public void onFinish() {
                        holder.mVideoItemToast.setText(mToast1 + 0 + "s" + mToast3);
                        holder.mVideoView.pause();
                    }
                }.start();

            }
        });

    }

    public void timerStop() {
        mTimer.cancel();
    }

    @Override
    public int getItemCount() {
        return mVideoBeanList.size();
    }

    public void addAll(List<VideoBean> result) {
        mVideoBeanList.addAll(result);
        notifyDataSetChanged();
    }

    public void clear() {
        mVideoBeanList.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView mDraweeView;               //缩略图
        private final VideoView mVideoView;                         //视频
        private final AppCompatImageView mVideoItemPlayerIv;        //播放按钮
        private final AppCompatTextView mVideoItemTitleTv;          //视频标题
        private final AppCompatTextView mVideoItemDescribeTv;       //视频描述
        private final AppCompatImageView mVideoItemBuyIv;           //购买按钮
        private final AppCompatImageView mVideoItemCommentIv;       //弹出输入框按钮
        private final AppCompatImageView mVideoItemCollectionIv;    //收藏按钮
        private final AppCompatImageView mVideoItemDanmuIv;         //弹幕开关按钮
        private final AppCompatTextView mVideoItemBuyNum;           //购买人数按钮
        private final AppCompatTextView mVideoItemToast;            //提示观看
        private final ProgressBar mVideoItemProgress;               //视频长度进度条

        ViewHolder(@NonNull View view) {
            super(view);
            this.mDraweeView = view.findViewById(R.id.video_item_iv);
            this.mVideoView = view.findViewById(R.id.video_item_vv);
            this.mVideoItemPlayerIv = view.findViewById(R.id.video_item_player_iv);
            this.mVideoItemTitleTv = view.findViewById(R.id.video_item_title_tv);
            this.mVideoItemDescribeTv = view.findViewById(R.id.video_item_describe_tv);
            this.mVideoItemBuyIv = view.findViewById(R.id.video_item_buy_iv);
            this.mVideoItemCommentIv = view.findViewById(R.id.video_item_comment_iv);
            this.mVideoItemCollectionIv = view.findViewById(R.id.video_item_collection_iv);
            this.mVideoItemDanmuIv = view.findViewById(R.id.video_item_danmu_iv);
            this.mVideoItemBuyNum = view.findViewById(R.id.video_item_buy_num);
            this.mVideoItemToast = view.findViewById(R.id.video_item_toast);
            this.mVideoItemProgress = view.findViewById(R.id.video_item_progress);
        }
    }

}
