package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class CreateTweetActivity extends AppCompatActivity {


    public String getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    String status;
    String userName;
    User user;

    private EditText etStats;
    private TextView tvCharactersLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tweet);
        etStats = (EditText) findViewById(R.id.etStats);
        tvCharactersLeft = (TextView) findViewById(R.id.tvCharactersLeft);
        userName = getIntent().getStringExtra("screen_name");
        if (userName != null && !userName.isEmpty()) {
            etStats.setSelection(etStats.getText().length());
            etStats.setText(String.format("@" + userName + " "));
            Integer charsLeft = 140 - etStats.getText().length();
            String charsLeftString = charsLeft.toString() + " chars left";
            tvCharactersLeft.setText(charsLeftString);
        }

        etStats.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer charsLeft = 140 - etStats.getText().length();
                String charsLeftString = charsLeft.toString() + " chars left";
                tvCharactersLeft.setText(charsLeftString);
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fetchUser();
    }
    public void fetchUser() {
        TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
            }
        });
    }

    public void createTweet(View view) {
        status = etStats.getText().toString();
        Intent data = new Intent();
        data.putExtra("body", status);
        data.putExtra("user", Parcels.wrap(user));
        setResult(RESULT_OK, data);
        finish();
    }
}
