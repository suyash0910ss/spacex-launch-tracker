package com.example.spacexlaunchtracker.feature.home.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.spacexlaunchtracker.feature.core.data.RetrofitClient;
import com.example.spacexlaunchtracker.feature.core.data.SpaceXApiService;
import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpaceXRepository {

    private SpaceXLaunchDao spaceXLaunchDao;

    public LiveData<List<LaunchData>> getBookmarkedLaunches(Context application) {
        SpaceXLaunchDatabase database = SpaceXLaunchDatabase.getInstance(application);
        spaceXLaunchDao = database.spaceXLaunchDao();

        return Transformations.distinctUntilChanged(spaceXLaunchDao.getBookmarkedLaunches());
    }

    public LiveData<List<LaunchData>> getLaunchesFromDatabase(Context application) {
        SpaceXLaunchDatabase database = SpaceXLaunchDatabase.getInstance(application);
        spaceXLaunchDao = database.spaceXLaunchDao();

        return Transformations.distinctUntilChanged(spaceXLaunchDao.getAllLaunches());
    }

    public LiveData<List<LaunchData>> getLaunchesFromServer() {
        final MutableLiveData<List<LaunchData>> data = new MutableLiveData<>();
        SpaceXApiService spaceXService = RetrofitClient.getInstance().create(SpaceXApiService.class);
        spaceXService.getAllLaunches().enqueue(new Callback<List<LaunchData>>() {
            @Override
            public void onResponse(@NonNull Call<List<LaunchData>> call, @NonNull Response<List<LaunchData>> response) {
                List<LaunchData> resource = new ArrayList<>();
                if (response.isSuccessful() && response.body() != null) {
                    List<LaunchData> launchDataList = new ArrayList<>(response.body());
                    for (LaunchData launchData : launchDataList) {
                        insertLaunch(launchData);
                    }

                    resource = response.body();
                }

                if (data.getValue() == null) {
                    data.setValue(resource);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LaunchData>> call, @NonNull Throwable t) {}
        });

        return data;
    }

    public void updateLaunch(LaunchData launch) {
        SpaceXLaunchDatabase.databaseWriteExecutor.execute(() ->
                spaceXLaunchDao.updateBookmarkStatus(launch.getFlightNumber(), launch.getIsBookmarked()));
    }

    private void insertLaunch(LaunchData launch) {
        SpaceXLaunchDatabase.databaseWriteExecutor.execute(() -> spaceXLaunchDao.insertLaunch(launch));
    }
}
