package com.wd.health.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.home.R;
import com.wd.health.home.activity.ChatActivity;
import com.wd.health.home.activity.SpecialHistoryActivity;
import com.wd.health.home.httpbean.SpecialHBean;

import java.util.List;

/**
 * @author 荣生
 * @description:
 * @date :2019/8/23 19:27
 */
public class SpecialHistoryAdapter extends XRecyclerView.Adapter<SpecialHistoryAdapter.Holder> {
    Context context;
    List<SpecialHBean.ResultBean> list;
    private HistoryBack back;
    private EvaluateBack evaluateBack;

    public SpecialHistoryAdapter(Context context, List<SpecialHBean.ResultBean> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public SpecialHistoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.specialhistory_item, viewGroup, false);
        Holder holder=new Holder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialHistoryAdapter.Holder holder, final int i) {

        int evaluateStatus = list.get(i).getEvaluateStatus();
        if(evaluateStatus!=1){
            holder.see_evaluate.setVisibility(View.VISIBLE);
            holder.go_evaluate.setVisibility(View.GONE);
        }

        Glide.with(context).load(list.get(i).getImagePic())
                .into(holder.pic);
        holder.job.setText(list.get(i).getJobTitle());
        holder.name.setText(list.get(i).getDoctorName());
        String s = TimeUtils.millis2String(list.get(i).getInquiryTime());
        holder.time.setText(s);

        holder.history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.play(i);
            }
        });

        holder.go_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateBack.play(list.get(i).getRecordId(),list.get(i).getDoctorId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final TextView name,job,time;
        private final ImageView pic;
        private final Button history_button;
        private final Button go_evaluate,see_evaluate;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sh_name);
            pic = itemView.findViewById(R.id.sh_pic);
            job = itemView.findViewById(R.id.sh_job);
            time = itemView.findViewById(R.id.sh_time);
            history_button = itemView.findViewById(R.id.history_button);
            go_evaluate = itemView.findViewById(R.id.go_evaluate);
            see_evaluate = itemView.findViewById(R.id.see_evaluate);
        }
    }

    public interface HistoryBack{
        void play(int i);
    }

    public void setHistoryBack(HistoryBack back){
        this.back =back;
    }

    public interface EvaluateBack{
        void play(int inquiryRecordId,int doctorId);
    }

    public void setEvaluateBack(EvaluateBack back){
        evaluateBack =back;
    }

}
