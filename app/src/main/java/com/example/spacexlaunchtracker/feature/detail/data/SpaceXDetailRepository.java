package com.example.spacexlaunchtracker.feature.detail.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spacexlaunchtracker.feature.core.data.RetrofitClient;
import com.example.spacexlaunchtracker.feature.core.data.SpaceXApiService;
import com.example.spacexlaunchtracker.feature.detail.domain.LaunchDetailData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpaceXDetailRepository {

    public LiveData<LaunchDetailData> getLaunchDetails(int flightNumber) {
        final MutableLiveData<LaunchDetailData> data = new MutableLiveData<>();
        SpaceXApiService spaceXService = RetrofitClient.getInstance().create(SpaceXApiService.class);

        spaceXService.getLaunchByFlightNumber(flightNumber).enqueue(new Callback<LaunchDetailData>() {
            @Override
            public void onResponse(@NonNull Call<LaunchDetailData> call, @NonNull Response<LaunchDetailData> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LaunchDetailData> call, @NonNull Throwable t) {}
        });

        return data;
    }
}
