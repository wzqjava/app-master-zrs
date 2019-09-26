package com.wd.health.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.home.R;
import com.wd.health.home.httpbean.DepartmentBean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 张荣生
 * date: 2019/8/7 09:25
 * update: $date$
 */
public class WzTitleAdapter extends RecyclerView.Adapter<WzTitleAdapter.Holders> {
    ArrayList<DepartmentBean.ResultBean> Wzlist;
    Context context;
    private int flag = -1;

    public WzTitleAdapter(ArrayList<DepartmentBean.ResultBean> wzlist, Context context) {
        Wzlist = wzlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.wztitlelayout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    public void setColor(int position) {
        this.flag = position;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final Holders holders, final int i) {
        holders.lefttext.setText(Wzlist.get(i).getDepartmentName());

        if (flag == i) {
            holders.lefttext.setTextColor(Color.parseColor("#E994F7"));
        } else {
            holders.lefttext.setTextColor(Color.parseColor("#383638"));
        }

        holders.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemclick.getposition(i);
                flag = i;

            }
        });


    }

    @Override
    public int getItemCount() {
        return Wzlist.size();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView lefttext;

        public Holders(@NonNull View itemView) {
            super(itemView);

            lefttext = itemView.findViewById(R.id.wztitletext);
        }
    }

    public interface OnItemclick {
        void getposition(int position);
    }

    OnItemclick onItemclick;

    public void setOnItemclick(OnItemclick onItemclick) {
        this.onItemclick = onItemclick;
    }
}
