package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient twitterClient;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        twitterClient = TwitterApplication.getRestClient();
        final String screenName = getIntent().getStringExtra("screen_name");
        Log.d("DEBUG", "SCREEN NAME IS -->" + screenName);
        twitterClient.getUserInfo(screenName, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                // current user account information
                getSupportActionBar().setTitle('@' + screenName);
                Log.d("DEBUG", "USER IS -->" + user.getName() + user.getScreenName() + user.getTagline()    );

                populateUserHeader(user);
                Log.d("DEBUG", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, userTimelineFragment);

            //changes the fragments
            fragmentTransaction.commit();
        }
    }

    private void populateUserHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvUserName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + "followers");
        tvFollowing.setText(user.getFollowingCount() + "following");
        Picasso.with(this).load(user.getProileImageUrl()).into(ivProfileImage);
    }
}
