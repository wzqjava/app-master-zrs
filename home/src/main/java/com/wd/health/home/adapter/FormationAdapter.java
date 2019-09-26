package com.wd.health.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wd.health.home.R;
import com.wd.health.home.httpbean.FormationBean;
import com.wd.health.home.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 荣生
 * @description:资讯列表适配器
 * @date :2019/8/7 10:15
 */
public class FormationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private Context context;
    private List<FormationBean.ResultBean> list;
    private int Type_one = 0;
    private int Type_two = 1;
    private int Type_three = 2;
    private FormationAdapter.itemClick itemClick;

    public FormationAdapter(Context context, List<FormationBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Type_one) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home_recy_formation_one, viewGroup, false);
            return new HolderOne(inflate);
        } else if (i == Type_two) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home_recy_formation_two, viewGroup, false);
            return new HolderTwo(inflate);
        } else if (i == Type_three) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home_recy_formation_three, viewGroup, false);
            return new HolderThree(inflate);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home_recy_formation_one, viewGroup, false);
            return new HolderOne(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        String timeAgo = TimeUtil.getTimeAgo(list.get(i).getReleaseTime());

        if (viewHolder instanceof HolderOne) {

            HolderOne holder = (HolderOne) viewHolder;
            holder.oneTitle.setText(list.get(i).getTitle());
            holder.recy_time.setText(timeAgo);

        } else if (viewHolder instanceof HolderTwo) {

            HolderTwo holder = (HolderTwo) viewHolder;
            String thumbnail = list.get(i).getThumbnail();
            String[] split = thumbnail.split(";");
            holder.twoTitle.setText(list.get(i).getTitle());
            Glide.with(context).load(split[0]).into(holder.two_image);
            holder.recy_time.setText(timeAgo);

        } else if (viewHolder instanceof HolderThree) {

            HolderThree holder = (HolderThree) viewHolder;
            String thumbnail = list.get(i).getThumbnail();
            String[] split = thumbnail.split(";");
            holder.threeTitle.setText(list.get(i).getTitle());
            Glide.with(context).load(split[0]).into(holder.three_image1);
            Glide.with(context).load(split[1]).into(holder.three_image2);
            Glide.with(context).load(split[2]).into(holder.three_image3);
            holder.recy_time.setText(timeAgo);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.play(list.get(i).getId());
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        String thumbnail = list.get(position).getThumbnail();
        String[] split = null;
        try {
            split = thumbnail.split(";");
        } catch (Exception e) {

        }

        if (split == null) {
            return Type_one;
        } else if (split.length == 1) {
            return Type_two;
        } else if (split.length == 3) {
            return Type_three;
        } else {
            return Type_one;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderOne extends RecyclerView.ViewHolder {

        private final TextView oneTitle;
        private final TextView recy_time;

        public HolderOne(@NonNull View itemView) {
            super(itemView);
            oneTitle = itemView.findViewById(R.id.formation_oneTitle);
            recy_time = itemView.findViewById(R.id.recy_time);
        }
    }

    public class HolderTwo extends RecyclerView.ViewHolder {

        private final TextView twoTitle;
        private final ImageView two_image;
        private final TextView recy_time;

        public HolderTwo(@NonNull View itemView) {
            super(itemView);
            twoTitle = itemView.findViewById(R.id.formation_twoTitle);
            two_image = itemView.findViewById(R.id.formation_two_image);
            recy_time = itemView.findViewById(R.id.recy_time);
        }
    }

    public class HolderThree extends RecyclerView.ViewHolder {

        private final TextView threeTitle;
        private final ImageView three_image1, three_image2, three_image3;
        private final TextView recy_time;

        public HolderThree(@NonNull View itemView) {
            super(itemView);
            threeTitle = itemView.findViewById(R.id.formation_threeTitle);
            three_image1 = itemView.findViewById(R.id.formation_three_image1);
            three_image2 = itemView.findViewById(R.id.formation_three_image2);
            three_image3 = itemView.findViewById(R.id.formation_three_image3);
            recy_time = itemView.findViewById(R.id.recy_time);
        }
    }

    public interface itemClick {
        void play(int i);
    }

    public void setItemClick(itemClick itemClick) {
        this.itemClick = itemClick;
    }

}
