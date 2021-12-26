package com.example.forum.util;

import com.example.forum.request.callback.AbstractCallback;
import com.example.forum.request.callback.ResponseResult;

import java.io.File;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static final String baseURL;
    private static final OkHttpClient client;

    static {
        baseURL = "http://192.168.1.113:8089";
        client = new OkHttpClient();
    }

    public static void get(String url, ResponseResult responseResult) {
        Request request = new Request.Builder()
                .get()
                .url(baseURL + url)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    public static void post(String url, Map<String, String> params, ResponseResult responseResult) {
        Request request = new Request.Builder()
                .post(buildParams(params))
                .url(baseURL + url)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    public static void delete(String url, Map<String, String> params, ResponseResult responseResult) {
        Request request = new Request.Builder()
                .delete(buildParams(params))
                .url(baseURL + url)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    public static void delete(String url, ResponseResult responseResult) {
        Request request = new Request.Builder()
                .delete()
                .url(baseURL + url)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    public static void patch(String url, Map<String, String> params, ResponseResult responseResult) {
        Request request = new Request.Builder()
                .patch(buildParams(params))
                .url(baseURL + url)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    private static RequestBody buildParams(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::add);
        return builder.build();
    }

    public static void uploadFile(String url, File file, ResponseResult responseResult) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();


        Request request = new Request.Builder()
                .url(baseURL + url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(transformToCallback(responseResult));
    }

    public static Callback transformToCallback(ResponseResult responseResult) {
        return new AbstractCallback(responseResult);
    }
}
