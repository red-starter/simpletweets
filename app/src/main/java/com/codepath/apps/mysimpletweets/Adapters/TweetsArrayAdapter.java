package com.codepath.apps.mysimpletweets.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by michaelsova on 10/31/16.
 */

public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {
    // ... constructor and member variables
    List<Tweet> Tweets;

    public TweetsArrayAdapter(Activity parent, List<Tweet> tweets) {
        Tweets = tweets;

    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TweetsArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_tweet, parent, false);
        // Return a new holder instance
        return new TweetsArrayAdapter.ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProfileImage)
        public ImageView imageView;

        @Bind(R.id.tvScreenName)
        public TextView tvScreenName;

        @Bind(R.id.tvUserName)
        public TextView tvUserName;

        @Bind(R.id.tvCreatedAt)
        public TextView tvCreatedAt;

        @Bind(R.id.tvBody)
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TweetsArrayAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Tweet tweet = Tweets.get(position);

        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreenName());
        viewHolder.tvCreatedAt.setText(tweet.getRelativeCreatedAt());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.imageView.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProileImageUrl()).transform(new RoundedCornersTransformation(7, 6)).into(viewHolder.imageView);


        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileActivity.class);
                Log.d("DEBUG", tweet.getUser().toString());
                Log.d("DEBUG", position + "");
                Log.d("DEBUG", tweet.getUser().getName());
                Log.d("DEBUG", tweet.getUser().getScreenName());
                tweet.getUser().getScreenName();
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                v.getContext().startActivity(i);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Tweets.size();
    }



}
