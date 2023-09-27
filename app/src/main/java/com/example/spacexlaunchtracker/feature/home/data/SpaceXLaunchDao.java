package com.example.spacexlaunchtracker.feature.home.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.List;

@Dao
public interface SpaceXLaunchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunch(LaunchData launch);

    @Query("SELECT * FROM launchData ORDER BY flight_number ASC")
    LiveData<List<LaunchData>> getAllLaunches();

    @Query("UPDATE launchData SET isBookmarked = :isBookmarked WHERE flight_number = :flight_number")
    void updateBookmarkStatus(int flight_number, boolean isBookmarked);

    @Query("SELECT * FROM launchData WHERE isBookmarked = 1")
    LiveData<List<LaunchData>> getBookmarkedLaunches();
}
