package com.example.spacexlaunchtracker.feature.core.data;

import com.example.spacexlaunchtracker.feature.detail.domain.LaunchDetailData;
import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpaceXApiService {

    @GET("launches/")
    Call<List<LaunchData>> getAllLaunches();

    @GET("launches/{flight_number}")
    Call<LaunchDetailData> getLaunchByFlightNumber(@Path("flight_number") int flightNumber);
}
