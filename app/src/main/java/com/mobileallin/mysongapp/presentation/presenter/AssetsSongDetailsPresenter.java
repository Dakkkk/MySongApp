package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.interactor.SongDetailsInteractor;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongDetailsFragment;
import com.mobileallin.mysongapp.ui.view.BaseAssetsDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import javax.inject.Inject;


public class AssetsSongDetailsPresenter extends MvpPresenter<BaseAssetsDetailsView> {

    @Inject
    Router router;

    @Inject
    SongDetailsInteractor detailsInteractor;

    private long songId;
    private int position;
    private AssetsSong chosenSong;
    private AssetsSongDetailsFragment view;

    public AssetsSongDetailsPresenter(MySongAppComponent component, AssetsSongDetailsFragment view) {
        component.inject(this);
        this.view = view;
    }

    public void init(long songId) {
        this.songId = songId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d("Dp onFirstViewAttach", "called!");
        Bundle args = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        if (args != null) {
            Log.d("DPargs", args.toString());
            songId = args.getLong(ArgumentKeys.ID);
            position = args.getInt(ArgumentKeys.POSITION);
        }
        System.out.print("DetailsPresenter: " + songId + ", " + position);
        Log.d("DetailsPresenter, song:", getSongTitle());
    }

    @Override
    public void attachView(BaseAssetsDetailsView view) {
        super.attachView(view);
        Log.d("DetailPresenter, song:", getSongTitle());
        view.showSongDetails(getAssetsSongDetails());
    }

    //ToDO Rewrite (maybe create separate class and function for building Itunes/Assets songs)
    public AssetsSong getAssetsSongDetails() {
        Bundle routerSongBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        long id = (long) routerSongBundle.get(ArgumentKeys.ID);
        String title = (String) routerSongBundle.get(ArgumentKeys.TITLE);
        String author = (String) routerSongBundle.get(ArgumentKeys.AUTHOR);
        String releaseDate = (String)  routerSongBundle.get(ArgumentKeys.RELEASE_DATE);
        String first = (String)  routerSongBundle.get(ArgumentKeys.FIRST);
        String year = (String)  routerSongBundle.get(ArgumentKeys.YEAR);
        String playCount = (String)  routerSongBundle.get(ArgumentKeys.PLAY_COUNT);


        AssetsSongFactory assetsSongFactory = new AssetsSongFactory(id, title, author,
                releaseDate, first, year, playCount);
        AssetsSong assetsSong = assetsSongFactory.buildAssetsSong();

        /*AssetsSong assetsSong = AssetsSong.builder()
                .setId(id)
                .setAuthor(author)
                .setTitle(title)
                .setReleaseDate(releaseDate)
                .build();*/
        return assetsSong;
    }

    public String getSongTitle() {
        Bundle routerSongBundle = router.getArguments(AssetsSongDetailsPresenter.class.getName());
        return (String) routerSongBundle.get(ArgumentKeys.TITLE);
    }
}