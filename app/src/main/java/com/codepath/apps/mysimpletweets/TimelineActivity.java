package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;

public class TimelineActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private static final int CREATE_TWEET_REQUEST_CODE = 100;


    public void onProfileView(MenuItem item) {
        //launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
//    private int tabIcons[] = {R.drawable.ic_menu_add, @android:drawable/ic_menu_add};

    //    return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 0;
        private String tabTitles[] = {"Home", "Mentions"};
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeTimelineFragment();
                case 1:
                    return new MentionsTimelineFragment();
            }
            return null;
        }

//        @Override
//        public int getPageIconResId(int position) {
//            return tabIcons[position];
//        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // Lookup the recyclerview in activity layout
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                fetchTimelineAsync(0);
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

    }
//    public static void fetchMoreHomeContent(int page) {
//        getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//
//                super.onSuccess(statusCode, headers, response);
//                tweetsListFragment.addAll(Tweet.fromJSONArray(response));
//            }
//        });
//    }


    public void launchCreateTweetView() {
        startActivityForResult(new Intent(TimelineActivity.this, CreateTweetActivity.class), CREATE_TWEET_REQUEST_CODE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_create) {
            launchCreateTweetView();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CREATE_TWEET_REQUEST_CODE){
//            handleCreateTweet(resultCode, data);
//        }
//    }

//    private void handleCreateTweet(int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//                Tweet tweet = new Tweet();
//                tweet.setBody(data.getStringExtra("body"));
//                User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
//                tweet.setUser(user);
//                postNewTweet(tweet);
//        }
//    }

//    protected void postNewTweet(final Tweet tweet) {
//        TwitterApplication.getRestClient().postTweet(tweet.getBody(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                TweetsListFragment.addOne(Tweet.fromJSON(response));
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", errorResponse.toString());
//            }
//        });
//    }

//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                TweetsListFragment.clearAllTweet();
//                // ...the data has come back, add new items to your adapter...
//                TweetsListFragment.addAll(Tweet.fromJSONArray(response));
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", "Fetch timeline error: " + errorResponse.toString());
//            }
//        });
//    }

}
