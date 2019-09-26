package com.wd.health.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wd.health.home.R;
import com.wd.health.home.httpbean.PlateListBean;

import java.util.List;

/**
 * @author 荣生
 * @description:资讯标题适配器
 * @date :2019/8/6 21:48
 */
public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.Holder> {

    private Context context;
    private List<PlateListBean.ResultBean> result;
    private int flag = 0;
    private PlateBack back;

    public PlateAdapter(Context context, List<PlateListBean.ResultBean> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public PlateAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recy_plate, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlateAdapter.Holder holder, final int i) {
        if (i == flag) {
            holder.title.setTextColor(Color.RED);
        } else {
            holder.title.setTextColor(Color.BLACK);
        }
        holder.title.setText(result.get(i).getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (back != null) {
                    flag = i;
                    back.changed(result.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.plate_title);
        }
    }

    public interface PlateBack {
        void changed(int i);
    }

    public void setPlateChange(PlateBack back) {
        this.back = back;
    }
}
