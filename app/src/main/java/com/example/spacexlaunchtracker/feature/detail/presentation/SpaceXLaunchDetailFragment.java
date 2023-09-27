package com.example.spacexlaunchtracker.feature.detail.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spacexlaunchtracker.R;
import com.example.spacexlaunchtracker.feature.detail.application.SpaceXDetailViewModel;
import com.example.spacexlaunchtracker.feature.detail.domain.LaunchDetailData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpaceXLaunchDetailFragment extends Fragment {

    private TextView loadingView;
    private LinearLayout containerView;

    // header views
    private ImageView thumbnailView;
    private TextView missionNameView;
    private TextView dateAndTimeView;

    // mission objective views
    private LinearLayout missionObjectiveContainer;
    private TextView missionObjectiveView;

    // rocket details views
    private TextView rocketNameView;
    private TextView rocketTypeView;
    private TextView shipsView;

    // launch details view
    private TextView launchStatusView;
    private TextView launchFailureReasonView;
    private TextView launchSiteView;

    // related links views
    private LinearLayout relatedLinksContainerView;
    private LinearLayout videoLinkContainerView;
    private TextView videoLinkView;
    private LinearLayout articleLinkContainerView;
    private TextView articleLinkView;
    private LinearLayout pressKitLinkContainerView;
    private TextView pressKitLinkView;

    private int flightNumber = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spacex_launch_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            flightNumber = bundle.getInt("flightNumber");
        }

        loadingView = view.findViewById(R.id.loading_view_detail_screen);
        containerView = view.findViewById(R.id.launch_detail_container);

        // header views
        thumbnailView = view.findViewById(R.id.thumbnail);
        missionNameView = view.findViewById(R.id.detail_screen_mission_name);
        dateAndTimeView = view.findViewById(R.id.date_and_time);

        // mission objective views
        missionObjectiveContainer = view.findViewById(R.id.mission_objective_container);
        missionObjectiveView = view.findViewById(R.id.mission_objective_text);

        // rocket details views
        rocketNameView = view.findViewById(R.id.rocket_name_detail_screen);
        rocketTypeView = view.findViewById(R.id.rocket_type);
        shipsView = view.findViewById(R.id.ships);

        // launch details view
        launchStatusView = view.findViewById(R.id.launch_status_detail_screen);
        launchFailureReasonView = view.findViewById(R.id.launch_failure_reason);
        launchSiteView = view.findViewById(R.id.launch_site);

        // related links views
        relatedLinksContainerView = view.findViewById(R.id.related_links_container);
        videoLinkContainerView = view.findViewById(R.id.video_link_container);
        videoLinkView = view.findViewById(R.id.video_link);
        articleLinkContainerView = view.findViewById(R.id.article_link_container);
        articleLinkView = view.findViewById(R.id.article_link);
        pressKitLinkContainerView = view.findViewById(R.id.press_kit_link_container);
        pressKitLinkView = view.findViewById(R.id.press_kit_link);

        SpaceXDetailViewModel viewModel = new ViewModelProvider(requireActivity()).get(SpaceXDetailViewModel.class);
        viewModel.getLaunchDetails(flightNumber).observe(getViewLifecycleOwner(), launchDetailData -> {
            if (launchDetailData != null) {
                loadingView.setVisibility(View.GONE);
                containerView.setVisibility(View.VISIBLE);

                setupHeaderSection(launchDetailData);
                setupMissionObjectiveSection(launchDetailData);
                setupRocketDetailsSection(launchDetailData);
                setupLaunchDetailsSection(launchDetailData);
                setupRelatedLinksSection(launchDetailData);
            }
        });
    }

    private void setupHeaderSection(LaunchDetailData launchDetailData) {
        String patchUrl = launchDetailData.getLinks().getMissionPatch();
        if (patchUrl == null || patchUrl.isEmpty()) {
            thumbnailView.setImageResource(R.drawable.rocket);
        } else {
            Picasso.get().load(patchUrl).into(thumbnailView);
        }

        missionNameView.setText(launchDetailData.getMissionName());
        dateAndTimeView.setText(launchDetailData.getFormattedLaunchDate());
    }

    private void setupMissionObjectiveSection(LaunchDetailData launchDetailData) {
        if (launchDetailData.getDescription() != null && !launchDetailData.getDescription().isEmpty()) {
            missionObjectiveView.setText(launchDetailData.getDescription());
            missionObjectiveContainer.setVisibility(View.VISIBLE);
        } else {
            missionObjectiveContainer.setVisibility(View.GONE);
        }
    }

    private void setupRocketDetailsSection(LaunchDetailData launchDetailData) {
        rocketNameView.setText(requireContext().getResources().getString(R.string.rocket_name_label, launchDetailData.getRocket().getRocketName()));
        rocketTypeView.setText(requireContext().getResources().getString(R.string.rocket_type_label,
                launchDetailData.getRocket().getRocketType()));
        List<String> shipsData = launchDetailData.getShips();
        if (shipsData == null || shipsData.isEmpty()) {
            shipsView.setVisibility(View.GONE);
        } else {
            StringBuilder shipsStringBuilder = new StringBuilder();
            for (int i = 0; i < shipsData.size(); i++) {
                shipsStringBuilder.append(shipsData.get(i));
                if (i + 1 < shipsData.size()) {
                    shipsStringBuilder.append(", ");
                }
            }
            shipsView.setText(requireContext().getResources().getString(R.string.ships_label,
                    shipsStringBuilder.toString()));
            shipsView.setVisibility(View.VISIBLE);
        }
    }

    private void setupLaunchDetailsSection(LaunchDetailData launchDetailData) {
        String launchStatus = launchDetailData.getLaunchStatus();
        launchStatusView.setText(requireContext().getResources().getString(R.string.launch_status_label, launchStatus));
        launchStatusView.setTextColor(ContextCompat.getColor(requireContext(),
                launchStatus.equals("SUCCESSFUL") ? R.color.green : R.color.red));
        if (launchDetailData.getLaunchFailureDetails() != null
                && launchDetailData.getLaunchFailureDetails().getReason() != null) {
            launchFailureReasonView.setText(requireContext().getResources().getString(R.string.launch_failure_reason_label,
                    launchDetailData.getLaunchFailureDetails().getReason()));
            launchFailureReasonView.setVisibility(View.VISIBLE);
        } else {
            launchFailureReasonView.setVisibility(View.GONE);
        }
        if (launchDetailData.getLaunchSite() != null
                && launchDetailData.getLaunchSite().getSiteAddress() != null) {
            launchSiteView.setText(requireContext().getResources().getString(R.string.launch_location_label,
                    launchDetailData.getLaunchSite().getSiteAddress()));
            launchSiteView.setVisibility(View.VISIBLE);
        } else {
            launchSiteView.setVisibility(View.GONE);
        }
    }

    private void setupRelatedLinksSection(LaunchDetailData launchDetailData) {
        if (launchDetailData.getLinks().getVideoLink() == null
                && launchDetailData.getLinks().getArticleLink() == null
                && launchDetailData.getLinks().getPressKit() == null) {
            relatedLinksContainerView.setVisibility(View.GONE);
        } else {
            relatedLinksContainerView.setVisibility(View.VISIBLE);

            String videoLink = launchDetailData.getLinks().getVideoLink();
            if (videoLink != null) {
                videoLinkContainerView.setVisibility(View.VISIBLE);

                SpannableString spannedString = new SpannableString(videoLink);
                spannedString.setSpan(new UnderlineSpan(), 0, videoLink.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                videoLinkView.setText(spannedString);
                videoLinkView.setMovementMethod(LinkMovementMethod.getInstance());
                videoLinkView.setOnClickListener(view1 -> openLink(videoLink));
            } else {
                videoLinkContainerView.setVisibility(View.GONE);
            }

            String articleLink = launchDetailData.getLinks().getArticleLink();
            if (articleLink != null) {
                articleLinkContainerView.setVisibility(View.VISIBLE);

                SpannableString spannedString = new SpannableString(articleLink);
                spannedString.setSpan(new UnderlineSpan(), 0, articleLink.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                articleLinkView.setText(spannedString);
                articleLinkView.setMovementMethod(LinkMovementMethod.getInstance());
                articleLinkView.setOnClickListener(view1 -> openLink(articleLink));
            } else {
                articleLinkContainerView.setVisibility(View.GONE);
            }

            String pressKitLink = launchDetailData.getLinks().getPressKit();
            if (pressKitLink != null) {
                pressKitLinkContainerView.setVisibility(View.VISIBLE);

                SpannableString spannedString = new SpannableString(pressKitLink);
                spannedString.setSpan(new UnderlineSpan(), 0, pressKitLink.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                pressKitLinkView.setText(spannedString);
                pressKitLinkView.setMovementMethod(LinkMovementMethod.getInstance());
                pressKitLinkView.setOnClickListener(view1 -> openPDFUsingIntent(pressKitLink));
            } else {
                pressKitLinkContainerView.setVisibility(View.GONE);
            }
        }
    }

    private void openLink(String videoLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
            startActivity(webIntent);
        }
    }

    private void openPDFUsingIntent(String pressKitLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pressKitLink), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
