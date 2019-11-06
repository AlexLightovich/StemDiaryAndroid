package com.coistem.stemdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private InfoFragment infoFragment;
    private NewsFragment newsFragment;
    private ShopFragment shopFragment;
    private boolean isInfoVisible;
    private boolean isTimeTableVisible;
    private boolean isNewsVisible;
    private boolean isShopVisible;
    public static String userLogin = "";
    private int backClicks = 0;
    private SharedPreferences sp;
    RecyclerView recyclerView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    if(isNewsVisible) {

                    } else {
                        //replaced news fragment
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, newsFragment);
                        fragmentTransaction.commit();
                        isTimeTableVisible = false;
                        isShopVisible = false;
                        isNewsVisible = true;
                        isInfoVisible = false;
                    }
                    return true;
                case R.id.navigation_timetable:
                    if(isTimeTableVisible) {

                    } else {
                        // replaced timetable fragment
                        isTimeTableVisible = true;
                        isNewsVisible = false;
                        isShopVisible = false;
                        isInfoVisible = false;
                    }
                    return true;

                case R.id.navigation_shop:
                    if(isShopVisible) {

                    } else {
                        isInfoVisible = false;
                        isShopVisible = true;
                        isNewsVisible = false;
                        isTimeTableVisible = false;
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, shopFragment);
                        fragmentTransaction.commit();
                    }
                    return true;

                case R.id.navigation_info:
                    if(isInfoVisible) {

                    } else {
                        isInfoVisible = true;
                        isNewsVisible = false;
                        isShopVisible = false;
                        isTimeTableVisible = false;
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, infoFragment);
                        fragmentTransaction.commit();
                    }
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        infoFragment = new InfoFragment();
        newsFragment = new NewsFragment();
        shopFragment = new ShopFragment();
        sp = getSharedPreferences("logins",MODE_PRIVATE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.container, newsFragment);
//        fragmentTransaction.commit();
        isTimeTableVisible = false;
        isShopVisible = false;
        isNewsVisible = true;
        isInfoVisible = false;
        setToolbar("ExitUntilCollapsed");
        vkRequest();
        recyclerView = findViewById(R.id.listRecyclerView);
        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.parseJson(userLogin);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<String> imageURLs = new ArrayList<>();

    public void vkRequest() {
        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID,-113376999,"domain","coistem",VKApiConst.COUNT,6));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray items = response.json.getJSONObject("response").getJSONArray("items");
                    HashMap<String, Object> map = new HashMap<>();
                    for(int i = 1; i<6; i++) {
                        JSONObject jsonObject = items.getJSONObject(i);
                        JSONArray attachments = jsonObject.getJSONArray("attachments");
                        JSONObject jsonObject1 = attachments.getJSONObject(0);
                        if(jsonObject1.getString("type").equals("photo")) {
                            JSONObject photos = jsonObject1.getJSONObject("photo");
                            String url = photos.getString("photo_807");
                            System.out.println(url);
                            imageURLs.add(url);

                        } else {
                            imageURLs.add("nothing");
                        }
                        String text = jsonObject.getString("text");
                        System.out.println(text);
                        newsText.add(text);
                    }
                    OurData.title = new String[newsText.size()];
                    OurData.title = newsText.toArray(OurData.title);
                    OurData.imgUrls = new String[imageURLs.size()];
                    OurData.imgUrls = imageURLs.toArray(OurData.imgUrls);
                    ListAdapter listAdapter = new ListAdapter();
                    recyclerView.setAdapter(listAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    for(int i  = 0; i<OurData.title.length; i++) {
                        System.out.println("START");
                        System.out.println(OurData.title[i]);
                        System.out.println("END");
                    }
                    for(int i  = 0; i<OurData.imgUrls.length; i++) {
                        System.out.println("START");
                        System.out.println(OurData.imgUrls[i]);
                        System.out.println("END");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    public void setToolbar(@NonNull String title) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbar.setTitle(GetUserInfo.userName);
    }

    @Override
    public void onBackPressed() {
        backClicks++;
        if(backClicks==1) {
            Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();
        }
        if(backClicks>=2){
            finishAffinity();
        }
    }
}
