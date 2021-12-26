package com.example.forum.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.util.MessageUtil;
import com.example.forum.util.StringUtil;
import com.example.forum.util.ToastUtil;

public class RegisterActivity extends AppCompatActivity {

    private static final int DEFAULT_MESSAGE = 0;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText checkPasswordEditText;
    private TextView textView;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE) {
                ToastUtil.show(RegisterActivity.this, MessageUtil.getStringDataByMessage(msg));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.et_register_username);
        passwordEditText = findViewById(R.id.et_register_password);
        checkPasswordEditText = findViewById(R.id.et_register_check_password);
        textView = findViewById(R.id.btn_register);

        Toolbar toolbar = findViewById(R.id.toolbar_head);
        toolbar.setNavigationOnClickListener(v -> finish());

        textView.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String firstPassword = passwordEditText.getText().toString();
            String secondPassword = checkPasswordEditText.getText().toString();

            if (StringUtil.isEmpty(username, firstPassword, secondPassword)) {
                ToastUtil.show(RegisterActivity.this, "用户名或密码为空！");
            } else {
                if (firstPassword.equals(secondPassword)) {
                    Requests.register(username, firstPassword, (ThenJson) jsonObject -> handler.sendMessage(MessageUtil.buildDatumMessage(DEFAULT_MESSAGE, jsonObject.getString("msg"))));
                } else {
                    ToastUtil.show(RegisterActivity.this, "两次输入的密码不一致！");
                }
            }
        });
    }
}