package com.example.forum.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.forum.dao.PlateDao;
import com.example.forum.model.pojo.Plate;

@Database(entities = {Plate.class}, version = 1, exportSchema = false)
public abstract class PlateDatabase extends RoomDatabase {
    public abstract PlateDao plateDao();
}
