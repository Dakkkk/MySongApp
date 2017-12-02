package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.view.BaseItunesDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import javax.inject.Inject;

@InjectViewState
public class ItunesSongDetailsPresenter extends MvpPresenter<BaseItunesDetailsView> {

    @Inject
    Router router;

    public ItunesSongDetailsPresenter(MySongAppComponent component) {
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void attachView(BaseItunesDetailsView view) {
        super.attachView(view);
        view.showSongDetails(getItunesSongDetails());
    }

    public ItunesSong getItunesSongDetails() {
        Bundle routerSongBundle = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        return createItunesSongFromBundle(routerSongBundle);
    }

    public ItunesSong createItunesSongFromBundle(Bundle routerSongBundle){
        long id = (long) routerSongBundle.get(ArgumentKeys.ID);
        String title = (String) routerSongBundle.get(ArgumentKeys.TITLE);
        String author = (String) routerSongBundle.get(ArgumentKeys.AUTHOR);
        String releaseDate = (String) routerSongBundle.get(ArgumentKeys.RELEASE_DATE);
        String genreName = (String) routerSongBundle.get(ArgumentKeys.GENRE_NAME);
        String collectionName = (String) routerSongBundle.get(ArgumentKeys.COLLECTION_NAME);
        String thumbnailUrl = (String) routerSongBundle.get(ArgumentKeys.THUMBNAIL_URL);
        String country = (String) routerSongBundle.get(ArgumentKeys.COUNTRY);

        ItunesSongsFactory itunesSongsFactory = new ItunesSongsFactory(id, title, author,
                releaseDate, genreName, collectionName, country, thumbnailUrl);
        ItunesSong itunesSong = itunesSongsFactory.buildItunesSong();
        return itunesSong;
    }

    public String getSongTitle() {
        Bundle routerSongBundle = router.getArguments(ItunesSongDetailsPresenter.class.getName());
        return (String) routerSongBundle.get(ArgumentKeys.TITLE);
    }
}
