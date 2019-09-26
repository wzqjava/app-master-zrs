package com.wd.health.home.emjoy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wd.health.home.R;

/**
 * @author 荣生
 * @description:表情包适配器
 * @date :2019/8/17 10:46
 */
public class EmjoyAdapter extends RecyclerView.Adapter<EmjoyAdapter.Holder> {

    Context context;
    EmojiBean[] sEmojiArray;
    private Click click;

    public EmjoyAdapter(Context context, EmojiBean[] sEmojiArray) {
        this.context=context;
        this.sEmojiArray=sEmojiArray;
    }

    @Override
    public EmjoyAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.emjoy_item, parent, false);
        Holder holder=new Holder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmjoyAdapter.Holder holder, final int position) {
        holder.iv.setImageResource(sEmojiArray[position].icon);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.play( sEmojiArray[position].emoji);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sEmojiArray.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final ImageView iv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }

    }

    public interface Click{
        void play(String s);
    }

    public void setOnClick(Click click){
        this.click =click;
    }

}
