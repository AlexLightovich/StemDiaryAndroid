package com.coistem.stemdiary;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private InfoFragment infoFragment;
    private NewsFragment newsFragment;
    private ShopFragment shopFragment;
    private boolean isInfoVisible;
    private boolean isTimeTableVisible;
    private boolean isNewsVisible;
    private boolean isShopVisible;
    public static String userLogin = "";

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
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, newsFragment);
        fragmentTransaction.commit();
        isTimeTableVisible = false;
        isShopVisible = false;
        isNewsVisible = true;
        isInfoVisible = false;
        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.parseJson(userLogin);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
