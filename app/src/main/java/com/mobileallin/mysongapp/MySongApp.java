package com.mobileallin.mysongapp;

import android.app.Application;

import com.mobileallin.mysongapp.dagger.component.DaggerMySongAppComponent;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.dagger.module.AppModule;


@SuppressWarnings("WeakerAccess")
public class MySongApp extends Application {

    private MySongAppComponent mySongAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mySongAppComponent = createAppComponent();
    }

    protected MySongAppComponent createAppComponent() {
        return DaggerMySongAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public MySongAppComponent getMySongsAppComponent() {
        return mySongAppComponent;
    }
}
