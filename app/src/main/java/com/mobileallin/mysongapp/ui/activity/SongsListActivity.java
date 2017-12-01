package com.mobileallin.mysongapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.INavigator;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongDetailsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.ItunesSongDetailsPresenter;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

import javax.inject.Inject;

public class SongsListActivity extends BaseActivity implements INavigator {

    //ToDo Move most of the logic to presenter
    @Inject
    Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);

        Bundle assetsDetailBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        Bundle itunesDetailBundle = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        if (assetsDetailBundle != null && !assetsDetailBundle.isEmpty()) {
            replaceFragment(R.id.songs_fragment_container, AssetsSongsFragment.newInstance(2), true, "assetsFragment");
        } else if (itunesDetailBundle != null && !itunesDetailBundle.isEmpty()) {
            replaceFragment(R.id.songs_fragment_container, ItunesSongsFragment.newInstance(1), true, "itunessFragment");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        router.attachToNavigator(this);
        Log.d(getClass().getSimpleName(), router.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                //ToDo Create method out of this?, we don' need id in the newInstance method - rewrite
                replaceFragment(R.id.songs_fragment_container, ItunesSongsFragment.newInstance(1), true, "itunessFragment");
                return true;
            case R.id.menu_assets_fragment:
                Toast.makeText(getApplicationContext(), "Assets", Toast.LENGTH_SHORT).show();
                replaceFragment(R.id.songs_fragment_container, AssetsSongsFragment.newInstance(2), true, "assetsFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleCommand(Command command) {
        Log.d("handleCommand", command.toString());
        switch (command) {
            case SHOW_ITUNE_SONG_DETAILS: {
                showSongDetails();
                break;
            }
            case SHOW_ASSETS_SONG_DETAILS: {
                showAssetsSongDetails();
                break;
            }
            default:
        }
    }

    private void showSongDetails() {
        Log.d("showSongDetails", "called");
        Intent intent = new Intent(this, SongDetailsActivity.class);
        startActivity(intent);
    }

    private void showAssetsSongDetails() {
        Log.d("showAssetsSongDetails", "called");
        Intent intent = new Intent(this, AssetsSongDetailsActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(@IdRes int containerId, Fragment f, boolean addToBackStack, String fragmentTag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();
    }
}
