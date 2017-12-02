package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.INavigator;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongDetailsFragment;
import com.mobileallin.mysongapp.utils.ActivityUtils;

/**
 * Created by Dawid on 2017-11-29.
 */

public class AssetsSongDetailsActivity extends BaseActivity implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
        ActivityUtils.setDisplayHomeAsUpEnabled(this);
        showSongDetails();
    }

    private void showSongDetails() {
        addFragment(R.id.song_details_container, AssetsSongDetailsFragment::new, false);
    }

    //ToDo this should not be here split the interface into separate
    @Override
    public void handleCommand(Command command) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
