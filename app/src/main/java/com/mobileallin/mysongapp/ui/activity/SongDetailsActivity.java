package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;

/**
 * Created by Dawid on 2017-11-27.
 */

public class SongDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }
}
