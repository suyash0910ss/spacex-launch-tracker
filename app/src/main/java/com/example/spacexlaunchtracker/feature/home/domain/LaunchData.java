package com.example.spacexlaunchtracker.feature.home.domain;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "launchData")
public class LaunchData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "flight_number")
    @SerializedName("flight_number")
    private int flightNumber;

    @ColumnInfo(name = "mission_name")
    @SerializedName("mission_name")
    private String missionName;

    @ColumnInfo(name = "launch_date_utc")
    @SerializedName("launch_date_utc")
    private String launchDate;

    @Embedded
    @SerializedName("rocket")
    private RocketData rocket;

    @ColumnInfo(name = "launch_success")
    @SerializedName("launch_success")
    private boolean launchSuccess;

    @Embedded
    @SerializedName("links")
    private LinksData links;

    private boolean isBookmarked;

    @NonNull
    public int getFlightNumber() {
        return flightNumber;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public RocketData getRocket() {
        return rocket;
    }

    public boolean getLaunchSuccess() {
        return launchSuccess;
    }

    public LinksData getLinks() {
        return links;
    }

    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public void setFlightNumber(@NonNull int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public void setRocket(RocketData rocket) {
        this.rocket = rocket;
    }

    public void setLaunchSuccess(boolean launchSuccess) {
        this.launchSuccess = launchSuccess;
    }

    public void setLinks(LinksData links) {
        this.links = links;
    }

    public void setBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    // Transformations

    public String getFormattedLaunchDate() {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat =
                    new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

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

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
