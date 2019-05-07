package com.example.narim.novaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileMediaAdapter extends RecyclerView.Adapter<ProfileMediaAdapter.myViewHolder>{
    List<Tweets> Tweets;
    Context context;
    String response;
    public ProfileMediaAdapter(List<Tweets> tweets,Context context)
    {
        this.context=context;
        getData();
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(context);
        response = m.getString("getresponse", "response");
        Log.e("WOW",response);
        this.Tweets=new ArrayList<>();
        {
            try {
                JSONObject reader = new JSONObject(response.toString());
                JSONArray array=reader.getJSONArray("novas");
                Log.e("array",String.valueOf(array.length()));
                for(int i=0; i<array.length(); i++){
                    JSONObject c = array.getJSONObject(i);
                    com.example.narim.novaa.Tweets tweet = new Tweets("","","","","","","",true);
                    tweet.Renovad=c.getBoolean("renovaed");
                    JSONObject entitiesobject=c.getJSONObject("entitiesObject");
                    JSONObject media=entitiesobject.getJSONObject("media");
                    String url=media.getString("url");
                    if(url.isEmpty()==false) {
                        tweet.ProfileName = c.getString("user_name");
                        tweet.ProfileScreenName = c.getString("user_screen_name");
                        tweet.TweetText = c.getString("text");
                        tweet.id=c.getString("_id");
                        tweet.RetweetsNumber = String.valueOf(c.getInt("renova_count"));
                        tweet.LikesNumber = String.valueOf(c.getInt("favorite_count"));
                        tweet.RepliesNumber = String.valueOf(c.getInt("reply_count"));
                        tweet.Renovad = c.getBoolean("renovaed");
                        Tweets.add(tweet);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param viewGroup
     * @param i
     * Set each recycler view item to the given item layout
     * @return
     */
    @NonNull
    @Override
    public ProfileMediaAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tweets_item,viewGroup,false);
        myViewHolder holder= new ProfileMediaAdapter.myViewHolder(row);
        return holder;
    }

    /**
     * @param myViewHolder
     * @param i
     * Set the Tweets data in the text views
     */
    @Override
    public void onBindViewHolder(@NonNull ProfileMediaAdapter.myViewHolder myViewHolder, int i) {
        Tweets tweets= Tweets.get(i);
        myViewHolder.ProfileName.setText(tweets.ProfileName);
        myViewHolder.ProfileScreenName.setText(tweets.ProfileScreenName);
        myViewHolder.TweetText.setText(tweets.TweetText);
        myViewHolder.RetweetsNumber.setText(tweets.RetweetsNumber);
        myViewHolder.RepliesNumber.setText(tweets.RepliesNumber);
        myViewHolder.LikesNumber.setText(tweets.LikesNumber);
    }

    /**
     * @return
     * This methods return the count of the given Tweets list
     */
    @Override
    public int getItemCount() {
        return Tweets.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        TextView ProfileName;
        TextView ProfileScreenName;
        TextView TweetText;
        TextView RepliesNumber;
        TextView RetweetsNumber;
        TextView LikesNumber;

        public myViewHolder(@NonNull final View itemView) {
            super(itemView);
            ProfileName=itemView.findViewById(R.id.Tweet_Name);
            ProfileScreenName= itemView.findViewById(R.id.Tweet_ScreenName);
            TweetText= itemView.findViewById(R.id.Tweet_TweetText);
            RepliesNumber=itemView.findViewById(R.id.Tweet_RepliesNumber);
            RetweetsNumber=itemView.findViewById(R.id.Tweet_RetweetsNumber);
            LikesNumber=itemView.findViewById(R.id.Tweet_LikesNumber);
        }

    }
    private void sharedResponseString(String key,String value) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = m.edit();
        editor.putString(key, value);
        editor.commit();
    };

    private void getData(){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(context);
        String screenname=m.getString("screenname","signinscreenname");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://3.19.122.178:8080/statuses/user_timeline/"+screenname,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("someOtherrrrr", response);
                        try {
                            //reader = new JSONObject(response.toString());
                            sharedResponseString("getresponse",response);
                        } catch (Exception e) {
                            Log.e("someOther", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                // params.put("token",getIntent().getStringExtra("token"));
                return new JSONObject(params).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            //---------------------------
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                //map.put("X-Device-Info","Android FOO BAR");
                // SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(ProfilePage.this);
                // String token = m.getString("signintoken", "signintoken");
                //  String screenname = m.getString("signinscreenname", "signinscreenname");
                //map.put("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1Y2JhMDA5MzliOGIxNDIwMTA4Njc3MDUiLCJzY3JlZW5fbmFtZSI6ImRvZG8iLCJpYXQiOjE1NTY5ODY4MTl9.HsfrVYwkmtOzXFr491rH04EY_ojuJixcLTRzjU03WZY");
                // map.put("screen_name",screenname);
                return map;
            }
        };


        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20 * 1000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20 * 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                error.printStackTrace();
                //commonCallBackInterface.onSuccess("ServicePl_VolleyError", "VolleyError");
            }
        });

        VolleySingelton volleySingleton = VolleySingelton.getInstance(context);
        volleySingleton.getRequestQueue().add(stringRequest);
    }
}
