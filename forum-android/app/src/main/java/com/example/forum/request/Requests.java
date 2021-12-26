package com.example.forum.request;


import android.annotation.SuppressLint;

import com.example.forum.MyApplication;
import com.example.forum.request.callback.ResponseResult;
import com.example.forum.model.pojo.User;
import com.example.forum.util.HttpUtil;
import com.example.forum.util.UserUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("DefaultLocale")
public class Requests {
    public static void login(String username, String password, ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        HttpUtil.post("/login", params, responseResult);
    }

    public static void logout(ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        params.put("token", UserUtil.currentToken());
        HttpUtil.post("/logout", params, responseResult);
    }

    public static void register(String username, String password, ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        HttpUtil.post("/register", params, responseResult);
    }

    public static void getAllPlates(ResponseResult responseResult) {
        HttpUtil.get("/plates", responseResult);
    }

    public static void getUser(long uId, ResponseResult responseResult) {
        HttpUtil.get("/users/" + uId, responseResult);
    }

    public static void getAllPosts(int pageNum, int pageSize, ResponseResult responseResult) {
        HttpUtil.get(String.format("/posts?pageNum=%d&pageSize=%d", pageNum, pageSize), responseResult);
    }

    public static void getPostsByPlateId(long plateId, int pageNum, int pageSize, ResponseResult responseResult) {
        HttpUtil.get(String.format("/plates/get?pId=%d&pageNum=%d&pageSize=%d", plateId, pageNum, pageSize), responseResult);
    }

    public static void getPostByPostId(long postId, ResponseResult responseResult) {
        HttpUtil.get("/posts/" + postId, responseResult);
    }

    public static void updateUser(String username, int gender, long birthday, String avatar, ResponseResult responseResult) {
        HttpUtil.patch(
                String.format("/user?token=%s&uName=%s&uGender=%d&uBirthday=%d&avatar=%s", UserUtil.currentToken(), username, gender, birthday, avatar),
                new HashMap<>(),
                responseResult);
    }

    public static void addPost(long plateId, String postName, String content, ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        User user = UserUtil.currentUser();
        params.put("token", UserUtil.currentToken());
        params.put("uId", user.getId() + "");
        params.put("plateId", plateId + "");
        params.put("postName", postName);
        params.put("content", content);
        HttpUtil.post("/posts", params, responseResult);
    }

    public static void addComment(long postId, String text, ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        params.put("token", UserUtil.currentToken());
        params.put("postId", postId + "");
        params.put("uId", UserUtil.currentUser().getId() + "");
        params.put("text", text);
        HttpUtil.post("/comments", params, responseResult);
    }

    public static void searchPosts(String q, int pageNum, int pageSize, ResponseResult responseResult) {
        HttpUtil.get(String.format("/posts/search?q=%s&pageNum=%d&pageNum=%d", q, pageNum, pageSize), responseResult);
    }

    public static void getUserPosts(int pageNum, int pageSize, ResponseResult responseResult) {
        HttpUtil.get(String.format("/posts/get?token=%s&pageNum=%d&pageSize=%d", UserUtil.currentToken(), pageNum, pageSize), responseResult);
    }

    public static void getPostsByUser(long uId, ResponseResult responseResult) {
        HttpUtil.get("/posts/user/" + uId, responseResult);
    }

    public static void deletePostByPostId(long postId, ResponseResult responseResult) {
        HttpUtil.delete(String.format("/posts?token=%s&postId=%d", UserUtil.currentToken(), postId), responseResult);
    }

    public static void getUserPlates(int pageNum, int pageSize, ResponseResult responseResult) {
        HttpUtil.get(String.format("/plates/user?token=%s&pageNum=%d&pageSize=%d", UserUtil.currentToken(), pageNum, pageSize), responseResult);
    }

    public static void getUserPlates(ResponseResult responseResult) {
        getUserPlates(1, Integer.MAX_VALUE, responseResult);
    }

    public static void addPostApprove(long postId, ResponseResult responseResult) {
        Map<String, String> params = new HashMap<>();
        params.put("uId", UserUtil.currentUser().getId() + "");
        params.put("postId", postId + "");
        params.put("token", UserUtil.currentToken());
        HttpUtil.post("/approves/user-post", params, responseResult);
    }

    public static void queryPostApprove(long postId, ResponseResult responseResult) {
        HttpUtil.get(String.format("/approves/user-post?uId=%d&postId=%d", UserUtil.currentUser().getId(), postId), responseResult);
    }

    public static void uploadImage(String realPath, ResponseResult responseResult) {
        HttpUtil.uploadFile("/uploadImage", new File(realPath), responseResult);
    }

    public static void uploadVideo(String realPath, ResponseResult responseResult) {
        HttpUtil.uploadFile("/uploadVideo", new File(realPath), responseResult);
    }
}
