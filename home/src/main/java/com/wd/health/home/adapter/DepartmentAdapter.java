package com.wd.health.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wd.health.home.R;
import com.wd.health.home.httpbean.DepartmentBean;

import java.util.List;

/**
 * @author 荣生
 * @description:科室列表适配器
 * @date :2019/8/6 21:48
 */
public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.Holder> {

    Context context;
    List<DepartmentBean.ResultBean> imageList;
    private DepartmentAdapter.onClick onClick;

    public DepartmentAdapter(Context context, List<DepartmentBean.ResultBean> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public DepartmentAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home__recy_department, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentAdapter.Holder holder, final int i) {
        Glide.with(context).load(imageList.get(i).getPic()).into(holder.pic);
        holder.name.setText(imageList.get(i).getDepartmentName());
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.play(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final ImageView pic;
        private final TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.pic_name);
        }
    }

    public interface onClick {
        void play(int i);
    }

    public void setOnClick(onClick click) {
        onClick = click;
    }
}
