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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lullabot.android.apps.iosched.R;
import com.lullabot.android.apps.iosched.provider.ScheduleContract;
import com.lullabot.android.apps.iosched.provider.ScheduleContract.Rooms;
import com.lullabot.android.apps.iosched.util.AnalyticsUtils;
import com.lullabot.android.apps.iosched.util.ParserUtils;

/**
 * Shows a {@link WebView} with a map of the conference venue.
 */
public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    /**
     * When specified, will automatically point the map to the requested room.
     */
    public static final String EXTRA_ROOM = "com.google.android.iosched.extra.ROOM";

    private static final String MAP_URL = "http://maps.google.com/maps/ms?vpsrc=6&ctz=240&ie=UTF8&msa=0&msid=203649348921880554562.0004af21084cf6cb802ee&ll=40.770434,-73.987663&spn=0,0&t=m&source=embed";
    private static boolean CLEAR_CACHE_ON_LOAD = false;

    private WebView mWebView;
    private View mLoadingSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        AnalyticsUtils.getInstance(getActivity()).trackPageView("/Map");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_webview_with_spinner, null);

        // For some reason, if we omit this, NoSaveStateFrameLayout thinks we are
        // FILL_PARENT / WRAP_CONTENT, making the progress bar stick to the top of the activity.
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

        mLoadingSpinner = root.findViewById(R.id.loading_spinner);
        mWebView = (WebView) root.findViewById(R.id.webview);
        mWebView.setWebViewClient(mWebViewClient);

        mWebView.post(new Runnable() {
            public void run() {
                // Initialize web view
                if (CLEAR_CACHE_ON_LOAD) {
                    mWebView.clearCache(true);
                }

                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl(MAP_URL);
            }
        });

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh_menu_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            mWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoadingSpinner.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoadingSpinner.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            Log.e(TAG, "Error " + errorCode + ": " + description);
            Toast.makeText(view.getContext(), "Error " + errorCode + ": " + description,
                    Toast.LENGTH_LONG).show();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    };
}
