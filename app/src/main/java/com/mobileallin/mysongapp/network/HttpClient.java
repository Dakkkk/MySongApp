package com.mobileallin.mysongapp.network;

import com.mobileallin.mysongapp.data.model.ItunesResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dawid on 2017-11-23.
 */

public interface HttpClient {
    String ENDPOINT = "https://itunes.apple.com/";

    //ToDo Just for test, this is going to change
    @GET("search?term=offspring&entity=musicTrack")
    Observable<ItunesResponse> getSongs();

    @GET("search")
    Flowable<ItunesResponse> searchItunesSongs(
            @Query("term") String query,
            @Query("media") String mediaType); // search only music
}

