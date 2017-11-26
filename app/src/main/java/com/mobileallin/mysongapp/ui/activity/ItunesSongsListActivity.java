package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

public class ItunesSongsListActivity extends AppCompatActivity {

    //ToDo Move most of the logic to presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
/*
        addAdeqateFregment();
*/
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }

    private void addAdeqateFregment() {

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

    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            return null;
        }
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
        return currentFragment;
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentBeforeBackPress = getCurrentFragment();
        // Perform the usual back action
        super.onBackPressed();
        Fragment fragmentAfterBackPress = getCurrentFragment();

   /*     int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            goBack();   // write your code to switch between fragments.
        } else {
            super.onBackPressed();
        }*/
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
