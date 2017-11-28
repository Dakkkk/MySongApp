package com.mobileallin.mysongapp.interactor;

import android.util.Log;

import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.ui.view.SongsListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;


public class ItunesSongsInteractor {
    private HttpClient client;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private ItunesSongsRepository itunesSongsRepository;

    private List<ItunesSong> allItunesSongs;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ItunesSongsInteractor(ItunesSongsRepository itunesSongsRepository, HttpClient client,
                                 @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
    ) {
        this.itunesSongsRepository = itunesSongsRepository;
        this.client = client;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Disposable loadSongs(SongsListView view) {

        compositeDisposable.add(itunesSongsRepository.getSongs(client,
                ioScheduler, uiScheduler)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableMaybeObserver<ItunesResponse>() {
                    @Override
                    public void onSuccess(@NonNull ItunesResponse songs) {
                        Log.d("loadSongs", songs.toString());
/*
                        allItunesSongs = songs.allItuneSongs();
*/
                        allItunesSongs = convertToItuneSongsList(songs.allItuneSongs());

                        Log.d("allItunesSongs", "2: " + allItunesSongs.get(2).id() + ", 5:" + allItunesSongs.get(5).id());

                        view.displaySongs(allItunesSongs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("boom! " + e.getMessage() + e.toString());
                        view.displayError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return null;
    }

    public List<ItunesSong> convertToItuneSongsList(@NonNull List<ItunesSong> itunesSongs) {
        List<ItunesSong> convertedItuneSongsList = new ArrayList<>();
        for (int i=0; i<itunesSongs.size(); i++) {
            ItunesSong currentSongI = itunesSongs.get(i);
            ItunesSong convertedItuneSong = ItunesSong.builder()
                    .setId(i)
                    .setTitle(currentSongI.title())
                    .setAuthor(currentSongI.author())
                    .setReleaseDate(currentSongI.releaseDate())
                    .build();

            convertedItuneSongsList.add(i, convertedItuneSong);
        }
        return convertedItuneSongsList;
    }

    //ToDo rewrite this
    public List<ItunesSong> getAllItunesSongs() {
        return allItunesSongs;
    }
}
