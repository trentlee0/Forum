package com.example.forum.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forum.model.pojo.User;

@Dao
public interface UserDao {
    @Query("select * from User")
    User query();

    @Insert
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("delete from User where 1=1")
    void deleteUser();
}
