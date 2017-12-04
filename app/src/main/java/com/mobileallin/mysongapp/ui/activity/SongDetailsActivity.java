package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongDetailsFragment;
import com.mobileallin.mysongapp.utils.ActivityUtils;


public class SongDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
        ActivityUtils.setDisplayHomeAsUpEnabled(this);
        showSongDetails();
    }

    private void showSongDetails() {
        addFragment(ItunesSongDetailsFragment::new);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
