package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    protected interface IFragmentCreator {
        Fragment createFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
