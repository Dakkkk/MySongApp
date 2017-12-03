package com.mobileallin.mysongapp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    interface IFragmentCreator {
        Fragment createFragment();
    }

    void addFragment(IFragmentCreator fragmentCreator) {

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(com.mobileallin.mysongapp.R.id.song_details_container) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(com.mobileallin.mysongapp.R.id.song_details_container, fragmentCreator.createFragment());
            if (false) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }
}
