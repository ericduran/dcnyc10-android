
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
import android.webkit.WebView;

import com.lullabot.android.apps.iosched.R;

/**
 * A {@link WebView}-based fragment that shows Google Realtime Search results for a given query,
 * provided as the {@link TagStreamFragment#EXTRA_QUERY} extra in the fragment arguments. If no
 * search query is provided, the conference hashtag is used as the default query.
 */
public class InfoFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.info_view, null);
        // Info Button.
        root.findViewById(R.id.btn_feedback).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	String url = "https://docs.google.com/spreadsheet/embeddedform?formkey=dE5hU0hfQ1d5cGltRzJ2dTBPakpsX3c6MQ";  
            	Intent i = new Intent(Intent.ACTION_VIEW);  
            	i.setData(Uri.parse(url));  
            	startActivity(i);  
            }
        });
        return root;
    }

}
