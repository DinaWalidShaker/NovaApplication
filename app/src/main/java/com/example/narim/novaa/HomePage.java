package com.example.narim.novaa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView Home;
    ImageView Search;
    ImageView Profile;
    ImageView Notifications;
    ImageView PostTweet;
    RecyclerView TweetRecView;
    List<Tweets> tweets= new ArrayList<>();
    ImageView nav_photo_opener ;
    DrawerLayout drawer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Home = findViewById(R.id.Icon_Home_Home);
        Search = findViewById(R.id.Icon_Home_Search);
        Profile = findViewById(R.id.Icon_Home_Profile);
        Notifications = findViewById(R.id.Icon_Home_Notifs);
        PostTweet = findViewById(R.id.Icon_Home_PostTweet);
        TweetRecView = findViewById(R.id.RecyclerView_Home_Tweets);
         nav_photo_opener = findViewById(R.id.image_Home_Photo);
      //   drawer = findViewById(R.id.drawer_layout);
      //  NavigationView nav_view = findViewById(R.id.nav_view);

        String Name[] = {"Dina Walid", "Menna Walid", "Arwa Walid", "Ziad Walid"};
        String ScreenName[] = {"dinawalid", "mennawalid", "arwawalid", "ziadwalid"};
        String TweetText[] = {"Hi, I'm dina", "Hi, I'm menna", "Hi, I'm arwa", "Hi, I'm ziad"};
        String RetweetNumber[] = {"1", "2", "3", "4"};
        String RepliesNumber[] = {"1", "2", "3", "4"};
        String LikesNumber[] = {"1", "2", "3", "4"};
        /*for (int i = 0; i < Name.length; i++) {
            Tweets tweet = new Tweets(Name[i], ScreenName[i], TweetText[i], RetweetNumber[i], RepliesNumber[i], LikesNumber[i],false);
            tweets.add(tweet);
        }*/
        TweetRecView.setLayoutManager(new LinearLayoutManager(HomePage.this));
        TweetsAdapter tweetsAdapter = new TweetsAdapter(this);
        TweetRecView.setAdapter(tweetsAdapter);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, ProfilePage.class);
                startActivity(i);
                finish();

            }
        });
        Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, NotificationsPage.class);
                startActivity(intent);
                finish();
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Search.class);
                startActivity(intent);
                finish();
            }
        });
        PostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTweet alertDialog = new PostTweet();
                FragmentManager f = getSupportFragmentManager();
                alertDialog.show(f, "fragment_alert");
            }

        });

        nav_photo_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.START)) {
                    drawer.closeDrawer(Gravity.START);
                } else {
                    drawer.openDrawer(Gravity.START);
                }
            }
        });
        //nav_view.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_profile) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent i =new Intent(HomePage.this,ProfilePage.class);
            startActivity(i);
        } else if (id == R.id.nav_followers) {
            Intent i =new Intent(HomePage.this,Followers.class);
            startActivity(i);
        }
        else if (id == R.id.nav_following) {
            Intent i =new Intent(HomePage.this,Following.class);
            startActivity(i);
        }
        else if (id == R.id.nav_editprofile) {
            Intent i =new Intent(HomePage.this,EditProfile.class);
            startActivity(i);
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void sharedResponseString(String key,String value) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString(key, value);
        editor.commit();
    }
    String url="http://3.19.122.178:8080/statuses/timeline";
    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject reader = null;
                        Log.e("someOtherrrrr", response);
                        try {
                            reader = new JSONObject(response.toString());
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
                SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
                String token = m.getString("signintoken", "signintoken");
                String screenname = m.getString("signinscreenname", "signinscreenname");
                map.put("token",token);
                map.put("screen_name",screenname);
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

    }
}
