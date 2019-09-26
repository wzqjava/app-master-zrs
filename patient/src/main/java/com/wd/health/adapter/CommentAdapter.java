package com.wd.health.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.model.bean.CommentBean;
import com.wd.health.patient.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者:毛佳翔 by Administor on 2019/8/20 15:35
 * 邮箱:1447925431@qq.com
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<CommentBean> list =new ArrayList<>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sick_comment_recyler_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(list.get(i).getHeadPic());
        viewHolder.sickCommentPicture.setImageURI(uri);

        viewHolder.sickCommentNickName.setText(list.get(i).getNickName());
        viewHolder.sickCommentContext.setText(list.get(i).getContent());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String format = dateFormat.format(new Date(list.get(i).getCommentTime()));
        viewHolder.sickCommentTime.setText(format);
        viewHolder.sickComentDisagreenum.setText(list.get(i).getOpposeNum() + "");
        viewHolder.sickComentAgreenum.setText(list.get(i).getSupportNum() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CommentBean> result) {
        list.addAll(result);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView sickCommentPicture;
        private TextView sickCommentNickName;
        private TextView sickCommentContext;
        private TextView sickCommentTime;
        private ImageView sickCommentDisagree;
        private TextView sickComentDisagreenum;
        private ImageView sickCommentAgree;
        private TextView sickComentAgreenum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sickCommentPicture = itemView.findViewById(R.id.sick_comment_picture);
            sickCommentNickName = itemView.findViewById(R.id.sick_comment_nickName);
            sickCommentContext = itemView.findViewById(R.id.sick_comment_context);
            sickCommentTime = itemView.findViewById(R.id.sick_comment_time);
            sickCommentDisagree = itemView.findViewById(R.id.sick_comment_disagree);
            sickComentDisagreenum = itemView.findViewById(R.id.sick_coment_disagreenum);
            sickCommentAgree = itemView.findViewById(R.id.sick_comment_agree);
            sickComentAgreenum = itemView.findViewById(R.id.sick_coment_agreenum);
        }
    }
}
