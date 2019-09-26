package com.wd.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.activity.DetailsActivity;
import com.wd.health.model.bean.CircleBean;
import com.wd.health.patient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/7 21:19
 * 邮箱:1447925431@qq.com
 */
public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private Context context;
    private List<CircleBean> list =new ArrayList<>();

    public CircleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.circle_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.c_name.setText(list.get(i).getTitle());
        long time = list.get(i).getReleaseTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        String day = simpleDateFormat.format(new Date(time));
        viewHolder.c_time.setText(day);
        viewHolder.c_nc.setText(list.get(i).getDetail());
        viewHolder.c_sc.setText("收藏"+list.get(i).getAmount());
        viewHolder.c_jy.setText("建议"+list.get(i).getSickCircleId());
        viewHolder.c_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,DetailsActivity.class);
                intent.putExtra("name",list.get(i).getTitle());
                intent.putExtra("id",list.get(i).getSickCircleId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CircleBean> result) {
        list.addAll(result);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView c_name;
        private final TextView c_time;
        private final TextView c_nc;
        private final TextView c_sc;
        private final TextView c_jy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_name = itemView.findViewById(R.id.c_name);
            c_time = itemView.findViewById(R.id.c_time);
            c_nc = itemView.findViewById(R.id.c_nc);
            c_sc = itemView.findViewById(R.id.c_sc);
            c_jy = itemView.findViewById(R.id.c_jy);

        }
    }
}
