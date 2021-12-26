package com.example.forum.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.forum.MyApplication;
import com.example.forum.R;
import com.example.forum.adapter.PlateBaseAdapter;
import com.example.forum.request.Requests;
import com.example.forum.request.callback.ThenJson;
import com.example.forum.dao.PlateDao;
import com.example.forum.model.pojo.Plate;
import com.example.forum.adapter.FooterHolder;
import com.example.forum.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlateFragment extends Fragment {

    private static final int UPDATE_PLATES = 0;

    private List<Plate> list;

    private ListView lvPlate;

    private FooterHolder footerHolder;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_PLATES && list != null) {
                PlateBaseAdapter adapter = new PlateBaseAdapter(getContext(), list);
                lvPlate.setAdapter(adapter);
                lvPlate.setOnItemClickListener(adapter);
                footerHolder.changeStatus(FooterHolder.FooterStatus.NORMAL);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plate, container, false);

        lvPlate = root.findViewById(R.id.lv_plate);

        footerHolder = new FooterHolder(root.findViewById(R.id.footer));

        if (NetworkUtil.isConnected(root.getContext())) {
            getNetworkData();
        } else {
            getLocalData();
            footerHolder.changeStatus(FooterHolder.FooterStatus.ERROR);
        }

        return root;
    }

    private void getLocalData() {
        PlateDao plateDao = MyApplication.getInstance().getPlateDatabase().plateDao();
        list = plateDao.queryAll();
        handler.sendEmptyMessage(UPDATE_PLATES);
    }

    private void getNetworkData() {
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
            PlateFragment.this.list = list;

            PlateDao plateDao = MyApplication.getInstance().getPlateDatabase().plateDao();
            plateDao.deleteAll();
            list.forEach(plateDao::insert);

            handler.sendEmptyMessage(UPDATE_PLATES);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}