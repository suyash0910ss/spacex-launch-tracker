package com.example.spacexlaunchtracker.feature.detail.domain;

import com.google.gson.annotations.SerializedName;

public class LaunchFailureData {

    @SerializedName("reason")
    private String reason;

    public String getReason() {
        return reason;
    }
}
