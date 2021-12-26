package com.example.forum.ui.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.forum.R;
import com.example.forum.adapter.PostRecyclerAdapter;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.model.vo.PaginationVO;
import com.example.forum.adapter.FooterHolder;
import com.example.forum.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlatePostActivity extends AppCompatActivity {

    private static final int DEFAULT_MESSAGE = 0;

    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostRecyclerAdapter adapter;

    private PaginationVO pagination;
    private List<PostDTO> list;
    private long plateId;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE) {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setFocusable(true);
                if (!pagination.getHasNextPage()) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_post);

        plateId = getIntent().getExtras().getLong("plateId");

        toolbar = findViewById(R.id.toolbar_head);
        toolbar.setNavigationOnClickListener(view -> finish());

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.green_700);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            reset();
            swipeRefreshLayout.setFocusable(false);
            getData(pagination.getPageNum(), pagination.getPageSize());
        });

        initRecyclerView();
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

        recyclerView = findViewById(R.id.rv_plate_post);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new PostRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.isLast(manager.findLastVisibleItemPosition())) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.LOADING);
                    if (pagination.getHasNextPage()) {
                        getData(pagination.getNextPage(), pagination.getPageSize());
                    } else {
                        if (!list.isEmpty()) {
                            adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                        } else {
                            adapter.changeFooterStatus(FooterHolder.FooterStatus.NORMAL);
                        }
                    }
                }
            }
        });

        getData(pagination.getPageNum(), pagination.getPageSize());
    }

    private void getData(int pageNum, int pageSize) {
        Requests.getPostsByPlateId(plateId, pageNum, pageSize, (ThenJson) jsonObject -> {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject postsJsonObject = data.getJSONObject("posts");
            JSONArray jsonArray = postsJsonObject.getJSONArray("list");
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

            pagination.setPageNum(postsJsonObject.getInt("pageNum"));
            pagination.setPageSize(postsJsonObject.getInt("pageSize"));
            pagination.setTotal(postsJsonObject.getInt("total"));
            pagination.setHasNextPage(postsJsonObject.getBoolean("hasNextPage"));
            pagination.setNextPage(postsJsonObject.getInt("nextPage"));

            handler.sendEmptyMessage(DEFAULT_MESSAGE);
        });
    }
}