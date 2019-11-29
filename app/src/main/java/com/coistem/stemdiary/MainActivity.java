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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKSdk;
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
    private ModerationFragment moderationFragment;
    private boolean isInfoVisible;
    private boolean isTimeTableVisible;
    private boolean isNewsVisible;
    private boolean isShopVisible;
    private boolean isModerationVisible;
    public static String userLogin = "";
    private int backClicks = 0;
    private static SharedPreferences sp;


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
                        fragmentTransaction.replace(R.id.main_content, newsFragment);
                        fragmentTransaction.commit();
                        isTimeTableVisible = false;
                        isModerationVisible = false;
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
                        isModerationVisible = false;
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
                        isModerationVisible = false;
                        isTimeTableVisible = false;
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, shopFragment);
                        fragmentTransaction.commit();
                    }
                    return true;

                case R.id.navigation_info:
                    if(isInfoVisible) {

                    } else {
                        isInfoVisible = true;
                        isNewsVisible = false;
                        isShopVisible = false;
                        isModerationVisible = false;
                        isTimeTableVisible = false;
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, infoFragment);
                        fragmentTransaction.commit();
                    }
                    return true;

                case R.id.navigation_moderation:
                    if(isModerationVisible) {

                    } else {
                        isInfoVisible = false;
                        isModerationVisible = true;
                        isNewsVisible = false;
                        isShopVisible = false;
                        isTimeTableVisible = false;
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_content, moderationFragment);
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
        moderationFragment = new ModerationFragment();
        sp = getSharedPreferences("logins",MODE_PRIVATE);
        GetUserInfo.avatarUrl = sp.getString("avatarUrl","null");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_content, newsFragment);
        fragmentTransaction.commit();
        isTimeTableVisible = false;
        isShopVisible = false;
        isNewsVisible = true;
        isModerationVisible = false;
        isInfoVisible = false;
        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.prepareJsonFile(MainActivity.this,userLogin);
//        if(!GetUserInfo.userAccessType.equals("admin")) {
//            navView.getMenu().removeItem(R.id.navigation_moderation);
//        }
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void savePreferences(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
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
