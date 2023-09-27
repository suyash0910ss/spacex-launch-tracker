package com.example.spacexlaunchtracker.feature.home.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class LinksData {

    @ColumnInfo(name = "mission_patch_small")
    @SerializedName("mission_patch_small")
    private String missionPatchSmall;

    @NonNull
    public String getMissionPatchSmall() {
        return missionPatchSmall != null ? missionPatchSmall : "_";
    }

    public void setMissionPatchSmall(String missionPatchSmall) {
        this.missionPatchSmall = missionPatchSmall;
    }
}
