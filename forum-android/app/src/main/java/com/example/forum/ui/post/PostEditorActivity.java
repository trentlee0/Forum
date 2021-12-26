package com.example.forum.ui.post;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.dto.ImageDTO;
import com.example.forum.model.dto.VideoDTO;
import com.example.forum.model.pojo.Plate;
import com.example.forum.util.FileUtil;
import com.example.forum.util.JsonUtil;
import com.example.forum.util.LogUtil;
import com.example.forum.util.MessageUtil;
import com.example.forum.util.PermissionUtil;
import com.example.forum.util.StringUtil;
import com.example.forum.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class PostEditorActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_READ_WRITE_EXTERNAL_STORAGE = 101;
    private static final int REQUEST_PICK_VIDEO = 102;
    private static final int REQUEST_VIDEO_READ_WRITE_EXTERNAL_STORAGE = 103;

    private static final int UPLOAD_IMAGE_SUCCESS = 0;
    private static final int UPLOAD_IMAGE_FAILURE = 1;
    private static final int UPLOAD_VIDEO_SUCCESS = 2;
    private static final int UPLOAD_VIDEO_FAILURE = 3;
    private static final int GET_ALL_PLATES = 4;
    private static final int ADD_POST = 5;

    private RichEditor mEditor;

    private EditText mPostTitleEditText;
    private Spinner mSpinner;
    private List<Plate> mList;
    private Plate mSelectedPlate;

    private final String[] mPermissionList = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPLOAD_IMAGE_SUCCESS) {
                String data = MessageUtil.getStringDataByMessage(msg);
                ImageDTO imageDTO = JsonUtil.getObjectFromJson(data, ImageDTO.class);
                mEditor.insertImage(imageDTO.getUrl(), imageDTO.getAlt() + "\" style=\"max-width:100%");
            } else if (msg.what == UPLOAD_IMAGE_FAILURE) {
                ToastUtil.show(PostEditorActivity.this, "图片上传失败");
            } else if (msg.what == GET_ALL_PLATES && mList != null) {
                String[] strings = new String[mList.size()];
                for (int i = 0; i < mList.size(); i++) {
                    strings[i] = mList.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PostEditorActivity.this, R.layout.plate_item_select, strings);
                mSpinner.setAdapter(adapter);
                mSpinner.setPrompt("请选择板块");
                mSpinner.setSelection(0);
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mSelectedPlate = mList.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            } else if (msg.what == ADD_POST) {
                Bundle bundle = msg.getData();
                String resMsg = bundle.getString("resMsg");
                int i = bundle.getInt("i");
                ToastUtil.show(PostEditorActivity.this, resMsg);
                if (i != 0) finish();
            } else if (msg.what == UPLOAD_VIDEO_SUCCESS) {
                String data = MessageUtil.getStringDataByMessage(msg);
                VideoDTO videoDTO = JsonUtil.getObjectFromJson(data, VideoDTO.class);
                mEditor.insertVideo(videoDTO.getUrl(), 360);
            } else if (msg.what == UPLOAD_VIDEO_FAILURE) {
                ToastUtil.show(PostEditorActivity.this, "视频上传失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editor);

        Toolbar toolbar = findViewById(R.id.toolbar_head);

        mPostTitleEditText = findViewById(R.id.et_post_title);
        mEditor = findViewById(R.id.editor);
        mSpinner = findViewById(R.id.spinner_plate);

        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_release) {
                String title = mPostTitleEditText.getText().toString();
                String content = mEditor.getHtml();
                if (StringUtil.isEmpty(title, content)) {
                    ToastUtil.show(PostEditorActivity.this, "贴名或正文不能为空！");
                } else {
                    Requests.addPost(mSelectedPlate.getPlateId(), title, content, (ThenJson) jsonObject -> {
                        String msg = jsonObject.getString("msg");
                        Message message = new Message();
                        message.what = ADD_POST;
                        int data = jsonObject.getInt("data");
                        Bundle bundle = new Bundle();
                        bundle.putString("resMsg", msg);
                        bundle.putInt("i", data);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    });
                }
            }
            return true;
        });

        Requests.getAllPlates((ThenJson) jsonObject -> {
            JSONArray dataArr = jsonObject.getJSONArray("data");
            List<Plate> list = new ArrayList<>();
            for (int i = 0; i < dataArr.length(); i++) {
                JSONObject ob = dataArr.getJSONObject(i);
                Plate plate = new Plate();
                plate.setName(ob.getString("pname"));
                plate.setUserId(ob.getLong("uid"));
                plate.setPlateId(ob.getLong("pid"));
                plate.setPostCount(ob.getInt("count"));
                list.add(plate);
            }
            PostEditorActivity.this.mList = list;
            mHandler.sendEmptyMessage(GET_ALL_PLATES);
        });

        initEditor();
    }

    private void initEditor() {
        mEditor.setEditorFontSize(18);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setInputEnabled(true);
        mEditor.setPlaceholder("帖子内容");

        LinearLayout linearLayout = findViewById(R.id.ll_editor_tool_bar);
        mPostTitleEditText.requestFocus();
        linearLayout.setVisibility(View.GONE);
        mEditor.setOnFocusChangeListener((view, b) -> {
            if (b) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        });

        LinearLayout editorTextToolLayout = findViewById(R.id.ll_editor_text_tool);
        findViewById(R.id.show_font_action).setOnClickListener(v -> {
            if (editorTextToolLayout.getVisibility() == View.VISIBLE) {
                editorTextToolLayout.setVisibility(View.GONE);
            } else {
                editorTextToolLayout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setBold();
        });

        findViewById(R.id.action_italic).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setItalic();
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setStrikeThrough();
        });

        findViewById(R.id.action_underline).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setUnderline();
        });

        findViewById(R.id.action_heading1).setOnClickListener(v -> mEditor.setHeading(1));
        findViewById(R.id.action_heading2).setOnClickListener(v -> mEditor.setHeading(2));
        findViewById(R.id.action_heading3).setOnClickListener(v -> mEditor.setHeading(3));
        findViewById(R.id.action_heading4).setOnClickListener(v -> mEditor.setHeading(4));
        findViewById(R.id.action_heading5).setOnClickListener(v -> mEditor.setHeading(5));

        findViewById(R.id.action_txt_color).setOnClickListener(v -> {
            mEditor.focusEditor();
            new MaterialDialog.Builder(PostEditorActivity.this)
                    .title("选择字体颜色")
                    .items(R.array.color_items)
                    .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                mEditor.setTextColor(Color.RED);
                                break;
                            case 1:
                                mEditor.setTextColor(Color.YELLOW);
                                break;
                            case 2:
                                mEditor.setTextColor(Color.GREEN);
                                break;
                            case 3:
                                mEditor.setTextColor(Color.BLUE);
                                break;
                            case 4:
                                mEditor.setTextColor(Color.BLACK);
                                break;
                        }
                        return false;
                    }).show();
        });

        findViewById(R.id.action_bg_color).setOnClickListener(v -> {
            mEditor.focusEditor();
            new MaterialDialog.Builder(PostEditorActivity.this)
                    .title("选择字体背景颜色")
                    .items(R.array.text_back_color_items)
                    .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                mEditor.setTextBackgroundColor(Color.RED);
                                break;
                            case 1:
                                mEditor.setTextBackgroundColor(Color.YELLOW);
                                break;
                            case 2:
                                mEditor.setTextBackgroundColor(Color.GREEN);
                                break;
                            case 3:
                                mEditor.setTextBackgroundColor(Color.BLUE);
                                break;
                            case 4:
                                mEditor.setTextBackgroundColor(Color.BLACK);
                                break;
                            case 5:
                                mEditor.setTextBackgroundColor(R.color.transparent);
                                break;
                        }
                        return false;
                    }).show();
        });

        findViewById(R.id.action_indent).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setIndent();
        });

        findViewById(R.id.action_outdent).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setOutdent();
        });

        findViewById(R.id.action_align_left).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.setAlignLeft();
        });

        findViewById(R.id.action_align_center).setOnClickListener(v -> mEditor.setAlignCenter());

        findViewById(R.id.action_align_right).setOnClickListener(v -> mEditor.setAlignRight());

        findViewById(R.id.action_insert_bullets).setOnClickListener(v -> mEditor.setBullets());

        findViewById(R.id.action_insert_numbers).setOnClickListener(v -> mEditor.setNumbers());

        findViewById(R.id.action_blockquote).setOnClickListener(v -> mEditor.setBlockquote());

        findViewById(R.id.action_insert_image).setOnClickListener(v -> {
            mEditor.focusEditor();
            ActivityCompat.requestPermissions(PostEditorActivity.this, mPermissionList, REQUEST_IMAGE_READ_WRITE_EXTERNAL_STORAGE);
        });
        findViewById(R.id.action_insert_video).setOnClickListener(v -> {
            mEditor.focusEditor();
            ActivityCompat.requestPermissions(PostEditorActivity.this, mPermissionList, REQUEST_VIDEO_READ_WRITE_EXTERNAL_STORAGE);
        });

        findViewById(R.id.action_insert_link).setOnClickListener(v -> {
            mEditor.focusEditor();
            LinearLayout layout = new LinearLayout(PostEditorActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            EditText titleEditText = new EditText(PostEditorActivity.this);
            EditText hrefEditText = new EditText(PostEditorActivity.this);
            titleEditText.setHint("标题");
            hrefEditText.setHint("链接");
            layout.addView(titleEditText);
            layout.addView(hrefEditText);
            AlertDialog alertDialog = new AlertDialog.Builder(PostEditorActivity.this)
                    .setTitle("请输入链接")
                    .setView(layout)
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                        String href = hrefEditText.getText().toString();
                        String title = titleEditText.getText().toString();
                        mEditor.insertLink(href, title);
                    })
                    .setNegativeButton("取消", null)
                    .create();
            alertDialog.show();
        });

        findViewById(R.id.action_insert_checkbox).setOnClickListener(v -> {
            mEditor.focusEditor();
            mEditor.insertTodo();
        });
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
        } else if (requestCode == REQUEST_VIDEO_READ_WRITE_EXTERNAL_STORAGE) {
            if (PermissionUtil.checkGranted(grantResults)) {
                getVideo();
            } else {
                ToastUtil.show(this, "需要获取读写存储卡权限，才能获取视频！");
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                if (data != null) {
                    String realPath = FileUtil.getPath(this, data.getData());
                    MaterialDialog dialog = new MaterialDialog.Builder(PostEditorActivity.this)
                            .title("上传中...")
                            .cancelable(false)
                            .customView(new ProgressBar(PostEditorActivity.this), false)
                            .build();
                    dialog.show();
                    Requests.uploadImage(realPath, (ThenJson) jsonObject -> {
                        int errno = jsonObject.getInt("errno");
                        if (errno == 0) {
                            String response = jsonObject.getJSONArray("data").getJSONObject(0).toString();
                            mHandler.sendMessage(MessageUtil.buildDatumMessage(UPLOAD_IMAGE_SUCCESS, response));
                            dialog.dismiss();
                        } else {
                            mHandler.sendEmptyMessage(UPLOAD_IMAGE_FAILURE);
                        }
                    });
                } else {
                    ToastUtil.show(this, "图片损坏，请重新选择");
                }
            } else if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    String realPath = FileUtil.getPath(this, data.getData());
                    MaterialDialog dialog = new MaterialDialog.Builder(PostEditorActivity.this)
                            .title("上传中...")
                            .cancelable(false)
                            .customView(new ProgressBar(PostEditorActivity.this), false)
                            .build();
                    dialog.show();
                    Requests.uploadVideo(realPath, (ThenJson) jsonObject -> {
                        int errno = jsonObject.getInt("errno");
                        if (errno == 0) {
                            String response = jsonObject.getJSONObject("data").toString();
                            mHandler.sendMessage(MessageUtil.buildDatumMessage(UPLOAD_VIDEO_SUCCESS, response));
                            dialog.dismiss();
                        } else {
                            mHandler.sendEmptyMessage(UPLOAD_VIDEO_FAILURE);
                        }
                    });
                } else {
                    ToastUtil.show(this, "视频损坏，请重新选择");
                }
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    private void getVideo() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_PICK_VIDEO);
    }
}