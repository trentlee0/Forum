package com.example.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forum.R;
import com.example.forum.model.dto.CommentDTO;
import com.example.forum.ui.user.UserInfoActivity;

import java.util.List;

public class CommentBaseAdapter extends BaseAdapter {

    private Context context;
    private List<CommentDTO> list;

    public CommentBaseAdapter(Context context, List<CommentDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
            holder.iv_avatar = view.findViewById(R.id.iv_avatar);
            holder.tv_username = view.findViewById(R.id.tv_username);
            holder.tv_publish_comment = view.findViewById(R.id.tv_publish_comment);
            holder.tv_content = view.findViewById(R.id.tv_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CommentDTO comment = list.get(i);
        Glide.with(context)
                .load(comment.getAvatar())
                .error(R.drawable.avatar)
                .circleCrop()
                .into(holder.iv_avatar);
        holder.iv_avatar.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("userId", comment.getUserId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
        holder.tv_username.setText(comment.getUsername());
        holder.tv_publish_comment.setText(comment.getPublishDatetime());
        holder.tv_content.setText(comment.getText());
        return view;
    }

    public static class ViewHolder {
        public ImageView iv_avatar;
        public TextView tv_username;
        public TextView tv_publish_comment;
        public TextView tv_content;
    }
}
