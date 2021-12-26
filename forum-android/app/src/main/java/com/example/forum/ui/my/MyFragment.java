package com.example.forum.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.forum.MyApplication;
import com.example.forum.R;
import com.example.forum.databinding.FragmentMyBinding;
import com.example.forum.model.pojo.User;
import com.example.forum.ui.LoginActivity;
import com.example.forum.ui.SettingActivity;
import com.example.forum.ui.user.MyPostActivity;
import com.example.forum.ui.user.UserInfoActivity;
import com.example.forum.ui.user.moderator.MyPlateActivity;
import com.example.forum.util.DateUtil;
import com.example.forum.util.UserUtil;

public class MyFragment extends Fragment {
    private FragmentMyBinding binding;

    private TextView toLoginButton;
    private TextView settingButton;

    private TextView myPostTextView;
    private TextView myPlateTextView;

    private TextView usernameTextView;
    private TextView genderTextView;
    private TextView birthdayTextView;
    private TextView userGradeTextView;
    private ImageView avatarImageView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        toLoginButton = root.findViewById(R.id.btn_to_login);
        settingButton = root.findViewById(R.id.btn_to_setting);

        myPostTextView = root.findViewById(R.id.btn_my_post);
        myPlateTextView = root.findViewById(R.id.btn_my_plate);

        usernameTextView = root.findViewById(R.id.tv_username);
        genderTextView = root.findViewById(R.id.tv_gender);
        birthdayTextView = root.findViewById(R.id.tv_birthday);
        userGradeTextView = root.findViewById(R.id.tv_grade);
        avatarImageView = root.findViewById(R.id.iv_avatar);

        root.findViewById(R.id.ll_user_info).setOnClickListener(view -> {
            if (UserUtil.isLogin()) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("userId", UserUtil.currentUser().getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        settingButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), SettingActivity.class)));

        return root;
    }

    @Override
    public void onResume() {
        if (!UserUtil.isLogin()) {
            logoutStatus();
        } else {
            loginStatus();
        }
        super.onResume();
    }

    private void logoutStatus() {
        toLoginButton.setVisibility(View.VISIBLE);
        settingButton.setVisibility(View.GONE);
        myPostTextView.setVisibility(View.GONE);
        myPlateTextView.setVisibility(View.GONE);

        usernameTextView.setText("游客");
        genderTextView.setText("未知");
        birthdayTextView.setText(DateUtil.now("yyyy-MM-dd"));
        userGradeTextView.setText(UserUtil.getUserGrade((byte) 0));
        avatarImageView.setImageResource(R.drawable.avatar);

        toLoginButton.setOnClickListener(v -> startActivity(new Intent(getContext(), LoginActivity.class)));
    }

    private void loginStatus() {
        toLoginButton.setVisibility(View.GONE);
        settingButton.setVisibility(View.VISIBLE);

        myPostTextView.setVisibility(View.VISIBLE);
        if (UserUtil.isModeratorUser()) {
            myPlateTextView.setVisibility(View.VISIBLE);
        } else {
            myPlateTextView.setVisibility(View.GONE);
        }

        User user = UserUtil.currentUser();
        usernameTextView.setText(user.getName());
        genderTextView.setText(UserUtil.genderToString(user.getGender()));
        birthdayTextView.setText(user.getBirthday());
        userGradeTextView.setText(UserUtil.getUserGrade(user.getGrade()));
        Glide.with(getContext())
                .load(user.getAvatar())
                .error(R.drawable.avatar)
                .circleCrop()
                .into(avatarImageView);

        myPostTextView.setOnClickListener(v -> startActivity(new Intent(getContext(), MyPostActivity.class)));

        myPlateTextView.setOnClickListener(v -> startActivity(new Intent(getContext(), MyPlateActivity.class)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}