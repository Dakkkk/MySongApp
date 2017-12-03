package com.mobileallin.mysongapp.network;

import com.mobileallin.mysongapp.data.model.ItunesResponse;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("SameParameterValue")
public interface HttpClient {
    String ENDPOINT = "https://itunes.apple.com/";

    @GET("search?term=*&entity=musicTrack")
    Maybe<ItunesResponse> getSongs();

    @GET("search")
    Flowable<ItunesResponse> searchItunesSongs(
            @Query("term") String query,
            @Query("media") String mediaType); // search only music
}

