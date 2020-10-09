package com.benetifi.spaceship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static FragmentManager fm;
    public static SharedPreferences preferences;
    public static int spaceship_speed;
    //public static boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("PREFS", 0);
        spaceship_speed = preferences.getInt("spaceship_speed", 15);
        //sound = preferences.getBoolean("sound", true);

        MenuFragment menuFragment = new MenuFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction ();
        ft.replace(R.id.main_container, menuFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}