package com.mobileallin.mysongapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;

public class ItunesSongsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itune_songs_list);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }
}
