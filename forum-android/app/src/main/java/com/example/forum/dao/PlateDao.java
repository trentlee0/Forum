package com.example.forum.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forum.model.pojo.Plate;

import java.util.List;

@Dao
public interface PlateDao {
    @Query("select * from Plate")
    List<Plate> queryAll();

    @Update
    void update(Plate plate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plate plate);

    @Query("delete from Plate where 1=1")
    void deleteAll();
}
