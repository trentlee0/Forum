package com.example.forum.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plate {
    @PrimaryKey
    @NonNull
    private long plateId;
    private String name;
    private long userId;
    private int postCount;

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public long getPlateId() {
        return plateId;
    }

    public void setPlateId(long plateId) {
        this.plateId = plateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "plateId=" + plateId +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", postCount=" + postCount +
                '}';
    }
}