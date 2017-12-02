package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

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
import io.reactivex.subjects.PublishSubject;


@InjectViewState
public class ItuneSongsPresenter extends MvpPresenter<ItunesSongsView> {

    private static final String TAG = "SongsListPresenter";
    private ItunesSongsView mainView;
    private Disposable disposable;
    private Disposable searchDisposable;
    private List<ItunesSong> currentItuneSongsList;
    private boolean isSearching;
    private long id;

    @Inject
    ItunesSongsInteractor itunesSongsInteractor;

    @Inject
    HttpClient client;

    @Inject
    Router router;

    private List<ItunesSong> ituneSongsSearchList;

    public ItuneSongsPresenter(MySongAppComponent component, ItunesSongsView mainView) {
        component.inject(this);
        this.mainView = mainView;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(getClass().getName());
        id = args.getLong(ArgumentKeys.ID);
        Log.d("ItunesPresenter", id + "");
    }

    @Override
    public void attachView(ItunesSongsView mainView) {
        super.attachView(mainView);
        // ToDo Dispose the observer to avoid memory leaks
        mainView.showLoading();
        disposable = itunesSongsInteractor.loadSongs(mainView);
    }

    public void forceLoadSongs() {
        Log.d("forceLoadSongs()", "called");
        itunesSongsInteractor.loadSongs(mainView);
    }

    @Override
    public void detachView(ItunesSongsView view) {
        super.detachView(view);
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

    //ToDo Should interactor handle this?
    public void searchItunesSong(String searchTerm) {
        mainView.showLoading();
        ItunesSearchCall itunesSearchCall = new ItunesSearchCall(mainView, client);
        itunesSearchCall.instantSearch(searchTerm);
        mainView.hideLoading();
    }

    public void showDetails(int position) {
        Bundle args = new Bundle();
        if (isSearching) {
            currentItuneSongsList = ituneSongsSearchList;
            Log.d("ituneSongsSearchList", "NOT null");
        } else {
            Log.d("ituneSongsSearchList", "null");
            currentItuneSongsList = itunesSongsInteractor.getAllItunesSongs();
        }
        Log.d("currentItuneSongs", currentItuneSongsList.toString());

        args.putLong(ArgumentKeys.ID, currentItuneSongsList.get(position).id());
        args.putString(ArgumentKeys.TITLE, currentItuneSongsList.get(position).title());
        args.putString(ArgumentKeys.AUTHOR, currentItuneSongsList.get(position).author());
        args.putString(ArgumentKeys.RELEASE_DATE, currentItuneSongsList.get(position).releaseDate());
        args.putString(ArgumentKeys.COUNTRY, currentItuneSongsList.get(position).country());
        args.putString(ArgumentKeys.GENRE_NAME, currentItuneSongsList.get(position).genreName());
        args.putString(ArgumentKeys.COLLECTION_NAME, currentItuneSongsList.get(position).collectionName());
        args.putString(ArgumentKeys.THUMBNAIL_URL, currentItuneSongsList.get(position).thumbnailUrl());

        router.putCommand(Command.SHOW_ITUNE_SONG_DETAILS, ItunesSongDetailsPresenter.class.getName(), args);
        Log.d("showDetails, Command: ", args.getLong(ArgumentKeys.ID) + "");
        Log.d("showDetails, ID: ", args.getLong(ArgumentKeys.ID) + "");
    }

    private class ItunesSearchCall {
        private PublishSubject publishSubject;
        private HttpClient client;
        private final static String MEDIA_TYPE = "music";
        private SearchView searchView;

        public ItunesSearchCall(SearchView view, HttpClient client) {
            this.searchView = view;
            this.client = client;
        }

        //ToDo Check if this can be done better
        public void instantSearch(String searchTerm) {
            if (disposable != null) {
                disposable.dispose();
            }
            if (searchDisposable != null) {
                searchDisposable.dispose();
            }

            //doONSubscribe, afterTerminate TEst
            isSearching = true;
            searchDisposable = client.searchItunesSongs(searchTerm, MEDIA_TYPE)
                    .delay(600, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchResponse -> {
                        ituneSongsSearchList = searchResponse.allItuneSongs();
                        Log.d("instantSearch", searchResponse.toString());
                        searchView.showSearchResult(searchResponse);
                    });
        }
    }
}
