package com.mobileallin.mysongapp.repositories.impl;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Dawid on 2017-11-24.
 */

public class ItunesSongsRepositoryImpl implements ItunesSongsRepository {

    //ToDo Rewrite this to use Maybe / Single instead of Observable

    @Override
    public Observable<ItunesResponse> getSongs(HttpClient httpClient,
                                               Scheduler ioScheduler, Scheduler uiScheduler) {
        return Observable.just(1)
                .concatMap(result -> httpClient.getSongs())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
