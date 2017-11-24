package com.mobileallin.mysongapp.interactor;

import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.helper.TimeController;
import com.mobileallin.mysongapp.network.HttpClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Dawid on 2017-11-23.
 */

public class SongsInteractor {
    private HttpClient client;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private TimeController timeController;
/*
    private SongsRepository songsRepository;
*/

    public SongsInteractor(HttpClient client, TimeController timeController,
                             @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
    ) {
        this.client = client;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.timeController = timeController;
    }

    public Observable<List<Song>> updateSongs() {
        return timeController.isItTimeToUpdate()
                .filter(result -> result == true)
                .concatMap(result -> client.getSong())
                .doOnNext(aLong -> timeController.saveTimeOfLastUpdate())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

  /*  public Observable<List<Song>> subscribeToSongs() {
        return songsRepository.getSongsNames()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }*/


}
