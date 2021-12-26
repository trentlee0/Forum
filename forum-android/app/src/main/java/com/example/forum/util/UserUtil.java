package com.example.forum.util;

import com.example.forum.MyApplication;
import com.example.forum.model.pojo.User;

public class UserUtil {

    /**
     * 游客
     */
    public static final int VISITOR = 0;

    /**
     * 普通用户
     */
    public static final int GENERAL = 1;

    /**
     * 版主
     */
    public static final int MODERATOR = 2;

    /**
     * 管理员
     */
    public static final int ADMIN = 3;

    public static String getUserGrade(byte grade) {
        switch (grade) {
            case GENERAL:
                return "普通用户";
            case MODERATOR:
                return "版主";
            case ADMIN:
                return "管理员";
            default:
                return "游客";
        }
    }

    public static String genderToString(byte gender) {
        switch (gender) {
            case 0:
                return "男";
            case 1:
                return "女";
            default:
                return "未知";
        }
    }

    public static boolean isGeneralUser(byte grade) {
        return GENERAL == grade;
    }

    public static boolean isModeratorUser(byte grade) {
        return MODERATOR == grade;
    }

    public static boolean isGeneralUser() {
        User user = currentUser();
        return isGeneralUser(user != null ? user.getGrade() : 0);
    }

    public static boolean isModeratorUser() {
        User user = currentUser();
        return isModeratorUser(user != null ? user.getGrade() : 0);
    }

    public static boolean isLogin() {
        return MyApplication.getUserVO().isLogin();
    }

    public static User currentUser() {
        return MyApplication.getUserVO().getUser();
    }

    public static String currentToken() {
        return MyApplication.getUserVO().getToken();
    }

    public static void saveLoginUser(User user) {
        MyApplication.saveLoginUser(user);
        MyApplication.getInstance().getUserDatabase().userDao().insert(user);
    }

    public static void removeLogoutUser() {
        MyApplication.removeLogoutUser();
        MyApplication.getInstance().getUserDatabase().userDao().deleteUser();
    }
}
