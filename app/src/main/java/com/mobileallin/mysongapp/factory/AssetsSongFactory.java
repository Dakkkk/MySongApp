package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.AssetsSong;

/**
 * Created by Dawid on 2017-11-30.
 */

public class AssetsSongFactory {

    private long id;
    private String title;
    private String author;
    private String releaseDate;
    private String first;
    private String year;
    private String playCount;

    public AssetsSongFactory(long id, String title, String author, String releaseDate, String first,
                             String year, String playCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.first = first;
        this.year = year;
        this.playCount = playCount;
    }

    public AssetsSong buildAssetsSong() {
        AssetsSong assetsSong = AssetsSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .setFirst(first)
                .setYear(year)
                .setPlayCount(playCount)
                .build();
        return assetsSong;
    }
}
