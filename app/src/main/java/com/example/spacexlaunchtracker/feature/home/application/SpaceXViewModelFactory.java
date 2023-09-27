package com.example.spacexlaunchtracker.feature.home.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SpaceXViewModelFactory implements ViewModelProvider.Factory {
    private final Context application;

    public SpaceXViewModelFactory(Context application) {
        this.application = application;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SpaceXViewModel.class)) {
            return (T) new SpaceXViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
