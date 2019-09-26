package com.wd.health.home.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wd.health.home.R;
import com.wd.health.home.bean.DialogueBean;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/5 10:47
 */
public class ChatingAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DialogueBean> list;
    Context context;

    public ChatingAdapter2(List<DialogueBean> list_send, Context context) {
        this.list = list_send;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int flag = list.get(i).getFlag();
        if (flag == 0) {
            MessageContent content = list.get(i).getMessage().getContent();
            View inflate = LayoutInflater.from(context).inflate(R.layout.dia_send, viewGroup, false);
            Holder holder = new Holder(inflate);
            return holder;
        } else {
            MessageContent content = list.get(i).getMessage().getContent();
            View inflate = LayoutInflater.from(context).inflate(R.layout.dia_receive, viewGroup, false);
            Holder1 holder = new Holder1(inflate);
            return holder;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof Holder) {
            final MessageContent content =list.get(i).getMessage().getContent();

            if(content instanceof TextContent){
                ((Holder) viewHolder).send_tv.setText(((TextContent) content).getText());
                ((Holder) viewHolder).send_image.setVisibility(View.GONE);
                ((Holder) viewHolder).send_yy.setVisibility(View.GONE);
                ((Holder) viewHolder).send_tv.setVisibility(View.VISIBLE);
            }else if(content instanceof ImageContent){
                ((Holder) viewHolder).send_image.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).send_yy.setVisibility(View.GONE);
                ((Holder) viewHolder).send_tv.setVisibility(View.GONE);
                ((ImageContent)content).downloadOriginImage(list.get(i).getMessage(), new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        Glide.with(context).load(file).into(((Holder) viewHolder).send_image);
                    }
                });
            }else {
                ((Holder) viewHolder).send_image.setVisibility(View.GONE);
                ((Holder) viewHolder).send_yy.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).send_tv.setVisibility(View.GONE);
                ((Holder) viewHolder).send_yy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                ((VoiceContent)content).downloadVoiceFile(list.get(i).getMessage(), new DownloadCompletionCallback() {
                            @Override
                            public void onComplete(int i, String s, File file) {
                                Toast.makeText(context, "下载完毕", Toast.LENGTH_SHORT).show();
                                doPlay(file);
                            }
                        });
                    }
                });
            }


        } else if (viewHolder instanceof Holder1) {
            final MessageContent content =list.get(i).getMessage().getContent();

            if(content instanceof TextContent){
                ((Holder1) viewHolder).receive_tv.setText(((TextContent) content).getText());
                ((Holder1) viewHolder).receive_tv.setVisibility(View.VISIBLE);
                ((Holder1) viewHolder).receive_image.setVisibility(View.GONE);
                ((Holder1) viewHolder).receive_yy.setVisibility(View.GONE);
            }else if(content instanceof ImageContent){
                ((Holder1) viewHolder).receive_image.setVisibility(View.VISIBLE);
                ((Holder1) viewHolder).receive_yy.setVisibility(View.GONE);
                ((Holder1) viewHolder).receive_tv.setVisibility(View.GONE);
                ((ImageContent)content).downloadOriginImage(list.get(i).getMessage(), new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        Glide.with(context).load(file).into(((Holder1) viewHolder).receive_image);
                    }
                });
            }else {
                ((Holder1) viewHolder).receive_image.setVisibility(View.GONE);
                ((Holder1) viewHolder).receive_yy.setVisibility(View.VISIBLE);
                ((Holder1) viewHolder).receive_tv.setVisibility(View.GONE);
                ((Holder1) viewHolder).receive_yy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((VoiceContent)content).downloadVoiceFile(list.get(i).getMessage(), new DownloadCompletionCallback() {
                            @Override
                            public void onComplete(int i, String s, File file) {
                                Toast.makeText(context, "下载完毕", Toast.LENGTH_SHORT).show();
                                doPlay(file);
                            }
                        });
                    }
                });
            }

        }

    }

    public class Holder extends RecyclerView.ViewHolder {

        private final TextView send_tv;
        private final ImageView send_image;
        private final ImageView send_yy;

        public Holder(@NonNull View itemView) {
            super(itemView);
            send_tv = itemView.findViewById(R.id.send_tv);
            send_image = itemView.findViewById(R.id.send_image);
            send_yy = itemView.findViewById(R.id.send_yy);
        }
    }

    public class Holder1 extends RecyclerView.ViewHolder {

        private final TextView receive_tv;
        private final ImageView receive_image;
        private final ImageView receive_yy;

        public Holder1(@NonNull View itemView) {
            super(itemView);
            receive_tv = itemView.findViewById(R.id.receive_tv);
            receive_image = itemView.findViewById(R.id.receive_image);
            receive_yy = itemView.findViewById(R.id.receive_yy);
        }
    }

    private void doPlay(File mAudioFile) {
        //配置播放器 MediaPlayer
        MediaPlayer mMediaPlayer = new MediaPlayer();


        try {

            //设置声音文件
            mMediaPlayer.setDataSource(mAudioFile.getAbsolutePath());

            //设置监听回调
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                }
            });

            //配置音量，是否循环
            mMediaPlayer.setVolume(1, 1);
            mMediaPlayer.setLooping(false);

            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
