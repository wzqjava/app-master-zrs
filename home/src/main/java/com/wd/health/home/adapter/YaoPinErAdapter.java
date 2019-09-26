package com.wd.health.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.home.R;
import com.wd.health.home.httpbean.YaoErBean;

import java.util.ArrayList;
import java.util.List;

/**
 * description: dell
 * author: 张荣生
 * date: 2019/8/7 09:25
 * update: $date$
 * 二级药品适配器
 */
public class YaoPinErAdapter extends XRecyclerView.Adapter<YaoPinErAdapter.Holders> {
    List<YaoErBean.ResultBean> yaoperlist;
    Context context;
    private YaoPinBack yaoPinBack;

    public YaoPinErAdapter(List<YaoErBean.ResultBean> yaoperlist, Context context) {
        this.yaoperlist = yaoperlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.yaopinerlaout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holders, final int i) {
        holders.yaopintext.setText(yaoperlist.get(i).getName());
        Glide.with(context).load(yaoperlist.get(i).getPicture()).into(holders.yaopimg);
        holders.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = yaoperlist.get(i).getId();
                String name = yaoperlist.get(i).getName();
                yaoPinBack.play(id,name);
            }
        });


    }

    @Override
    public int getItemCount() {
        return yaoperlist.size();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView yaopintext;
        ImageView yaopimg;

        public Holders(@NonNull View itemView) {
            super(itemView);
            yaopintext = itemView.findViewById(R.id.yaopintext);
            yaopimg = itemView.findViewById(R.id.yaopimg);
        }
    }

    public interface OnItemclick {
        void getposition(int position);
    }

    public interface YaoPinBack{
        void play(int i,String s);
    }

    public void  getYaoPinBack(YaoPinBack back){
        yaoPinBack =back;
    }
}
