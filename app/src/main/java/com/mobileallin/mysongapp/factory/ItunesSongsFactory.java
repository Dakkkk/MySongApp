package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.ItunesSong;

/**
 * Created by Dawid on 2017-11-30.
 */

public class ItunesSongsFactory {
    private long id;
    private String title;
    private String author;
    private String releaseDate;
    private String genreName;
    private String collectionName;
    private String country;
    private String thumbnailUrl;

    public ItunesSongsFactory(long id, String title, String author, String releaseDate,
                              String genreName, String collectionName, String country,
                              String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.genreName = genreName;
        this.collectionName = collectionName;
        this.country = country;
        this.thumbnailUrl = thumbnailUrl;
    }

    public ItunesSong buildItunesSong() {
        ItunesSong itunesSong = ItunesSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .setGenreName(genreName)
                .setCollectionName(collectionName)
                .setCountry(country)
                .setThumbnailUrl(thumbnailUrl)
                .build();
        return itunesSong;
    }
}


