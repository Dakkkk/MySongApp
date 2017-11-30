package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.interactor.SongDetailsInteractor;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongDetailsFragment;
import com.mobileallin.mysongapp.ui.view.BaseItunesDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import javax.inject.Inject;

@InjectViewState
public class ItunesSongDetailsPresenter extends MvpPresenter<BaseItunesDetailsView> {

    @Inject
    Router router;

    @Inject
    SongDetailsInteractor detailsInteractor;


    private long songId;
    private int position;
    private ItunesSong chosenSong;
    private ItunesSongDetailsFragment view;

    public ItunesSongDetailsPresenter(MySongAppComponent component, ItunesSongDetailsFragment view){
        component.inject(this);
        this.view = view;
    }

    public void init(long songId){
        this.songId = songId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        Log.d("Dp onFirstViewAttach", "called!");

        Bundle args = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        if (args != null){
            Log.d("DPargs", args.toString());
            songId = args.getLong(ArgumentKeys.ID);
            position = args.getInt(ArgumentKeys.POSITION);
        }
/*
        System.out.print("DetailsPresenter: " + songId + ", " + position);
*/
/*
        Log.d("DetailsPresenter, song:", getSongTitle().toString());
*/

    }

    @Override
    public void attachView(BaseItunesDetailsView view) {
        super.attachView(view);

        Log.d("DetailPresenter, song:", getSongTitle());
        view.showSongDetails(getItunesSongDetails());

    }

    //ToDO Rewrite (maybe create separate class and function for building Itunes/Assets songs)
    public ItunesSong getItunesSongDetails() {
        Bundle routerSongBundle = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        long id = (long) routerSongBundle.get(ArgumentKeys.ID);
        String title = (String) routerSongBundle.get(ArgumentKeys.TITLE);
        String author = (String) routerSongBundle.get(ArgumentKeys.AUTHOR);
        String releaseDate = (String) routerSongBundle.get(ArgumentKeys.RELEASE_DATE);
        String genreName = (String) routerSongBundle.get(ArgumentKeys.GENRE_NAME);
        String collectionName = (String) routerSongBundle.get(ArgumentKeys.COLLECTION_NAME);
        String thumbnailUrl = (String) routerSongBundle.get(ArgumentKeys.THUMBNAIL_URL);
        String country = (String) routerSongBundle.get(ArgumentKeys.COUNTRY);

        ItunesSong itunesSong = ItunesSong.builder()
                .setId(id)
                .setAuthor(author)
                .setTitle(title)
                .setReleaseDate(releaseDate)
                .setCollectionName(collectionName)
                .setCountry(country)
                .setGenreName(genreName)
                .setThumbnailUrl(thumbnailUrl)
                .build();

        return itunesSong;
    }

    public String getSongTitle() {
        Bundle routerSongBundle = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        return (String) routerSongBundle.get(ArgumentKeys.TITLE);
    }

}
