package com.example.spacexlaunchtracker.feature.home.application;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spacexlaunchtracker.feature.home.data.SpaceXRepository;
import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.List;

public class SpaceXViewModel extends ViewModel {

    private final SpaceXRepository repository;
    private final LiveData<List<LaunchData>> bookmarkedLaunchData;
    private final LiveData<List<LaunchData>> databaseLaunchData;
    private final LiveData<List<LaunchData>> serverLaunchData;

    public SpaceXViewModel(Context application) {
        this.repository = new SpaceXRepository();
        this.bookmarkedLaunchData = repository.getBookmarkedLaunches(application);
        this.databaseLaunchData = repository.getLaunchesFromDatabase(application);
        this.serverLaunchData = repository.getLaunchesFromServer();
    }

    public LiveData<List<LaunchData>> getBookmarkedLaunches() {
        return bookmarkedLaunchData;
    }

    public LiveData<List<LaunchData>> getLaunchesFromDatabase() {
        return databaseLaunchData;
    }

    public LiveData<List<LaunchData>> getLaunchesFromServer() {
        return serverLaunchData;
    }

    public void toggleBookmarkStatus(LaunchData launch) {
        repository.updateLaunch(launch);
    }
}
