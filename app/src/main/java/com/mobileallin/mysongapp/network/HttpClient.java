package com.mobileallin.mysongapp.network;

import com.mobileallin.mysongapp.data.model.Song;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Dawid on 2017-11-23.
 */

public interface HttpClient {
    String ENDPOINT = "https://itunes.apple.com/search?";

    //Just for test
    @GET("term=jack+johnson")
    Observable<List<Song>> getSong();
}

