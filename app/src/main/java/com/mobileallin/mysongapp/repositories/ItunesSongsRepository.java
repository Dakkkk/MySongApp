package com.mobileallin.mysongapp.repositories;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.network.HttpClient;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;


public interface ItunesSongsRepository {
    Maybe<ItunesResponse> getSongs(HttpClient httpClient, Scheduler ioScheduler,
                                   Scheduler uiScheduler);

}
