package com.mobileallin.mysongapp.repositories;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.network.HttpClient;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Dawid on 2017-11-23.
 */

public interface ItunesSongsRepository {
    Observable<ItunesResponse> getSongs(HttpClient httpClient,Scheduler ioScheduler,
                                        Scheduler uiScheduler);

}
