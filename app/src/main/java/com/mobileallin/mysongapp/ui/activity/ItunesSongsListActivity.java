package com.mobileallin.mysongapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getApplicationContext(), "Itunes", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(getApplicationContext(), "Assets", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content,
                                new AssetsSongsFragment()).commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
