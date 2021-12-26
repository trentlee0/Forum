package com.example.forum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.forum.MyApplication;
import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ResponseResult;
import com.example.forum.ui.user.UpdateUserActivity;
import com.example.forum.util.ToastUtil;
import com.example.forum.util.UserUtil;

import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    private static final int LOGOUT_SUCCESS = 0;
    private static final int LOGOUT_FAILURE = 1;

    private Toolbar toolbar;
    private TextView logoutButton;
    private TextView updateUserButton;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOGOUT_SUCCESS) {
                UserUtil.removeLogoutUser();
                ToastUtil.show(SettingActivity.this, "注销成功！");
                finish();
            } else if (msg.what == LOGOUT_FAILURE) {
                ToastUtil.show(SettingActivity.this, "注销失败！");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logoutButton = findViewById(R.id.btn_logout);
        updateUserButton = findViewById(R.id.btn_update_user);
        toolbar = findViewById(R.id.toolbar_head);

        toolbar.setNavigationOnClickListener(v -> finish());

        logoutButton.setOnClickListener(v -> Requests.logout(new ResponseResult() {
            @Override
            public void thenResult(String resp) {
                handler.sendEmptyMessage(LOGOUT_SUCCESS);
            }

            @Override
            public void catchResult(IOException e) {
                handler.sendEmptyMessage(LOGOUT_FAILURE);
            }
        }));

        updateUserButton.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, UpdateUserActivity.class)));
    }
}