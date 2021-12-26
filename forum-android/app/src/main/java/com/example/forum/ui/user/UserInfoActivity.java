package com.example.forum.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.UserDTO;
import com.example.forum.util.DateUtil;
import com.example.forum.util.UserUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private static final int UPDATE_USER_INFO_MESSAGE = 0;

    private ImageView avatarBgImageView;
    private ImageView avatarIconImageView;
    private TextView usernameTextView;
    private TextView birthdayTextView;
    private TextView genderTextView;
    private TextView gradeTextView;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private long userId;
    private UserDTO userDTO;

    private List<Fragment> tabs = new ArrayList<>();
    private String[] titles = {"帖子"};

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_USER_INFO_MESSAGE) {
                Glide.with(UserInfoActivity.this)
                        .load(userDTO.getAvatar())
                        .centerCrop()
                        .error(R.drawable.avatar)
                        .into(avatarBgImageView);
                Glide.with(UserInfoActivity.this)
                        .load(userDTO.getAvatar())
                        .circleCrop()
                        .error(R.drawable.avatar)
                        .into(avatarIconImageView);
                usernameTextView.setText(userDTO.getUname());
                birthdayTextView.setText(userDTO.getUbirthday());
                genderTextView.setText(userDTO.getUgender());
                gradeTextView.setText(userDTO.getUgrade());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        userId = getIntent().getExtras().getLong("userId");

        Toolbar toolbar = findViewById(R.id.toolbar_head);
        toolbar.setNavigationOnClickListener(v -> finish());

        avatarBgImageView = findViewById(R.id.iv_avatar_bg);
        avatarIconImageView = findViewById(R.id.iv_avatar_icon);
        usernameTextView = findViewById(R.id.tv_username);
        birthdayTextView = findViewById(R.id.tv_birthday);
        genderTextView = findViewById(R.id.tv_gender);
        gradeTextView = findViewById(R.id.tv_ugrade);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new PagerAdapter(this));
        tabs.add(UserPostFragment.newInstance(userId));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(titles[position])).attach();

        getData();
    }

    private void getData() {
        Requests.getUser(userId, (ThenJson) jsonObject -> {
            JSONObject data = jsonObject.getJSONObject("data");
            UserDTO userDTO = new UserDTO();
            userDTO.setAvatar(data.getString("avatar"));
            userDTO.setUid(data.getLong("uid"));
            userDTO.setUname(data.getString("uname"));
            userDTO.setUbirthday(DateUtil.format(data.getLong("ubirthday"), "yyyy-MM-dd"));
            userDTO.setUgender(UserUtil.genderToString((byte) data.getInt("ugender")));
            userDTO.setUgrade(UserUtil.getUserGrade((byte) data.getInt("ugrade")));

            this.userDTO = userDTO;

            handler.sendEmptyMessage(UPDATE_USER_INFO_MESSAGE);
        });
    }

    public class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return tabs.get(position);
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }
    }
}