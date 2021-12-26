package com.example.forum.ui.user;

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
import com.example.forum.adapter.MyPostRecyclerAdapter;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.model.vo.PaginationVO;
import com.example.forum.adapter.FooterHolder;
import com.example.forum.util.DateUtil;
import com.example.forum.util.LogUtil;
import com.example.forum.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity {

    public static final int MY_POST_MESSAGE = 0;
    public static final int PLATE_POST_MESSAGE = 1;
    public static final int SHOW_DELETE_POST_RESULT_MESSAGE = 2;

    private List<PostDTO> list;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PaginationVO pagination;
    private MyPostRecyclerAdapter adapter;
    private Toolbar toolbar;
    private long plateId;

    private boolean isPlatePost;
    private String title;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MY_POST_MESSAGE) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setFocusable(false);
                adapter.notifyDataSetChanged();

                if (!pagination.getHasNextPage()) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                }
            } else if (msg.what == PLATE_POST_MESSAGE) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setFocusable(true);
                adapter.notifyDataSetChanged();

                if (!pagination.getHasNextPage()) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                }
            } else if (msg.what == SHOW_DELETE_POST_RESULT_MESSAGE) {
                Bundle bundle = msg.getData();
                ToastUtil.show(MyPostActivity.this, bundle.getString("resMsg"));
                if (bundle.getInt("i") != 0) {
                    int position = bundle.getInt("position");
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        toolbar = findViewById(R.id.toolbar_head);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.green_700);

        try {
            plateId = getIntent().getExtras().getLong("plateId");
            isPlatePost = true;
            toolbar.setTitle("板块的帖子");
        } catch (NullPointerException e) {
            toolbar.setTitle("我的帖子");
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            reset();
            swipeRefreshLayout.setFocusable(false);
            getData(pagination.getPageNum(), pagination.getPageSize());
        });

        toolbar.setNavigationOnClickListener(v -> finish());

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
        recyclerView = findViewById(R.id.rv_post);
        pagination = new PaginationVO();
        pagination.setPageNum(1);
        pagination.setPageSize(10);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new MyPostRecyclerAdapter(this, list, handler);
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
        if (isPlatePost) {
            Requests.getPostsByPlateId(plateId, pageNum, pageSize, (ThenJson) jsonObject -> {
                JSONObject data = jsonObject.getJSONObject("data");
                title = data.getString("pname");

                JSONObject postsJSONObject = data.getJSONObject("posts");
                JSONArray jsonArray = postsJSONObject.getJSONArray("list");
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

                pagination.setPageNum(postsJSONObject.getInt("pageNum"));
                pagination.setPageSize(postsJSONObject.getInt("pageSize"));
                pagination.setTotal(postsJSONObject.getInt("total"));
                pagination.setHasNextPage(postsJSONObject.getBoolean("hasNextPage"));
                pagination.setNextPage(postsJSONObject.getInt("nextPage"));

                handler.sendEmptyMessage(PLATE_POST_MESSAGE);
            });
        } else {
            Requests.getUserPosts(pageNum, pageSize, (ThenJson) jsonObject -> {
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

                handler.sendEmptyMessage(MY_POST_MESSAGE);
            });
        }
    }
}