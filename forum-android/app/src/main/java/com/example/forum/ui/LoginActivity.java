package com.example.forum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.forum.MyApplication;
import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.dao.UserDao;
import com.example.forum.model.pojo.User;
import com.example.forum.util.DateUtil;
import com.example.forum.util.JsonUtil;
import com.example.forum.util.MessageUtil;
import com.example.forum.util.ToastUtil;
import com.example.forum.util.UserUtil;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private TextView loginTextView;
    private TextView toRegisterTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private static final int DEFAULT_MESSAGE = 0;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE) {
                ToastUtil.show(LoginActivity.this, MessageUtil.getStringDataByMessage(msg));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTextView = findViewById(R.id.btn_login);
        toRegisterTextView = findViewById(R.id.btn_to_register);
        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);

        Toolbar toolbar = findViewById(R.id.toolbar_head);
        toolbar.setNavigationOnClickListener(v -> finish());

        toRegisterTextView.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        loginTextView.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Requests.login(username, password, (ThenJson) jsonObject -> {
                Message message = MessageUtil.buildDatumMessage(DEFAULT_MESSAGE, jsonObject.getString("msg"));
                JSONObject data = JsonUtil.getJSONObject(jsonObject, "data");
                if (data != null) {
                    JSONObject userData = data.getJSONObject("user");
                    User user = new User();
                    user.setName(JsonUtil.getString(userData, "uname"));
                    user.setId(JsonUtil.getLong(userData, "uid"));
                    user.setAvatar(JsonUtil.getString(userData, "avatar"));
                    user.setGender((byte) JsonUtil.getInt(userData, "ugender"));
                    user.setGrade((byte) JsonUtil.getInt(userData, "ugrade"));
                    user.setBirthday(DateUtil.format(JsonUtil.getLong(userData, "ubirthday"), "yyyy-MM-dd"));
                    user.setToken(JsonUtil.getString(data, "token"));

                    UserUtil.saveLoginUser(user);
                    handler.sendMessage(message);
                    finish();
                } else {
                    handler.sendMessage(message);
                }
            });
        });
    }
}