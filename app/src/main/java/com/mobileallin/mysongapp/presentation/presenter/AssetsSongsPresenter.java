package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dawid on 2017-11-25.
 */

@InjectViewState
public class AssetsSongsPresenter extends MvpPresenter<AssetsSongsView> {

    private static final String TAG = "AssetsSongsPresenter";
    private List<Song> songsList;
    private AssetsSongsView view;
    private AssetsSongsRepositoryImpl assetsRepository;
    private Context context;

    //ToDo to many arguments, get rid of the context...
    public AssetsSongsPresenter(MySongAppComponent component, AssetsSongsView view,
                                AssetsSongsRepositoryImpl assetsRepository, Context context) {
        component.inject(this);
        this.view = view;
        this.assetsRepository = assetsRepository;
        this.context = context;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    @Override
    public void attachView(AssetsSongsView view) {

        //ToDo pass this logic to some helper class

        super.attachView(view);

        String songAssetsString = assetsRepository.loadJSONFromAsset(context);
        Log.d("songAssetsString", songAssetsString + "boom");

        JSONArray jsonarray;
        try {
            jsonarray = new JSONArray(songAssetsString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("Song Clean");
                String author = jsonobject.getString("ARTIST CLEAN");
                Log.d("songAssetsParsed", name + " " + author);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
