package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.helper.AssertsSongsStringParser;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    @Inject
    public AssetsSongsInteractor assetsSongsInteractor;

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
        super.attachView(view);

        String songAssetsString = assetsRepository.loadJSONFromAsset(context);
        ArrayList<AssetsSong> assetsSongArrayList;

        //ToDo move from here!
        AssertsSongsStringParser assertsSongsStringParser = new AssertsSongsStringParser();
        assetsSongArrayList = assertsSongsStringParser.parseStringToAssetsSongList(songAssetsString);
        assetsSongsInteractor.loadSongs(view, assetsSongArrayList);
    }
}
