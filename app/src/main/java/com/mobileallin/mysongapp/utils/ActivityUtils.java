package com.mobileallin.mysongapp.utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


public class ActivityUtils {

    public static void setDisplayHomeAsUpEnabled(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
