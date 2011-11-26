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

import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lullabot.android.apps.iosched.R;


public class TwitterStreamFragment extends ListFragment {
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            new MyBGTask().execute();
        }
        private class MyBGTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... arg0) {
                    try {
                        tweets = getTweets("drupalcampnyc", 1);
                   } catch (Exception e) {
                           Log.e("TwitterFeedActivity", "Error loading JSON", e);
                   }
                   return null;
            }
            
            @Override
            protected void onPostExecute(Void result) {
                setListAdapter(new TweetItemAdapter(getActivity(), R.layout.tweets_items, tweets));
           }
        }
        public class TweetItemAdapter extends ArrayAdapter<Tweet> {
        	  private ArrayList<Tweet> tweets;

        	  public TweetItemAdapter(Context context, int textViewResourceId, ArrayList<Tweet> tweets) {
        	    super(context, textViewResourceId, tweets);
        	    this.tweets = tweets;
        	  }

        	  @Override
        	  public View getView(int position, View convertView, ViewGroup parent) {
        	    View v = convertView;
        	    if (v == null) {
        	      v = getActivity().getLayoutInflater().inflate(R.layout.tweets_items, null);
        	    }

        	    Tweet tweet = tweets.get(position);
        	    if (tweet != null) {
        	      TextView username = (TextView) v.findViewById(R.id.username);
        	      TextView message = (TextView) v.findViewById(R.id.tweet_msg);
        	      message.setAutoLinkMask(Linkify.WEB_URLS);
        	      ImageView image = (ImageView) v.findViewById(R.id.tweet_avatar);

        	      if (username != null) {
        	        username.setText(tweet.username);
        	      }

        	      if(message != null) {
        	        message.setText(Html.fromHtml(tweet.message));
        	      }    	        
        	      if(image != null) {
        	        image.setImageBitmap(getBitmap(tweet.image_url));
        	      }
        	    }
        	    return v;
        	  }
        	}
        public ArrayList<Tweet> getTweets(String searchTerm, int page) {
        	  String searchUrl = 
        	        "http://search.twitter.com/search.json?q=@" 
        	        + searchTerm + "&rpp=50&page=" + page;
        	    
        	  ArrayList<Tweet> tweets = 
        	        new ArrayList<Tweet>();
        	    
        	  HttpClient client = new  DefaultHttpClient();
        	  HttpGet get = new HttpGet(searchUrl);
        	        
        	  ResponseHandler<String> responseHandler = 
        	        new BasicResponseHandler();

        	  String responseBody = null;
        	  try {
        	    responseBody = client.execute(get, responseHandler);
        	  } catch(Exception ex) {
        	    ex.printStackTrace();
        	  }

        	  JSONObject jsonObject = null;
        	  JSONParser parser=new JSONParser();
        	    
        	  try {
        	    Object obj = parser.parse(responseBody);
        	    jsonObject=(JSONObject)obj;
        	  }catch(Exception ex){
        	    Log.v("TEST","Exception: " + ex.getMessage());
        	  }
        	    
        	  JSONArray arr = null;
        	    
        	  try {
        	    Object j = jsonObject.get("results");
        	    arr = (JSONArray)j;
        	  } catch(Exception ex){
        	    Log.v("TEST","Exception: " + ex.getMessage());
        	  }

        	  for(Object t : arr) {
        	    Tweet tweet = new Tweet(
        	      ((JSONObject)t).get("from_user").toString(),
        	      ((JSONObject)t).get("text").toString(),
        	      ((JSONObject)t).get("profile_image_url").toString()
        	    );
        	    tweets.add(tweet);
        	  }
        	    
        	  return tweets;
        	}
        
        public Bitmap getBitmap(String bitmapUrl) {
        	  try {
        	    URL url = new URL(bitmapUrl);
        	    return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
        	  }
        	  catch(Exception ex) {return null;}
        	}
        
        public class Tweet {
      	  public String username;
      	  public String message;
      	  public String image_url;
      	    
      	  public Tweet(String username, String message, String url) {
      	    this.username = username;
      	    this.message = message;
      	    this.image_url = url;
      	  }
      }
 }

