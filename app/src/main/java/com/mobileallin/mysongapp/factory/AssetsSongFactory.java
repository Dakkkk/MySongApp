package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.AssetsSong;

/**
 * Created by Dawid on 2017-11-30.
 */

public class AssetsSongFactory {

    private int id;
    private String title;
    private String author;
    private String releaseDate;
    private String genreName;
    private String collectionName;
    private String country;
    private String thumbnailUrl;

    private AssetsSong buildAssetsSong(int id, String title, String author, String releaseDate,
                                       String genreName, String collectionName, String country,
                                       String thumbnailUrl) {
        AssetsSong assetsSong = AssetsSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .setReleaseDate(genreName)
                .setReleaseDate(collectionName)
                .setReleaseDate(country)
                .setReleaseDate(thumbnailUrl)
                .build();
        return assetsSong;
    }
}
