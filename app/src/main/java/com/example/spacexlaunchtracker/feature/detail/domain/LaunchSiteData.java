package com.example.spacexlaunchtracker.feature.detail.domain;

import com.google.gson.annotations.SerializedName;

public class LaunchSiteData {

    @SerializedName("site_name")
    private String siteName;

    @SerializedName("site_name_long")
    private String siteAddress;

    public String getSiteName() {
        return siteName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }
}
