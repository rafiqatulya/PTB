package com.example.drplasma2;

import static android.text.TextUtils.replace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

//    BottomNavigationView bottomNavigation;
    AnimatedBottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar= findViewById(R.id.bottom_bar);

        //set default fragment

        replace(new HomeFragment());

        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {

                if (tab1.getId()==R.id.nav_home) {
                    replace(new HomeFragment());
                } else if (tab1.getId()==R.id.nav_event) {
                    replace(new DonorMasukFragment());
                } else if (tab1.getId()==R.id.nav_history) {
                    replace(new RiwayatUtamaFragment());
                } else if (tab1.getId()==R.id.nav_profile) {
                    replace(new ProfilFragment());
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });

//        bottomNavigation = findViewById(R.id.bottom_bar);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//
//
//        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//
//                if (item.getItemId() == R.id.nav_home) {
//                    selectedFragment = new HomeFragment();
//                } else if (item.getItemId() == R.id.nav_event) {
//                    selectedFragment = new EventFragment();
//                } else if (item.getItemId() == R.id.nav_history) {
//                    selectedFragment = new HistoryFragment();
//                } else if (item.getItemId() == R.id.nav_profile) {
//                    selectedFragment = new ProfileFragment();
//                }
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
//
//                return true;
//            }
//        });

    }
    private void replace (Fragment fragment)  {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}