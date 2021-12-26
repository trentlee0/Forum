package com.example.forum.ui.user.moderator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.forum.R;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.model.pojo.Plate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyPlateActivity extends AppCompatActivity {

    private static final int DEFAULT_MESSAGE = 0;

    private List<Plate> list;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == DEFAULT_MESSAGE && list != null) {
                ListView lvPlate = findViewById(R.id.lv_plate);
                MyPlateBaseAdapter adapter = new MyPlateBaseAdapter(MyPlateActivity.this, list);
                lvPlate.setAdapter(adapter);
                lvPlate.setOnItemClickListener(adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plate);

        Toolbar toolbar = findViewById(R.id.toolbar_head);
        toolbar.setNavigationOnClickListener(v -> finish());

        Requests.getUserPlates((ThenJson) jsonObject -> {
            JSONArray dataArr = jsonObject.getJSONObject("data").getJSONArray("list");

            List<Plate> list = new ArrayList<>();
            for (int i = 0; i < dataArr.length(); i++) {
                JSONObject ob = dataArr.getJSONObject(i);
                Plate plate = new Plate();
                plate.setName(ob.getString("pname"));
                plate.setUserId(ob.getLong("uid"));
                plate.setPlateId(ob.getLong("pid"));
                list.add(plate);
            }
            MyPlateActivity.this.list = list;
            handler.sendEmptyMessage(DEFAULT_MESSAGE);
        });
    }
}