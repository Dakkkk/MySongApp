package com.mobileallin.mysongapp.repositories.impl;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;


public class ItunesSongsRepositoryImpl implements ItunesSongsRepository {

    @Override
    public Maybe<ItunesResponse> getSongs(HttpClient httpClient,
                                          Scheduler ioScheduler, Scheduler uiScheduler) {
        return Maybe.just(1)
                .concatMap(result -> httpClient.getSongs())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
