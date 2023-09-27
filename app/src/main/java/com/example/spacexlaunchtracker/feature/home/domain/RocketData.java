package com.example.spacexlaunchtracker.feature.home.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class RocketData {

    @ColumnInfo(name = "rocket_name")
    @SerializedName("rocket_name")
    private String rocketName;

    @NonNull
    public String getRocketName() {
        return rocketName;
    }

    public void setRocketName(String rocketName) {
        this.rocketName = rocketName;
    }
}
