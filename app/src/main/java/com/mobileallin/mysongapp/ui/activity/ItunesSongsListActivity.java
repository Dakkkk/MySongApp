package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

public class ItunesSongsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        addItunesFragment();
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }

    private void addItunesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.songs_fragment_container,
                        new ItunesSongsFragment()).addToBackStack(null).commit();
    }

    private void addAssetsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.songs_fragment_container,
                        new AssetsSongsFragment()).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ToDo Replacing fragments should be done by presenter
        switch (item.getItemId()) {
            case R.id.menu_itunes_fragment:
                Toast.makeText(getApplicationContext(), "Itunes", Toast.LENGTH_SHORT).show();
                addItunesFragment();
                return true;
            case R.id.menu_assets_fragment:
                Toast.makeText(getApplicationContext(), "Assets", Toast.LENGTH_SHORT).show();
                addAssetsFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
