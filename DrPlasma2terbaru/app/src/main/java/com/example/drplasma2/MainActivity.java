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

    AnimatedBottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar= findViewById(R.id.bottom_bar);


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

    }
    private void replace (Fragment fragment)  {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}