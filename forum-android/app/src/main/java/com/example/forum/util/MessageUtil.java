package com.example.forum.util;

import android.os.Bundle;
import android.os.Message;

import java.util.Map;

public class MessageUtil {
    private static final String DATA_KEY = "data";

    public static Message buildDatumMessage(int what, String data) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(DATA_KEY, data);
        message.setData(bundle);
        return message;
    }

    public static String getStringDataByMessage(Message message) {
        return message.getData().getString(DATA_KEY);
    }

    public static Message buildDatumMessage(int what, Map<String, String> datum) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        datum.forEach(bundle::putString);
        message.setData(bundle);
        return message;
    }

    public static Bundle getBundleByMessage(Message message) {
        return message.getData();
    }
}
