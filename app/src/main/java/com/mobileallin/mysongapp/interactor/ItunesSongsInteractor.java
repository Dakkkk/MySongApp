package com.mobileallin.mysongapp.interactor;

import android.util.Log;

import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
import com.mobileallin.mysongapp.helper.ItunesSongTitleComparator;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.ui.view.ItunesSongsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;


public class ItunesSongsInteractor {
    private final HttpClient client;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;
    private final ItunesSongsRepository itunesSongsRepository;
    private List<ItunesSong> allItunesSongs;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ItunesSongsInteractor(ItunesSongsRepository itunesSongsRepository, HttpClient client,
                                 @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
    ) {
        this.itunesSongsRepository = itunesSongsRepository;
        this.client = client;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @SuppressWarnings("SameReturnValue")
    public Disposable loadSongs(ItunesSongsView view) {
        compositeDisposable.add(itunesSongsRepository.getSongs(client,
                ioScheduler, uiScheduler)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableMaybeObserver<ItunesResponse>() {
                    @Override
                    public void onSuccess(@NonNull ItunesResponse songs) {
                        allItunesSongs = convertToItuneSongsList(songs.allItuneSongs());
                        view.hideLoading();
                        view.displaySongs(allItunesSongs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.displayError(e);
                    }

                    @Override
                    public void onComplete() {
                        //The case when we got response from server, but it contains no body
                        view.displayNoSongs();
                        Log.d("onComplete", "called");
                    }
                }));
        return null;
    }

    public List<ItunesSong> getAllItunesSongs() {
        return allItunesSongs;
    }

    private List<ItunesSong> convertToItuneSongsList(@NonNull List<ItunesSong> itunesSongs) {
        List<ItunesSong> convertedItuneSongsList = new ArrayList<>();
        for (int i = 0; i < itunesSongs.size(); i++) {
            ItunesSong convertedItuneSong = createItuneSong(itunesSongs, i);
            convertedItuneSongsList.add(i, convertedItuneSong);
            Collections.sort(convertedItuneSongsList, new ItunesSongTitleComparator());
        }
        return convertedItuneSongsList;
    }

    private ItunesSong createItuneSong(List<ItunesSong> itunesSongs, int currentIteration) {
        ItunesSong currentSongI = itunesSongs.get(currentIteration);
        String title = currentSongI.title();
        String author = currentSongI.author();
        String releaseDate = currentSongI.releaseDate();
        String genre = currentSongI.genreName();
        String collectionName = currentSongI.collectionName();
        String country = currentSongI.country();
        String thumbnailUrl = currentSongI.thumbnailUrl();

        ItunesSongsFactory itunesSongsFactory = new ItunesSongsFactory(currentIteration, title,
                author, releaseDate, genre, collectionName, country, thumbnailUrl);
        return itunesSongsFactory.buildItunesSong();
    }
}
