package com.example.spacexlaunchtracker.feature.detail.domain;

import com.google.gson.annotations.SerializedName;

public class RocketDetailData {

    @SerializedName("rocket_name")
    private String rocketName;

    @SerializedName("rocket_type")
    private String rocketType;

    public String getRocketName() {
        return rocketName;
    }

    public String getRocketType() {
        return rocketType;
    }
}
