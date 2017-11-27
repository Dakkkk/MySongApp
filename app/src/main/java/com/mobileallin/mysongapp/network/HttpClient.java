package com.mobileallin.mysongapp.network;

import com.mobileallin.mysongapp.data.model.ItunesResponse;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dawid on 2017-11-23.
 */

public interface HttpClient {
    String ENDPOINT = "https://itunes.apple.com/";

    //Used Maybe instead of Single because it handles also the case when we get the response
    // from server, but it contains no body, which Single does not handle.
    //ToDo Change the API call adress?
    @GET("search?term=offspring&entity=musicTrack")
    Maybe<ItunesResponse> getSongs();

    @GET("search")
    Flowable<ItunesResponse> searchItunesSongs(
            @Query("term") String query,
            @Query("media") String mediaType); // search only music
}

