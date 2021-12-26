package com.example.forum.ui.home;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.model.vo.PaginationVO;
import com.example.forum.adapter.PostRecyclerAdapter;
import com.example.forum.adapter.FooterHolder;
import com.example.forum.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    private static final int DEFAULT_MESSAGE = 0;

    private View root;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostRecyclerAdapter adapter;

    private List<PostDTO> list;
    private PaginationVO pagination;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE) {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setFocusable(true);
                swipeRefreshLayout.setRefreshing(false);
                if (!pagination.getHasNextPage()) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                }
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.green_700);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            reset();
            swipeRefreshLayout.setFocusable(false);
            getData(pagination.getPageNum(), pagination.getPageSize());
        });

        initRecyclerView();

        return root;
    }

    private void reset() {
        if (pagination != null) {
            pagination.setTotal(0);
            pagination.setPageNum(1);
            pagination.setPageSize(10);
            pagination.setNextPage(0);
            pagination.setHasNextPage(false);
        }
        if (list != null) {
            list.clear();
        }
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        pagination = new PaginationVO();
        pagination.setPageNum(1);
        pagination.setPageSize(10);

        recyclerView = root.findViewById(R.id.rv_plate_post);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new PostRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.isLast(manager.findLastVisibleItemPosition())) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.LOADING);
                    if (pagination.getHasNextPage() != null && pagination.getHasNextPage()) {
                        getData(pagination.getNextPage(), pagination.getPageSize());
                    } else {
                        adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                    }
                }
            }
        });

        getData(pagination.getPageNum(), pagination.getPageSize());
    }

    private void getData(int pageNum, int pageSize) {
        Requests.getAllPosts(pageNum, pageSize, (ThenJson) jsonObject -> {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = data.getJSONArray("list");
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

            pagination.setPageNum(data.getInt("pageNum"));
            pagination.setPageSize(data.getInt("pageSize"));
            pagination.setTotal(data.getInt("total"));
            pagination.setHasNextPage(data.getBoolean("hasNextPage"));
            pagination.setNextPage(data.getInt("nextPage"));

            handler.sendEmptyMessage(DEFAULT_MESSAGE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }
}