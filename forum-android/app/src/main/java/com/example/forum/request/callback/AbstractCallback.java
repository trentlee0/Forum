package com.example.forum.request.callback;

import androidx.annotation.NonNull;

import com.example.forum.util.LogUtil;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AbstractCallback implements Callback {

    private ResponseResult responseResult;

    public AbstractCallback() {
    }

    public AbstractCallback(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        LogUtil.log(e.toString());
        responseResult.catchResult(e);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        String string = Objects.requireNonNull(response.body()).string();
        LogUtil.log(string);
        responseResult.thenResult(string);
    }
}
