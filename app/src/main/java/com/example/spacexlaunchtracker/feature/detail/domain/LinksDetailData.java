package com.example.spacexlaunchtracker.feature.detail.domain;

import com.google.gson.annotations.SerializedName;

public class LinksDetailData {

    @SerializedName("mission_patch_small")
    private String missionPatch;

    @SerializedName("article_link")
    private String articleLink;

    @SerializedName("presskit")
    private String pressKit;

    @SerializedName("video_link")
    private String videoLink;

    public String getMissionPatch() {
        return missionPatch;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public String getPressKit() {
        return pressKit;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
