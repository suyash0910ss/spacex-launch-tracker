package com.example.spacexlaunchtracker.feature.detail.domain;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LaunchDetailData {

    @SerializedName("mission_name")
    private String missionName;

    @SerializedName("launch_date_utc")
    private String launchDate;

    @SerializedName("rocket")
    private RocketDetailData rocket;

    @SerializedName("launch_success")
    private boolean launchSuccess;

    @SerializedName("launch_site")
    private LaunchSiteData launchSite;

    @SerializedName("launch_window")
    private float launchWindow;

    @SerializedName("links")
    private LinksDetailData links;

    @SerializedName("details")
    private String description;

    @SerializedName("launch_failure_details")
    private LaunchFailureData launchFailureDetails;

    @SerializedName("ships")
    private List<String> ships;

    public String getMissionName() {
        return missionName;
    }

    public RocketDetailData getRocket() {
        return rocket;
    }

    public LaunchSiteData getLaunchSite() {
        return launchSite;
    }

    public float getLaunchWindow() {
        return launchWindow;
    }

    public LinksDetailData getLinks() {
        return links;
    }

    public String getDescription() {
        return description;
    }

    public LaunchFailureData getLaunchFailureDetails() {
        return launchFailureDetails;
    }

    public List<String> getShips() {
        return ships;
    }

    public String getFormattedLaunchDate() {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd MMMM, yyyy - HH:mm", Locale.US);
            outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date inputDate = inputFormat.parse(launchDate);
            return outputFormat.format(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLaunchStatus() {
        return launchSuccess ? "SUCCESSFUL" : "FAILED";
    }
}
