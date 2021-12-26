package com.example.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.post.PostActivity;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.util.StringUtil;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL = 0;
    private static final int FOOTER = 1;

    private Context context;
    private List<PostDTO> list;
    private FooterHolder footerHolder;

    public PostRecyclerAdapter(Context context, List<PostDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.post_item, parent, false));
        } else {
            footerHolder = new FooterHolder(LayoutInflater.from(context).inflate(R.layout.footer, parent, false));
            return footerHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.size() == position ? FOOTER : NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            PostDTO post = list.get(position);
            itemHolder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putLong("postId", post.getPostId());
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
            itemHolder.tv_title.setText(post.getPostName());
            itemHolder.tv_sub_title.setText(StringUtil.htmlToText(post.getContent()));
            itemHolder.tv_update_date.setText(post.getUpdateDatetime());
        }
    }

    public void changeFooterStatus(FooterHolder.FooterStatus status) {
        if (footerHolder != null)
            footerHolder.changeStatus(status);
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public boolean isLast(int position) {
        return position == list.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_sub_title;
        public TextView tv_update_date;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_sub_title = itemView.findViewById(R.id.tv_sub_title);
            tv_update_date = itemView.findViewById(R.id.tv_update_date);
        }
    }
}
