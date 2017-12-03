package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.view.BaseAssetsDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import javax.inject.Inject;


public class AssetsSongDetailsPresenter extends MvpPresenter<BaseAssetsDetailsView> {

    @SuppressWarnings("WeakerAccess")
    @Inject
    Router router;

    public AssetsSongDetailsPresenter(MySongAppComponent component) {
        component.inject(this);
    }

    @Override
    public void attachView(BaseAssetsDetailsView view) {
        super.attachView(view);
        view.showSongDetails(getAssetsSongDetails());
    }

    @SuppressWarnings("WeakerAccess")
    public AssetsSong getAssetsSongDetails() {
        Bundle routerSongBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        return createAssetsSong(routerSongBundle);
    }

    @SuppressWarnings("WeakerAccess")
    public AssetsSong createAssetsSong(Bundle routerSongBundle) {
        long id = (long) routerSongBundle.get(ArgumentKeys.ID);
        String title = (String) routerSongBundle.get(ArgumentKeys.TITLE);
        String author = (String) routerSongBundle.get(ArgumentKeys.AUTHOR);
        String releaseDate = (String) routerSongBundle.get(ArgumentKeys.RELEASE_DATE);
        String first = (String) routerSongBundle.get(ArgumentKeys.FIRST);
        String year = (String) routerSongBundle.get(ArgumentKeys.YEAR);
        String playCount = (String) routerSongBundle.get(ArgumentKeys.PLAY_COUNT);

        AssetsSongFactory assetsSongFactory = new AssetsSongFactory(id, title, author,
                releaseDate, first, year, playCount);
        return assetsSongFactory.buildAssetsSong();
    }

    public String getSongTitle() {
        Bundle routerSongBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        return (String) routerSongBundle.get(ArgumentKeys.TITLE);
    }
}