package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.INavigator;

/**
 * Created by Dawid on 2017-11-27.
 */

public class SongDetailsActivity extends BaseActivity implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }

    @Override
    public void handleCommand(Command command) {

    }
}
