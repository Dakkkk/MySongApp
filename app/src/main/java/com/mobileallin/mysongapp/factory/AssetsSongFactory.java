package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.AssetsSong;


public class AssetsSongFactory {

    private final long id;
    private final String title;
    private final String author;
    private final String releaseDate;
    private final String first;
    private final String year;
    private final String playCount;

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
        return AssetsSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .setFirst(first)
                .setYear(year)
                .setPlayCount(playCount)
                .build();
    }
}
