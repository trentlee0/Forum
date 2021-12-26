package com.example.forum;

import android.app.Application;

import androidx.room.Room;

import com.example.forum.database.PlateDatabase;
import com.example.forum.database.UserDatabase;
import com.example.forum.model.pojo.User;
import com.example.forum.model.vo.UserVO;

public class MyApplication extends Application {
    private static MyApplication app;

    private UserDatabase userDatabase;
    private PlateDatabase plateDatabase;

    private static UserVO userVO;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        userDatabase = Room.databaseBuilder(app, UserDatabase.class, "User")
                .addMigrations()
                .allowMainThreadQueries()
                .build();

        plateDatabase = Room.databaseBuilder(app, PlateDatabase.class, "Plate")
                .addMigrations()
                .allowMainThreadQueries()
                .build();

        User user = userDatabase.userDao().query();
        userVO = new UserVO();
        userVO.setUser(user);
        userVO.setToken(user != null ? user.getToken() : null);
        userVO.setLogin(user != null);
    }

    public static MyApplication getInstance() {
        return app;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public PlateDatabase getPlateDatabase() {
        return plateDatabase;
    }

    public static UserVO getUserVO() {
        return userVO;
    }

    public static void saveLoginUser(User user) {
        userVO.setUser(user);
        userVO.setLogin(true);
        userVO.setToken(user != null ? user.getToken() : null);
    }

    public static void removeLogoutUser() {
        userVO.setUser(null);
        userVO.setLogin(false);
    }
}
