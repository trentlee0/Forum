package com.example.forum.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.databinding.FragmentSearchBinding;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.model.vo.PaginationVO;
import com.example.forum.adapter.PostRecyclerAdapter;
import com.example.forum.adapter.FooterHolder;
import com.example.forum.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final int POSTS_CHANGE_MESSAGE = 0;

    private FragmentSearchBinding binding;

    private RecyclerView recyclerView;
    private PostRecyclerAdapter adapter;

    private EditText searchPostEditText;

    private PaginationVO pagination;
    private List<PostDTO> list;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == POSTS_CHANGE_MESSAGE) {
                adapter.notifyDataSetChanged();
                if (!list.isEmpty() && !pagination.getHasNextPage()) {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.END);
                } else {
                    adapter.changeFooterStatus(FooterHolder.FooterStatus.NORMAL);
                }
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchPostEditText = root.findViewById(R.id.et_search_post);
        initRecyclerView(root);

        searchPostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pagination.setTotal(0);
                pagination.setPageNum(1);
                pagination.setPageSize(10);
                pagination.setNextPage(0);
                pagination.setHasNextPage(false);
                list.clear();
                if (charSequence.toString().isEmpty()) {
                    handler.sendEmptyMessage(POSTS_CHANGE_MESSAGE);
                } else {
                    getData(searchPostEditText.getText().toString(), pagination.getPageNum(), pagination.getPageSize());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return root;
    }

    private void initRecyclerView(View root) {
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
                        getData(searchPostEditText.getText().toString(), pagination.getNextPage(), pagination.getPageSize());
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
    }

    private void getData(String q, int pageNum, int pageSize) {
        Requests.searchPosts(q, pageNum, pageSize, (ThenJson) jsonObject -> {
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

            handler.sendEmptyMessage(POSTS_CHANGE_MESSAGE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}