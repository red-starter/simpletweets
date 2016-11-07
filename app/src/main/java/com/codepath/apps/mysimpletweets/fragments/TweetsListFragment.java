package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelsova on 11/3/16.
 */

public class TweetsListFragment extends Fragment {
    //inflation logic
    private ArrayList<Tweet> tweetArrayList;
    private TweetsArrayAdapter tweetsArrayAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TwitterClient client;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_timeline);
        recyclerView.setAdapter(tweetsArrayAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                populateTimeline(page);
//            }
//        });

        return v;
    }
//    public void populateTimeline(int page){
//        String screenName = getArguments().getString("screen_name");
//        client.getUserTimeline(page, screenName, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                addAll(Tweet.fromJSONArray(response));
//                Log.d("DEBUG", response.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", errorResponse.toString());
//            }
//        });
//    }


    //creation lifecycle event

//    public void fetchMoreHomeContent(String endpoint, int page) {
//        getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                addAll(Tweet.fromJSONArray(response));
//            }
//        });
//    }
    
        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetArrayList = new ArrayList<>();
        tweetsArrayAdapter = new TweetsArrayAdapter(getActivity(), tweetArrayList);
        client = TwitterApplication.getRestClient();
    }

    public void addAll(List<Tweet> tweets){
        tweetArrayList.addAll(tweets);
        tweetsArrayAdapter.notifyDataSetChanged();
    }

    public void addOne(Tweet tweet){
        tweetArrayList.add(0, tweet);
        tweetsArrayAdapter.notifyItemChanged(0);
    }

    public void clearAllTweet() {
        tweetArrayList.clear();
        tweetsArrayAdapter.notifyDataSetChanged();
    }

}
