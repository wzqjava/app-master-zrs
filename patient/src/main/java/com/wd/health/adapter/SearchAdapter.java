package com.wd.health.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.model.bean.SearchBean;
import com.wd.health.patient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/15 09:41
 * 邮箱:1447925431@qq.com
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<SearchBean> list =new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        long time = list.get(i).getReleaseTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        String day = simpleDateFormat.format(new Date(time));
        viewHolder.time.setText(day);
        viewHolder.detail.setText(list.get(i).getDetail());
        viewHolder.s_commentNum.setText("建议"+list.get(i).getCommentNum());
        viewHolder.s_collectionNum.setText("收藏"+list.get(i).getCollectionNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(List<SearchBean> result) {
        if (result!=null){
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView time;
        private final TextView detail;
        private final TextView s_commentNum;
        private final TextView s_collectionNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.s_title);
            time = itemView.findViewById(R.id.s_time);
            detail = itemView.findViewById(R.id.s_detail);
            s_commentNum = itemView.findViewById(R.id.s_commentNum);
            s_collectionNum = itemView.findViewById(R.id.s_collectionNum);
        }
    }
}
