package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.INavigator;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongDetailsFragment;
import com.mobileallin.mysongapp.utils.ActivityUtils;


public class SongDetailsActivity extends BaseActivity implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
        ActivityUtils.setDisplayHomeAsUpEnabled(this);
        showSongDetails();
    }

    private void showSongDetails() {

        addFragment(R.id.song_details_container, ItunesSongDetailsFragment::new, false);
    }

    @Override
    public void handleCommand(Command command) {

    }
}
