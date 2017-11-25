package com.mobileallin.mysongapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

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


    public AssetsSongsPresenter(MySongAppComponent component, AssetsSongsView view,
                                AssetsSongsRepositoryImpl assetsRepository)
    {
        component.inject(this);
        this.view = view;
        this.assetsRepository = assetsRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    @Override
    public void attachView(AssetsSongsView view) {
        super.attachView(view);
/*
        assetsRepository.loadJSONFromAsset(view);
*/

    }
}