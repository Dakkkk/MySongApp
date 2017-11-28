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
import com.mobileallin.mysongapp.ui.view.SearchView;
import com.mobileallin.mysongapp.ui.view.SongsListView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


@InjectViewState
public class ItuneSongsPresenter extends MvpPresenter<SongsListView> {

    private static final String TAG = "SongsListPresenter";
    private List<ItunesSong> songsList;
    private SongsListView view;
    private Disposable disposable;
    private Disposable searchDisposable;

    private long id;


    @Inject
    ItunesSongsInteractor itunesSongsInteractor;

    @Inject
    HttpClient client;

    @Inject
    Router router;

    public ItuneSongsPresenter(MySongAppComponent component, SongsListView view) {
        component.inject(this);
        this.view = view;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(getClass().getName());
        id = args.getLong(ArgumentKeys.ID);
    }

    @Override
    public void attachView(SongsListView view) {
        super.attachView(view);
        // ToDo Dispose the observer to avoid memory leaks
        disposable = itunesSongsInteractor.loadSongs(view);


    }

    @Override
    public void detachView(SongsListView view) {
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

    public void searchItunesSong(String searchTerm) {
        ItunesSearchCall itunesSearchCall = new ItunesSearchCall(view, client);
        itunesSearchCall.instantSearch(searchTerm);
/*
        songsInteractor.searchItunesSongs(searchTerm);
*/
    }

    public void showDetails(int position) {
        Bundle args = new Bundle();
        args.putLong(ArgumentKeys.ID, itunesSongsInteractor.getAllItunesSongs().get(position).id());
        router.putCommand(Command.SHOW_ITUNE_SONG_DETAILS, ItunesSongDetailsPresenter.class.getName(), args);
        Log.d("showDetails, Command: ", args.getLong(ArgumentKeys.ID) + "");
        Log.d("showDetails, ID: ", args.getLong(ArgumentKeys.ID) + "");
    }


    public class ItunesSearchCall {

        private PublishSubject publishSubject;
        private HttpClient client;
        private final static String MEDIA_TYPE = "music";
        private SearchView view;


        public ItunesSearchCall(SearchView view, HttpClient client) {
            this.view = view;
            this.client = client;
        }

        public void instantSearch(String searchTerm) {
            if (disposable != null) {
                disposable.dispose();
            }
            if (searchDisposable != null) {
                searchDisposable.dispose();
            }
            searchDisposable = client.searchItunesSongs(searchTerm, MEDIA_TYPE)
                    .delay(600, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchResponse -> {
                        Log.d("instantSearch", searchResponse.toString());
                        view.showSearchResult(searchResponse);
                    });

        }
    }
}
