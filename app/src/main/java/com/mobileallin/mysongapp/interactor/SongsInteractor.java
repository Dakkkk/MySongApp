package com.mobileallin.mysongapp.interactor;

import android.util.Log;

import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.helper.TimeController;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.ui.view.SongsListView;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SongsInteractor {
    private HttpClient client;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private TimeController timeController;
    private ItunesSongsRepository itunesSongsRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SongsInteractor(ItunesSongsRepository itunesSongsRepository, HttpClient client, TimeController timeController,
                           @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
    ) {
        this.itunesSongsRepository = itunesSongsRepository;
        this.client = client;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.timeController = timeController;

    }

    public Disposable loadSongs(SongsListView view) {

        compositeDisposable.add(itunesSongsRepository.getSongs(timeController, client,
                ioScheduler, uiScheduler)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableObserver<ItunesResponse>() {
                    @Override
                    public void onNext(@NonNull ItunesResponse songs) {
                        Log.d("loadSongs", songs.toString());
                        view.displaySongs(songs.allItuneSongs());
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
}
