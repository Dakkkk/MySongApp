package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.interactor.ItunesSongsInteractor;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.ui.view.ItunesSongsView;
import com.mobileallin.mysongapp.ui.view.SearchView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ItunesSongsPresenter extends MvpPresenter<ItunesSongsView> {

    private final ItunesSongsView mainView;
    private Disposable disposable;
    private Disposable searchDisposable;
    private List<ItunesSong> currentItuneSongsList;
    private boolean isSearching;

    @SuppressWarnings("WeakerAccess")
    @Inject
    ItunesSongsInteractor itunesSongsInteractor;

    @SuppressWarnings("WeakerAccess")
    @Inject
    HttpClient client;

    @SuppressWarnings("WeakerAccess")
    @Inject
    Router router;

    private List<ItunesSong> ituneSongsSearchList;

    public ItunesSongsPresenter(MySongAppComponent component, ItunesSongsView mainView) {
        component.inject(this);
        this.mainView = mainView;
    }

    @Override
    public void attachView(ItunesSongsView mainView) {
        super.attachView(mainView);
        mainView.showLoading();
        if (isSearching) {
            mainView.hideLoading();
            return;
        }
        disposable = itunesSongsInteractor.loadSongs(this);
    }

    public void displaySongs(List<ItunesSong> allItunesSongs) {
        mainView.hideLoading();
        mainView.displaySongs(allItunesSongs);
    }

    public void displayError(Throwable e) {
        mainView.hideLoading();
        mainView.displayError(e);
    }

    public void displayNoSongs() {
        mainView.displayNoSongs();
    }

    public void forceLoadSongs() {
        if (!isSearching) {
            itunesSongsInteractor.loadSongs(this);
        }
    }

    @Override
    public void detachView(ItunesSongsView view) {
        super.detachView(view);
        disposeAll();
    }

    private void disposeAll() {
        if (disposable == null && searchDisposable == null) return;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
            searchDisposable = null;
        }
    }

    public void searchItunesSong(String searchTerm) {
        mainView.showLoading();
        ItunesSearchCall itunesSearchCall = new ItunesSearchCall(mainView, client);
        itunesSearchCall.instantSearch(searchTerm);
        mainView.hideLoading();
    }

    public void showDetails(int position) {
        if (isSearching) {
            currentItuneSongsList = ituneSongsSearchList;
        } else {
            currentItuneSongsList = itunesSongsInteractor.getAllItunesSongs();
        }

        if (currentItuneSongsList.isEmpty()) {
            return;
        }
        putSongToBundle(position);
    }

    private void putSongToBundle(int position) {
        Bundle args = new Bundle();
        args.putLong(ArgumentKeys.ID, currentItuneSongsList.get(position).id());
        args.putString(ArgumentKeys.TITLE, currentItuneSongsList.get(position).title());
        args.putString(ArgumentKeys.AUTHOR, currentItuneSongsList.get(position).author());
        args.putString(ArgumentKeys.RELEASE_DATE, currentItuneSongsList.get(position).releaseDate());
        args.putString(ArgumentKeys.COUNTRY, currentItuneSongsList.get(position).country());
        args.putString(ArgumentKeys.GENRE_NAME, currentItuneSongsList.get(position).genreName());
        args.putString(ArgumentKeys.COLLECTION_NAME, currentItuneSongsList.get(position).collectionName());
        args.putString(ArgumentKeys.THUMBNAIL_URL, currentItuneSongsList.get(position).thumbnailUrl());
        router.putCommand(Command.SHOW_ITUNE_SONG_DETAILS, ItunesSongDetailsPresenter.class.getName(), args);
    }

    private class ItunesSearchCall {
        private final HttpClient client;
        private final static String MEDIA_TYPE = "music";
        private final SearchView searchView;

        public ItunesSearchCall(SearchView view, HttpClient client) {
            this.searchView = view;
            this.client = client;
        }

        public void instantSearch(String searchTerm) {
            disposeAll();
            isSearching = true;
            searchDisposable = client.searchItunesSongs(searchTerm, MEDIA_TYPE)
                    .delay(100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchResponse -> {
                        ituneSongsSearchList = searchResponse.allItuneSongs();
                        searchView.showSearchResult(searchResponse);
                    });
        }
    }
}
