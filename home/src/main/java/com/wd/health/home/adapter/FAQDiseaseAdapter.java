package com.wd.health.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.home.R;
import com.wd.health.home.activity.FAQActivity;
import com.wd.health.home.httpbean.DiseaseCategoryBean;

import java.util.List;

/**
 * @author 荣生
 * @description:常见病症适配器
 * @date :2019/8/18 19:45
 */
public class FAQDiseaseAdapter extends RecyclerView.Adapter<FAQDiseaseAdapter.Holder> {

    private Context context;
    private List<DiseaseCategoryBean.ResultBean> list;
    private DiseaseBack diseaseBack;

    public FAQDiseaseAdapter(Context context, List<DiseaseCategoryBean.ResultBean> disease_list) {
        this.context = context;
        this.list = disease_list;
    }

    @NonNull
    @Override
    public FAQDiseaseAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.faq_disease_item, viewGroup, false);
        Holder holder = new Holder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FAQDiseaseAdapter.Holder holder, final int i) {
        holder.tv.setText(list.get(i).getName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(i).getId();
                String name = list.get(i).getName();
                diseaseBack.play(id,name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.disease_tv);
        }
    }

    public interface DiseaseBack{
        void play(int i,String s);
    }

    public void  getDiseaseBack(DiseaseBack back){
        diseaseBack =back;
    }
}
