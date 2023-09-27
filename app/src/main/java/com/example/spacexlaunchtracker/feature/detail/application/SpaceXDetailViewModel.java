package com.example.spacexlaunchtracker.feature.detail.application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spacexlaunchtracker.feature.detail.data.SpaceXDetailRepository;
import com.example.spacexlaunchtracker.feature.detail.domain.LaunchDetailData;

public class SpaceXDetailViewModel extends ViewModel {

    private final SpaceXDetailRepository spaceXDetailRepository;

    public SpaceXDetailViewModel() {
        this.spaceXDetailRepository = new SpaceXDetailRepository();
    }

    public LiveData<LaunchDetailData> getLaunchDetails(int flightNumber) {
        return spaceXDetailRepository.getLaunchDetails(flightNumber);
    }
}
