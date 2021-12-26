package com.example.forum.ui.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.adapter.PostRecyclerAdapter;
import com.example.forum.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserPostFragment extends Fragment {
    private static final int DEFAULT_MESSAGE = 0;

    private View root;

    private RecyclerView recyclerView;
    private PostRecyclerAdapter adapter;

    private List<PostDTO> list;
    private long userId;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE) {
                adapter.notifyDataSetChanged();
            }
        }
    };

    public static UserPostFragment newInstance(long userId) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putLong("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getLong("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_post, container, false);

        initRecyclerView();

        return root;
    }

    private void initRecyclerView() {
        list = new ArrayList<>();

        recyclerView = root.findViewById(R.id.rv_plate_post);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new PostRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        Requests.getPostsByUser(userId, (ThenJson) jsonObject -> {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ob = jsonArray.getJSONObject(i);
                PostDTO post = new PostDTO();
                post.setPostName(ob.getString("postName"));
                post.setPostId(ob.getLong("postId"));
                post.setUpdateDatetime(DateUtil.format(ob.getLong("updateDatetime")));
                post.setCreateDatetime(DateUtil.format(ob.getLong("createDatetime")));
                post.setContent(ob.getString("content"));
                post.setUsername(ob.getJSONObject("user").getString("uname"));
                list.add(post);
            }
            handler.sendEmptyMessage(DEFAULT_MESSAGE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }
}