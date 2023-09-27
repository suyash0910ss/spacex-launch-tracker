package com.example.spacexlaunchtracker.feature.home.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacexlaunchtracker.R;
import com.example.spacexlaunchtracker.feature.MainActivity;
import com.example.spacexlaunchtracker.feature.home.domain.LaunchData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LaunchAdapter extends ListAdapter<LaunchData, LaunchAdapter.LaunchViewHolder> {
    private List<LaunchData> bookmarkedLaunchList;
    private final Context context;

    private final OnBookmarkIconOnClickListener bookmarkIconOnClickListener;

    public LaunchAdapter(OnBookmarkIconOnClickListener bookmarkIconOnClickListener,
                         Context context) {
        super(new LaunchDiffCallback());
        this.bookmarkIconOnClickListener = bookmarkIconOnClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public LaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.launch_card_item, parent, false);
        return new LaunchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchViewHolder holder, int position) {
        LaunchData launchData = getItem(position);

        holder.missionNameTextView.setText(launchData.getMissionName());

        String launchStatus = launchData.getLaunchStatus();
        holder.launchStatusTextView.setText(launchStatus);
        holder.launchStatusTextView.setTextColor(ContextCompat.getColor(context, launchStatus.equals("SUCCESSFUL") ? R.color.green : R.color.red));

        holder.rocketNameTextView.setText(context.getResources().getString(R.string.rocket_name_label, launchData.getRocket().getRocketName()));
        holder.launchDateTextView.setText(context.getResources().getString(R.string.launch_date_label, launchData.getFormattedLaunchDate()));

        if (launchData.getLinks().getMissionPatchSmall().equals("_")) {
            holder.missionThumbnail.setImageResource(R.drawable.rocket);
        } else {
            Picasso.get().load(launchData.getLinks().getMissionPatchSmall()).into(holder.missionThumbnail);
        }

        if (isBookmarked(launchData.getFlightNumber())) {
            holder.bookmarkIconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.positive_bookmark));
        } else {
            holder.bookmarkIconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.negative_bookmark));
        }

        holder.bookmarkIconView.setOnClickListener(view -> {
            if (launchData.getIsBookmarked()) {
                launchData.setBookmarked(false);
                holder.bookmarkIconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.negative_bookmark));
            } else {
                launchData.setBookmarked(true);
                holder.bookmarkIconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.positive_bookmark));
            }
            bookmarkIconOnClickListener.onItemClick(launchData);
        });

        // setup card click listener
        holder.launchCardView.setOnClickListener(view -> openDetailScreen(launchData.getFlightNumber()));
    }

    public void setBookmarkedLaunchList(List<LaunchData> bookmarkedLaunchList) {
        this.bookmarkedLaunchList = bookmarkedLaunchList;
    }

    private boolean isBookmarked(int flightNumber) {
        if (bookmarkedLaunchList.isEmpty()) {
            return false;
        }
        for (LaunchData bookMarkedLaunchData : bookmarkedLaunchList) {
            if (bookMarkedLaunchData.getFlightNumber() == flightNumber) {
                return true;
            }
        }

        return false;
    }

    private void openDetailScreen(int flightNumber) {
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.openDetailFragment(flightNumber);
        }
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class LaunchViewHolder extends RecyclerView.ViewHolder {

        CardView launchCardView;
        TextView missionNameTextView;
        TextView launchStatusTextView;
        TextView rocketNameTextView;
        TextView launchDateTextView;
        ImageView missionThumbnail;
        ImageView bookmarkIconView;

        public LaunchViewHolder(View itemView) {
            super(itemView);
            launchCardView = itemView.findViewById(R.id.launch_card);
            missionNameTextView = itemView.findViewById(R.id.mission_name);
            launchStatusTextView = itemView.findViewById(R.id.launch_status);
            rocketNameTextView = itemView.findViewById(R.id.rocket_name);
            launchDateTextView = itemView.findViewById(R.id.launch_date);
            missionThumbnail = itemView.findViewById(R.id.mission_patch_thumbnail);
            bookmarkIconView = itemView.findViewById(R.id.bookmarked_icon);
        }
    }

    public interface OnBookmarkIconOnClickListener {
        void onItemClick(LaunchData launchData);
    }

    private static class LaunchDiffCallback extends DiffUtil.ItemCallback<LaunchData> {
        @Override
        public boolean areItemsTheSame(@NonNull LaunchData oldItem, @NonNull LaunchData newItem) {
            return oldItem.getFlightNumber() == newItem.getFlightNumber();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LaunchData oldItem, @NonNull LaunchData newItem) {
            return oldItem.getIsBookmarked() == oldItem.getIsBookmarked();
        }
    }
}
