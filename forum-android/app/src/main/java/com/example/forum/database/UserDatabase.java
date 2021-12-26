package com.example.forum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.forum.dao.UserDao;
import com.example.forum.model.pojo.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
