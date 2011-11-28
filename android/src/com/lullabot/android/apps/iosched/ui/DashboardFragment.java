/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lullabot.android.apps.iosched.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lullabot.android.apps.iosched.R;
import com.lullabot.android.apps.iosched.provider.ScheduleContract;
import com.lullabot.android.apps.iosched.ui.phone.ScheduleActivity;
import com.lullabot.android.apps.iosched.ui.tablet.ScheduleMultiPaneActivity;
import com.lullabot.android.apps.iosched.ui.tablet.SessionsMultiPaneActivity;
import com.lullabot.android.apps.iosched.ui.tablet.VendorsMultiPaneActivity;
import com.lullabot.android.apps.iosched.util.AnalyticsUtils;
import com.lullabot.android.apps.iosched.util.UIUtils;

public class DashboardFragment extends Fragment {

    public void fireTrackerEvent(String label) {
        AnalyticsUtils.getInstance(getActivity()).trackEvent(
                "Home Screen Dashboard", "Click", label, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container);

        // Attach click Listeners.

        // Schedule Button.
        root.findViewById(R.id.home_btn_schedule).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Schedule");
                if (UIUtils.isHoneycombTablet(getActivity())) {
                    startActivity(new Intent(getActivity(), ScheduleMultiPaneActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), ScheduleActivity.class));
                }
            }
        });

        // Session Button.
        root.findViewById(R.id.home_btn_sessions).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Sessions");
                if (UIUtils.isHoneycombTablet(getActivity())) {
                    startActivity(new Intent(getActivity(), SessionsMultiPaneActivity.class));
                } else {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, ScheduleContract.Tracks.CONTENT_URI);
                    intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.title_session_tracks));
                    intent.putExtra(TracksFragment.EXTRA_NEXT_TYPE, TracksFragment.NEXT_TYPE_SESSIONS);
                    startActivity(intent);
                }
            }
        });

        // Map Button.
        root.findViewById(R.id.home_btn_map).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Map");
                startActivity(new Intent(getActivity(), FragmentPagerSupport.class));
            }
        });

        // Tweets Button.
        root.findViewById(R.id.home_btn_tweets).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Twitter");
                startActivity(new Intent(getActivity(), TwitterStreamActivity.class));                
            }
        });
        
        // News Button.
        root.findViewById(R.id.home_btn_news).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("News");
                startActivity(new Intent(getActivity(), NewsActivity.class));                
            }
        });
        // Info Button.
        root.findViewById(R.id.home_btn_info).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("News");
                startActivity(new Intent(getActivity(), InfoActivity.class));                
            }
        });
        

        // Sponsors Button.
        root.findViewById(R.id.home_btn_sponsors).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Sandbox");
                if (UIUtils.isHoneycombTablet(getActivity())) {
                    startActivity(new Intent(getActivity(), VendorsMultiPaneActivity.class));
                } else {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, ScheduleContract.Tracks.CONTENT_URI);
                    intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.title_vendor_tracks));
                    intent.putExtra(TracksFragment.EXTRA_NEXT_TYPE, TracksFragment.NEXT_TYPE_VENDORS);
                    startActivity(intent);
                }
            }
        });
        
        // Star Button.
        root.findViewById(R.id.home_btn_starred).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fireTrackerEvent("Starred");
                startActivity(new Intent(getActivity(), StarredActivity.class));                
            }
        });

        return root;
    }
}
