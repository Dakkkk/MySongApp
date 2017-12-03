package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.ItunesSong;


public class ItunesSongsFactory {
    private final long id;
    private final String title;
    private final String author;
    private final String releaseDate;
    private final String genreName;
    private final String collectionName;
    private final String country;
    private final String thumbnailUrl;

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
        return ItunesSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .setGenreName(genreName)
                .setCollectionName(collectionName)
                .setCountry(country)
                .setThumbnailUrl(thumbnailUrl)
                .build();
    }
}


