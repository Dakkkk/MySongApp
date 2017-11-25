package com.mobileallin.mysongapp.repositories.impl;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.helper.TimeController;
import com.mobileallin.mysongapp.network.HttpClient;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Dawid on 2017-11-24.
 */

public class ItunesSongsRepositoryImpl implements com.mobileallin.mysongapp.repositories.ItunesSongsRepository {
    HttpClient httpClient;

    @Override
    public Observable<ItunesResponse> getSongs(TimeController timeController, HttpClient httpClient,
                                               Scheduler ioScheduler, Scheduler uiScheduler) {
        return timeController.isItTimeToUpdate()
                .filter(result -> result == true)
                .concatMap(result -> httpClient.getSongs())
                .doOnNext(aLong -> timeController.saveTimeOfLastUpdate())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);

    }
}
