package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "jUWFqbqNccvXaBOQ8sR2bFZjx";       // Change this
	public static final String REST_CONSUMER_SECRET = "eEPv1nPONSNCn9IYvXxHRGLQkwlZFKVUCYdUV1Z3aDkKf0zGii"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */

//	public void getEndPoint(String endpoint, int page, JsonHttpResponseHandler handler) {
//		switch (endpoint){
//			case "home_timeline":
//				getHomeTimeline(page, handler);
//			case "mentions_timeline":
//				getMentionsTimeline(page, handler);
//            case "user_timeline":
//                getUserTimeline(page,  handler);
//		}
//	}

	public void getHomeTimeline(int page, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count" , 25);
        int since_id = 1;
        if (page > 0){
            since_id = 25* page;
        }
        params.put("since_id", since_id);
		//execute the method
		client.get(apiUrl, params, handler);
	}

	public void getCurrentUser(JsonHttpResponseHandler handler) {
		getClient().get(getApiUrl("account/verify_credentials.json"), handler);
	}

	public void postTweet(String tweet, JsonHttpResponseHandler handler) {
		getClient().post(getApiUrl("statuses/update.json"), new RequestParams("status", tweet), handler);
	}

	public void getMentionsTimeline(int page, JsonHttpResponseHandler jsonHttpResponseHandler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count" , 25);
        int since_id = 1;
        if (page > 0){
            since_id = 25* page;
        }
        params.put("since_id", since_id);
        //execute the method
		client.get(apiUrl, params, jsonHttpResponseHandler);
	}

	public void getUserTimeline(int page, String screenName, JsonHttpResponseHandler jsonHttpResponseHandler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count" , 25);
		params.put("screen_name", screenName);
        int since_id = 1;
        if (page > 0){
            since_id = 25* page;
        }
        params.put("since_id", since_id);
        //execute the method
		client.get(apiUrl, params, jsonHttpResponseHandler);
	}

	public void getUserInfo(String screenName, JsonHttpResponseHandler jsonHttpResponseHandler){
		String apiUrl;
        RequestParams rp;
        if (screenName == null || screenName.equals("")) {
            apiUrl = getApiUrl("account/verify_credentials.json");
        } else {
            apiUrl = getApiUrl("users/show.json");
        }
        rp = new RequestParams();
        rp.put("screen_name", screenName);
        Log.d("DEBUG", "SCREEN NAME IS -->" + screenName);
        client.get(apiUrl, rp, jsonHttpResponseHandler);

	}

}
