package com.example.spacexlaunchtracker.feature.home.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LaunchData.class}, version = 1, exportSchema = false)
public abstract class SpaceXLaunchDatabase extends RoomDatabase {
    private static SpaceXLaunchDatabase instance;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(2);

    public abstract SpaceXLaunchDao spaceXLaunchDao();

    public static synchronized SpaceXLaunchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SpaceXLaunchDatabase.class, "space_x_launch_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
