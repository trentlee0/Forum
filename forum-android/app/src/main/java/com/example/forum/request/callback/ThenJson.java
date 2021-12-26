package com.example.forum.request.callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@FunctionalInterface
public interface ThenJson extends ResponseResult {
    @Override
    default void thenResult(String resp) {
        try {
            JSONObject jsonObject = new JSONObject(resp);
            thenResult(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void thenResult(JSONObject jsonObject) throws JSONException;

    @Override
    default void catchResult(IOException e) {
    }
}
