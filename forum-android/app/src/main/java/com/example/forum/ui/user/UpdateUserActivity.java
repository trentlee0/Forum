package com.example.forum.ui.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.forum.MyApplication;
import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ResponseResult;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.ImageDTO;
import com.example.forum.model.pojo.User;
import com.example.forum.util.DateUtil;
import com.example.forum.util.FileUtil;
import com.example.forum.util.JsonUtil;
import com.example.forum.util.MessageUtil;
import com.example.forum.util.PermissionUtil;
import com.example.forum.util.ToastUtil;
import com.example.forum.util.UserUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class UpdateUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_READ_WRITE_EXTERNAL_STORAGE = 101;

    private static final int UPLOAD_IMAGE_SUCCESS = 0;
    private static final int UPLOAD_IMAGE_FAILURE = 1;

    private static final int UPDATE_USER_SUCCESS = 2;
    private static final int UPDATE_USER_FAILURE = 3;

    private Toolbar toolbar;

    private ImageView avatarImageView;
    private EditText usernameEditText;
    private RadioGroup genderRadioGroup;
    private TextView birthdayTextView;

    private String avatar;

    private String[] permissionlist = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPLOAD_IMAGE_SUCCESS) {
                String data = MessageUtil.getStringDataByMessage(msg);
                ImageDTO imageDTO = JsonUtil.getObjectFromJson(data, ImageDTO.class);
                avatar = imageDTO.getUrl();
                Glide.with(UpdateUserActivity.this)
                        .load(avatar)
                        .error(R.drawable.avatar)
                        .circleCrop()
                        .into(avatarImageView);
            } else if (msg.what == UPLOAD_IMAGE_FAILURE) {
                ToastUtil.show(UpdateUserActivity.this, "图片上传失败");
            } else if (msg.what == UPDATE_USER_SUCCESS) {
                ToastUtil.show(UpdateUserActivity.this, "修改成功！");
                finish();
            } else if (msg.what == UPDATE_USER_FAILURE) {
                ToastUtil.show(UpdateUserActivity.this, "修改失败～");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        toolbar = findViewById(R.id.toolbar_head);
        avatarImageView = findViewById(R.id.iv_avatar);
        usernameEditText = findViewById(R.id.et_username);
        genderRadioGroup = findViewById(R.id.rg_gender);
        birthdayTextView = findViewById(R.id.tv_birthday);

        toolbar.setNavigationOnClickListener(v -> finish());

        if (UserUtil.isLogin()) {
            initView();
        }
    }

    private void initView() {
        User user = UserUtil.currentUser();
        avatar = user.getAvatar();
        Glide.with(this)
                .load(user.getAvatar())
                .error(R.drawable.avatar)
                .circleCrop()
                .into(avatarImageView);
        usernameEditText.setText(user.getName());
        genderRadioGroup.check(getGenderResId(user.getGender()));
        birthdayTextView.setText(user.getBirthday());

        avatarImageView.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(UpdateUserActivity.this, permissionlist, REQUEST_IMAGE_READ_WRITE_EXTERNAL_STORAGE);
        });

        birthdayTextView.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            Date date = DateUtil.parse(birthdayTextView.getText().toString(), "yyyy-MM-dd");
            calendar.setTime(date);
            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_save) {
                updateData();
            }
            return true;
        });
    }

    private void updateData() {
        String username = usernameEditText.getText().toString();
        byte gender = getResIdGender(genderRadioGroup.getCheckedRadioButtonId());
        long birthday = DateUtil.parseToLong(birthdayTextView.getText().toString(), "yyyy-MM-dd");
        Requests.updateUser(username, gender, birthday, avatar, new ResponseResult() {
            @Override
            public void thenResult(String resp) {
                User user = UserUtil.currentUser();
                user.setName(username);
                user.setGender(gender);
                user.setBirthday(DateUtil.format(birthday));
                user.setAvatar(avatar);
                handler.sendEmptyMessage(UPDATE_USER_SUCCESS);
            }

            @Override
            public void catchResult(IOException e) {
                handler.sendEmptyMessage(UPDATE_USER_FAILURE);
            }
        });
    }

    private int getGenderResId(byte gender) {
        return gender == 0 ? R.id.rb_gender_man : R.id.rb_gender_woman;
    }

    private byte getResIdGender(int resId) {
        return (byte) (resId == R.id.rb_gender_man ? 0 : 1);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        birthdayTextView.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                getImageData(data);
            }
        }
    }

    private void getImageData(Intent data) {
        if (data != null) {
            String realPath = FileUtil.getPath(this, data.getData());
            AlertDialog dialog = new AlertDialog.Builder(UpdateUserActivity.this)
                    .setTitle("上传中...")
                    .setCancelable(false)
                    .setView(new ProgressBar(UpdateUserActivity.this))
                    .create();
            dialog.show();
            Requests.uploadImage(realPath, (ThenJson) jsonObject -> {
                if (jsonObject.getInt("errno") == 0) {
                    String response = jsonObject.getJSONArray("data").getJSONObject(0).toString();
                    handler.sendMessage(MessageUtil.buildDatumMessage(UPLOAD_IMAGE_SUCCESS, response));
                    dialog.dismiss();
                } else {
                    handler.sendEmptyMessage(UPLOAD_IMAGE_FAILURE);
                }
            });
        } else {
            ToastUtil.show(this, "图片损坏，请重新选择");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_READ_WRITE_EXTERNAL_STORAGE) {
            if (PermissionUtil.checkGranted(grantResults)) {
                getImage();
            } else {
                ToastUtil.show(this, "需要获取读写存储卡权限，才能获取图片！");
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }
}