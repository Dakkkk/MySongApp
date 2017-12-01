package com.mobileallin.mysongapp.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dawid on 2017-11-28.
 */

public class BaseActivity extends AppCompatActivity {

    protected interface IFragmentCreator {
        Fragment createFragment();
    }

    //ToDo decide if we have time to play around with this, or delete
    protected boolean isTablet;
    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*isTablet = checkConfiguration();*/
    }

 /*   protected boolean checkConfiguration(){
        return getResources().getBoolean(R.bool.is_tablet);
    }*/

    //ToDo decide if we have time to play around with this, or delete
    protected void setOrientation(boolean isTablet) {
        int orientation = isTablet ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE :
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
    }

    protected void addFragment(@IdRes int containerId, IFragmentCreator fragmentCreator,
                               boolean addToBackStack) {

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(containerId) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(containerId, fragmentCreator.createFragment());
            if (addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }
}
