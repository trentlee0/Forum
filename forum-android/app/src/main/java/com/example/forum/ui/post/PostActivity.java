package com.example.forum.ui.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.forum.R;
import com.example.forum.adapter.CommentBaseAdapter;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.CommentDTO;
import com.example.forum.model.dto.PostDTO;
import com.example.forum.ui.user.UserInfoActivity;
import com.example.forum.util.DateUtil;
import com.example.forum.util.InputMethodUtil;
import com.example.forum.util.ListViewUtil;
import com.example.forum.util.LogUtil;
import com.example.forum.util.MessageUtil;
import com.example.forum.util.StringUtil;
import com.example.forum.util.ToastUtil;
import com.example.forum.util.UserUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private static final int UPDATE_POSTS_MESSAGE = 0;
    private static final int COMMENT_SUCCESS_MESSAGE = 1;
    private static final int APPROVE_MESSAGE = 2;
    private static final int GET_APPROVE_MESSAGE = 3;

    private ImageView avatarImageView;
    private TextView postNameTextView;
    private TextView userTextView;
    private TextView updateDateTextView;
    private ListView commentListView;
    private TextView openCommentTextView;

    private LinearLayout thumbLinearLayout;
    private TextView approvalTextView;
    private ImageView thumbUpImageView;

    private WebView contentWebView;

    private RelativeLayout commentLayout;
    private EditText commentEditText;

    private PostDTO post;

    private long postId;
    private boolean isApprove;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_POSTS_MESSAGE && post != null) {
                CommentBaseAdapter adapter = new CommentBaseAdapter(PostActivity.this, post.getComments());
                commentListView.setAdapter(adapter);

                postNameTextView.setText(post.getPostName());
                userTextView.setText(post.getUsername());
                updateDateTextView.setText(post.getUpdateDatetime());
                approvalTextView.setText(String.valueOf(post.getApprovePostCount()));
                thumbLinearLayout.setOnClickListener(view -> {
                    if (UserUtil.isLogin()) {
                        Requests.addPostApprove(postId, (ThenJson) jsonObject -> {
                            String data = jsonObject.getString("data");
                            String resMsg = jsonObject.getString("msg");
                            Map<String, String> datum = new HashMap<>();
                            datum.put("data", data);
                            datum.put("resMsg", resMsg);
                            handler.sendMessage(MessageUtil.buildDatumMessage(APPROVE_MESSAGE, datum));
                        });
                    } else {
                        ToastUtil.show(PostActivity.this, "请登录！");
                    }
                });

                Glide.with(PostActivity.this)
                        .load(post.getAvatar())
                        .error(R.drawable.avatar)
                        .circleCrop()
                        .into(avatarImageView);

                avatarImageView.setOnClickListener(v -> {
                    Intent intent = new Intent(PostActivity.this, UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("userId", post.getUserId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                });

                contentWebView.loadDataWithBaseURL(null, post.getContent(), "text/html", "utf-8", null);
                openCommentTextView.setOnClickListener(view -> switchCommentLayout(view));
                ListViewUtil.setListViewHeightBasedOnChildren(commentListView);
            } else if (msg.what == COMMENT_SUCCESS_MESSAGE) {
                getData();
                ToastUtil.show(PostActivity.this, "评论成功");
                hideCommentLayout(commentEditText);
            } else if (msg.what == APPROVE_MESSAGE) {
                Bundle bundle = MessageUtil.getBundleByMessage(msg);
                String data = bundle.getString("data");
                String resMsg = bundle.getString("resMsg");
                if (data == null || data.equals("null") || data.equals("0")) {
                    ToastUtil.show(PostActivity.this, resMsg);
                } else {
                    thumbUpImageView.setImageResource(R.drawable.ic_thumb_up_active);
                    approvalTextView.setText(String.valueOf(post.getApprovePostCount() + 1));
                    ToastUtil.show(PostActivity.this, "点赞成功～");
                }
            } else if (msg.what == GET_APPROVE_MESSAGE) {
                if (isApprove) {
                    thumbUpImageView.setImageResource(R.drawable.ic_thumb_up_active);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        Bundle bundle = getIntent().getExtras();
        postId = bundle.getLong("postId");

        findViewById(R.id.toolbar_head).setOnClickListener(v -> finish());

        postNameTextView = findViewById(R.id.tv_post_name);
        userTextView = findViewById(R.id.tv_owner);
        updateDateTextView = findViewById(R.id.tv_update_date);
        commentListView = findViewById(R.id.lv_comment);
        openCommentTextView = findViewById(R.id.btn_open_comment);
        approvalTextView = findViewById(R.id.tv_approval);

        contentWebView = findViewById(R.id.wv_content);

        thumbLinearLayout = findViewById(R.id.ll_thumb);
        avatarImageView = findViewById(R.id.iv_avatar);
        thumbUpImageView = findViewById(R.id.iv_thumb_up);

        commentLayout = findViewById(R.id.rl_comment_layout);
        commentEditText = findViewById(R.id.et_comment);
        View viewLayer = findViewById(R.id.view_layer);
        viewLayer.setOnClickListener(this::switchCommentLayout);

        Button commentButton = findViewById(R.id.btn_comment);
        commentButton.setOnClickListener(view -> {
            if (UserUtil.isLogin()) {
                if (StringUtil.isEmpty(commentEditText.getText().toString())) {
                    ToastUtil.show(PostActivity.this, "评论不能为空！");
                } else {
                    Requests.addComment(post.getPostId(), commentEditText.getText().toString(), (ThenJson) jsonObject -> handler.sendEmptyMessage(COMMENT_SUCCESS_MESSAGE));
                }
            } else {
                ToastUtil.show(PostActivity.this, "请登录！");
            }
        });

        getData();
    }

    private void switchCommentLayout(View view) {
        if (commentLayout.getVisibility() == View.GONE) {
            commentLayout.setVisibility(View.VISIBLE);
            commentEditText.requestFocus();
            InputMethodUtil.show(commentEditText);
        } else {
            hideCommentLayout(view);
        }
    }

    private void hideCommentLayout(View view) {
        commentLayout.setVisibility(View.GONE);
        InputMethodUtil.hide(view);
    }

    private void getData() {
        Requests.getPostByPostId(postId, (ThenJson) jsonObject -> {
            PostDTO post = new PostDTO();
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray commentsJsonArray = data.getJSONArray("comments");

            List<CommentDTO> comments = new ArrayList<>();
            for (int i = 0; i < commentsJsonArray.length(); i++) {
                CommentDTO comment = new CommentDTO();

                JSONObject commentJsonObject = commentsJsonArray.getJSONObject(i);
                comment.setCommentId(commentJsonObject.getLong("cid"));
                comment.setText(commentJsonObject.getString("text"));
                comment.setPostId(commentJsonObject.getLong("postId"));
                comment.setPublishDatetime(DateUtil.format(commentJsonObject.getLong("publishDatetime")));

                JSONObject userJsonObject = commentJsonObject.getJSONObject("user");
                comment.setUsername(userJsonObject.getString("uname"));
                comment.setUserId(userJsonObject.getLong("uid"));
                comment.setAvatar(userJsonObject.getString("avatar"));
                comments.add(comment);
            }

            JSONObject postJsonObject = data.getJSONObject("post");
            post.setPostId(postJsonObject.getLong("postId"));
            post.setPostName(postJsonObject.getString("postName"));
            post.setContent(postJsonObject.getString("content"));
            post.setUpdateDatetime(DateUtil.format(postJsonObject.getLong("updateDatetime")));
            post.setCreateDatetime(DateUtil.format(postJsonObject.getLong("createDatetime")));
            post.setComments(comments);
            post.setApprovePostCount(data.getLong("approvePostCount"));

            JSONObject userJsonObject = postJsonObject.getJSONObject("user");
            post.setUsername(userJsonObject.getString("uname"));
            post.setAvatar(userJsonObject.getString("avatar"));
            post.setUserId(userJsonObject.getLong("uid"));

            PostActivity.this.post = post;

            handler.sendEmptyMessage(UPDATE_POSTS_MESSAGE);
        });

        if (UserUtil.isLogin()) {
            Requests.queryPostApprove(postId, (ThenJson) jsonObject -> {
                String data = jsonObject.getString("data");
                isApprove = !data.equals("null");
                handler.sendEmptyMessage(GET_APPROVE_MESSAGE);
            });
        }
    }
}