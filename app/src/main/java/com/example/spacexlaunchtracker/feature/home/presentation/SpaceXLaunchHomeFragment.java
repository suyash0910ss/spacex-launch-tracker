package com.example.spacexlaunchtracker.feature.home.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.spacexlaunchtracker.R;
import com.example.spacexlaunchtracker.feature.home.application.SpaceXViewModel;
import com.example.spacexlaunchtracker.feature.home.application.SpaceXViewModelFactory;
import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;

import java.util.ArrayList;
import java.util.List;

public class SpaceXLaunchHomeFragment extends Fragment implements LaunchAdapter.OnBookmarkIconOnClickListener {

    private TextView loadingView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LaunchAdapter adapter;
    private SpaceXViewModel viewModel;
    private boolean bookmarkedAlreadyFetched = false;
    private List<LaunchData> bookmarkedLaunches = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spacex_launch_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingView = view.findViewById(R.id.loading_view);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LaunchAdapter(this, getContext());
        recyclerView.setAdapter(adapter);

        SpaceXViewModelFactory viewModelFactory = new SpaceXViewModelFactory(requireActivity().getApplicationContext());
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SpaceXViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            setupLaunchTrackerView();
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 2000);
        });

        setupLaunchTrackerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bookmarkedAlreadyFetched = false;
    }

    @Override
    public void onItemClick(LaunchData launchData) {
        viewModel.toggleBookmarkStatus(launchData);
    }

    private void setupLaunchTrackerView() {
        viewModel.getBookmarkedLaunches().observe(getViewLifecycleOwner(), bookmarkedLaunchData -> {
            bookmarkedLaunches = bookmarkedLaunchData;
            if (!bookmarkedAlreadyFetched) {
                bookmarkedAlreadyFetched = true;
                viewModel.getLaunchesFromDatabase().observe(getViewLifecycleOwner(), databaseLaunchData -> {
                    if (!databaseLaunchData.isEmpty()) {
                        showLaunchTrackerData(databaseLaunchData);
                    } else {
                        viewModel.getLaunchesFromServer().observe(getViewLifecycleOwner(), serverLaunchData -> {
                            if (!serverLaunchData.isEmpty()) {
                                showLaunchTrackerData(serverLaunchData);
                            }
                        });
                    }
                });
            }
        });
    }

    private void showLaunchTrackerData(List<LaunchData> launches) {
        loadingView.setVisibility(View.GONE);
        adapter.setBookmarkedLaunchList(bookmarkedLaunches);
        adapter.submitList(launches);
    }
}
