package com.wd.health.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.model.bean.KeShiQueryBean;
import com.wd.health.patient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/21 18:13
 * 邮箱:1447925431@qq.com
 */
public class KeShiQueryAdapter extends RecyclerView.Adapter<KeShiQueryAdapter.ViewHolder> {
    private Context context;
    private List<KeShiQueryBean> list =new ArrayList<>();

    public KeShiQueryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ke_shi_query_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (list!=null){
            viewHolder.sick_publish_itemtext_id.setText(list.get(i).getName());
            viewHolder.sick_publish_itemtext_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = list.get(i).getName();
                    onCallback.getData(s);
                }
            });
        }
    }

    public void addAll(List<KeShiQueryBean> result) {
        list.addAll(result);
        notifyDataSetChanged();
    }

    public void clert() {
        list.clear();
    }

    //定义接口回调
    public interface onCallback{
        void getData(String i);
    }
    public onCallback onCallback;

    public void setOnCallback(onCallback onCallback){
        this.onCallback = onCallback;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView sick_publish_itemtext_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sick_publish_itemtext_id = itemView.findViewById(R.id.sick_publish_itemtext_id);
        }
    }
}
