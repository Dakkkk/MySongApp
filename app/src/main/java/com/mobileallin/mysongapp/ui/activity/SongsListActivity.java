package com.mobileallin.mysongapp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.INavigator;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongDetailsPresenter;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;
import com.mobileallin.mysongapp.utils.Keys;

import javax.inject.Inject;


public class SongsListActivity extends BaseActivity implements INavigator {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @Inject
    Router router;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @Inject
    SharedPreferences sharedPreferences;

    private Bundle assetsDetailBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
        displayIntroDialog(isAppFirstLaunch());
        choseFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        router.attachToNavigator(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_itunes_fragment:
                Toast.makeText(getApplicationContext(), "Itunes", Toast.LENGTH_SHORT).show();
                addItunesFragment();
                return true;
            case R.id.menu_assets_fragment:
                Toast.makeText(getApplicationContext(), "Assets", Toast.LENGTH_SHORT).show();
                replaceFragment(AssetsSongsFragment.newInstance()
                );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleCommand(Command command) {
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

    private void displayIntroDialog(boolean isFirsLaunch) {
        if (!isFirsLaunch) return;
        AlertDialog alertDialog = new AlertDialog.Builder(SongsListActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.introduction));
        alertDialog.setIcon(R.mipmap.app_launch_icon);
        alertDialog.setMessage(getResources().getString(R.string.intro_dialog_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                getResources().getString(R.string.dismiss),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean isAppFirstLaunch() {
        boolean firstRun = sharedPreferences.getBoolean("firstRun", true);
        if (firstRun) {
            sharedPreferences
                    .edit()
                    .putBoolean("firstRun", false)
                    .apply();
        }
        return firstRun;
    }

    private void choseFragment() {
        assetsDetailBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        if (assetsDetailBundle != null &&
                assetsDetailBundle.get(Keys.IS_ASSETS_SONG) == Keys.IS_ASSETS_SONG) {
            addAssetsFragment();
        } else {
            addItunesFragment();
        }
    }

    private void addItunesFragment() {
        removeAssetsFlag();
        replaceFragment(ItunesSongsFragment.newInstance()
        );
    }

    private void removeAssetsFlag() {
        assetsDetailBundle.remove(Keys.IS_ASSETS_SONG);
    }

    private void addAssetsFragment() {
        removeAssetsFlag();
        replaceFragment(AssetsSongsFragment.newInstance()
        );
    }

    private void showSongDetails() {
        Intent intent = new Intent(this, SongDetailsActivity.class);
        startActivity(intent);
    }

    private void showAssetsSongDetails() {
        Intent intent = new Intent(this, AssetsSongDetailsActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.songs_fragment_container, f);
        ft.commit();
    }
}
