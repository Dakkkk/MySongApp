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
        setContentView(R.layout.activity_itune_songs_list);
        ((MySongApp) getApplication()).getMySongsAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
/*
        selectFragment(item.getItemId());
*/
        switch (item.getItemId()) {
            case R.id.itunes_fragment:
                Toast.makeText(getApplicationContext(), "Itunes", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.itunes_songs_fragment,
                                new ItunesSongsFragment()).commit();
                return true;
            case R.id.assets_fragment:
                Toast.makeText(getApplicationContext(), "Assets", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.itunes_songs_fragment,
                                new AssetsSongsFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* public void selectFragment(int choosenFragmentId) {
        Fragment fragment;
        int currentFragmentId;
        if (choosenFragmentId == R.id.itunes_songs_fragment) {
            fragment = new ItunesSongsFragment();
            currentFragmentId = R.id.assets_fragment;
        } else {
            fragment = new AssetsSongsFragment();
            currentFragmentId = R.id.itunes_fragment;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(currentFragmentId, fragment);
        transaction.commit();
    }*/
}
