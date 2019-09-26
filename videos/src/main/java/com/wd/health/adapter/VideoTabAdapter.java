package com.wd.health.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wd.health.model.bean.FindVideoListBean;
import com.wd.health.videos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created :  LiZhIX
 * Date :  2019/8/14 16:59
 * Description  :
 */
public class VideoTabAdapter extends RecyclerView.Adapter<VideoTabAdapter.Holder> {

    private Context mContext;
    private List<FindVideoListBean> mFindVideoListBeans;

    public VideoTabAdapter(Context context) {
        mContext = context;
        mFindVideoListBeans = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.tab_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        FindVideoListBean listBean = mFindVideoListBeans.get(i);
        holder.mTabTv.setText(listBean.getName());
    }

    @Override
    public int getItemCount() {
        return mFindVideoListBeans.size();
    }

    public void addList(List<FindVideoListBean> findVideoListBeans) {
        mFindVideoListBeans.addAll(findVideoListBeans);
        notifyDataSetChanged();
    }

    public void clearList(){
        mFindVideoListBeans.clear();
    }
    class Holder extends RecyclerView.ViewHolder {

        AppCompatTextView mTabTv;

        public Holder(@NonNull View view) {
            super(view);
            this.mTabTv = (AppCompatTextView) view.findViewById(R.id.tab_tv);
        }
    }
}
