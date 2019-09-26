package com.wd.health.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.model.bean.KeShiBean;
import com.wd.health.patient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/7 20:37
 * 邮箱:1447925431@qq.com
 */
public class KeShiAdapter extends RecyclerView.Adapter<KeShiAdapter.ViewHolder> {
    private Context context;
    private List<KeShiBean> list =new ArrayList<>();

    public KeShiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.keshi_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.ke_text.setText(list.get(i).departmentName);
        viewHolder.ke_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(i).getId();
                String name = list.get(i).getDepartmentName();
                if (onCallback!=null){
                    onCallback.getData(id,name);
                }
            }
        });
    }

    //接口回调
    public interface OnCallback{
        void getData(int id,String i);
    }
    private OnCallback onCallback;

    public void setOnCallback(OnCallback onCallback){
        this.onCallback = onCallback;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<KeShiBean> result) {
        list.addAll(result);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ke_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ke_text = itemView.findViewById(R.id.ke_text);
        }
    }
}
