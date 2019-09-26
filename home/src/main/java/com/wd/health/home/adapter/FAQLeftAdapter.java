package com.wd.health.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.home.R;
import com.wd.health.home.bean.FAQLeftBean;

import java.util.List;

/**
 * @author 荣生
 * @description:常见问题一级列表适配器
 * @date :2019/8/6 21:48
 */
public class FAQLeftAdapter extends RecyclerView.Adapter<FAQLeftAdapter.Holder> {

    Context context;
    List<FAQLeftBean> imageList;
    private int flag = 0;
    private PlateAdapter.PlateBack back;

    public FAQLeftAdapter(Context context, List<FAQLeftBean> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public FAQLeftAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home__recy_vertical_department, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FAQLeftAdapter.Holder holder, final int i) {
        if (flag == i) {
            holder.view_flag.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);
        } else {
            holder.view_flag.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#DFDDCD"));
        }

        holder.content.setText(imageList.get(i).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (back != null) {
                    flag = i;
                    back.changed(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final View view_flag;
        private final TextView content;

        public Holder(@NonNull View itemView) {
            super(itemView);
            view_flag = itemView.findViewById(R.id.view_flag);
            content = itemView.findViewById(R.id.view_content);
        }
    }

    public void setLeftChange(PlateAdapter.PlateBack back) {
        this.back = back;
    }
}
