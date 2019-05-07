package com.example.narim.novaa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {
    String screenname;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        screenname = m.getString("screenname", "screenname");
        name = m.getString("signinname", "name");
       // getData();
        final TabLayout ProfilePage_TabLayout = findViewById(R.id.TabLayout_Profile);
        ViewPager ProfilePage_ViewPage = findViewById(R.id.ViewPager_Profile);
        TextView Back=findViewById(R.id.Textview_Profile_Back);
        ProfilePage_Adapter adapter = new ProfilePage_Adapter(getSupportFragmentManager());
        adapter.AddFragment("one", new profile_page_tweets());
        adapter.AddFragment("two", new profile_page_retweets());
        adapter.AddFragment("three", new profile_page_likes());
        adapter.AddFragment("four", new profile_page_media());

        ProfilePage_TabLayout.setupWithViewPager(ProfilePage_ViewPage);
        ProfilePage_ViewPage.setAdapter(adapter);
        ProfilePage_TabLayout.getTabAt(0).setCustomView(R.layout.tablayout_profile_page_tweets);
        ProfilePage_TabLayout.getTabAt(1).setCustomView(R.layout.tablayout_profile_page_retweets);
        ProfilePage_TabLayout.getTabAt(2).setCustomView(R.layout.tablayout_profile_page_likes);
        ProfilePage_TabLayout.getTabAt(3).setCustomView(R.layout.tablayout_profile_page_media);

        Button EditProfile = findViewById(R.id.Button_Profile_EditProfile);
        Button Following = findViewById(R.id.Button_Profile_Following);
        Button Followers = findViewById(R.id.Button_Profile_Followers);
        TextView SignOut=findViewById(R.id.Textview_Profile_SignOut);
        TextView ScreenName=findViewById(R.id.TextView_Profile_Name);
        TextView UserName=findViewById(R.id.TextView_Profile_UserName);
        ScreenName.setText(screenname);
        UserName.setText(name);

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfilePage.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this,EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

        Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this,Followers.class);
                startActivity(intent);
                finish();
            }
        });

        Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this,Following.class);
                startActivity(intent);
                finish();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfilePage.this,HomePage.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void sharedResponseString(String key,String value) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString(key, value);
        editor.commit();
    };
   /* private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://3.19.122.178:8080/statuses/user_timeline/dodo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("someOtherrrrr", response);
                        try {
                           //reader = new JSONObject(response.toString());
                           sharedResponseString("response",response);
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

        VolleySingelton volleySingleton = VolleySingelton.getInstance(this);
        volleySingleton.getRequestQueue().add(stringRequest);

    }*/
}
