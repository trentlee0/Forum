package com.example.forum.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.user.MyPostActivity;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.ui.post.PostActivity;
import com.example.forum.util.StringUtil;

import java.util.List;

public class MyPostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL = 0;
    private static final int FOOTER = 1;

    private Context context;
    private List<PostDTO> list;
    private Handler handler;
    private FooterHolder footerHolder;

    public MyPostRecyclerAdapter(Context context, List<PostDTO> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.my_post_item, parent, false));
        } else {
            footerHolder = new FooterHolder(LayoutInflater.from(context).inflate(R.layout.footer, parent, false));
            return footerHolder;
        }
    }

    public void changeFooterStatus(FooterHolder.FooterStatus status) {
        if (footerHolder != null)
            footerHolder.changeStatus(status);
    }

    @Override
    public int getItemViewType(int position) {
        return list.size() == position ? FOOTER : NORMAL;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            PostDTO post = list.get(position);
            itemHolder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putLong("postId", post.getPostId());
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
            itemHolder.tv_title.setText(post.getPostName());
            itemHolder.tv_sub_title.setText(StringUtil.htmlToText(post.getContent()));
            itemHolder.tv_update_date.setText(post.getUpdateDatetime());
            itemHolder.btn_remove.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("删除")
                        .setMessage("确定删除帖子吗？")
                        .setPositiveButton("确定", (dialogInterface, i1) -> Requests.deletePostByPostId(post.getPostId(), (ThenJson) jsonObject -> {
                            String msg = jsonObject.getString("msg");
                            Message message = new Message();
                            message.what = MyPostActivity.SHOW_DELETE_POST_RESULT_MESSAGE;
                            Bundle bundle = new Bundle();
                            bundle.putString("resMsg", msg);
                            bundle.putInt("i", jsonObject.getJSONObject("data").getInt("affectedRows"));
                            bundle.putInt("position", position);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }))
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            });
        }
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
        public TextView btn_remove;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_sub_title = itemView.findViewById(R.id.tv_sub_title);
            tv_update_date = itemView.findViewById(R.id.tv_update_date);
            btn_remove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
