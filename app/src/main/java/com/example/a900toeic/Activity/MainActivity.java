package com.example.a900toeic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a900toeic.Adapter.MainViewPagerAdapter;
import com.example.a900toeic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_practice:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_statistic:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_test:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_review:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.nav_account:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch(position)
                {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_practice).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_statistic).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_test).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_review).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }
        });
    }

    private void addControls() {
        viewPager = findViewById(R.id.viewpager2);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        MainViewPagerAdapter mViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(mViewPagerAdapter);
    }
}