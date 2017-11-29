package com.mobileallin.mysongapp.factory;

import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.utils.Keys;

/**
 * Created by Dawid on 2017-11-29.
 */

public class SongFactory {

    private int id;
    private String title;
    private String author;
    private String releaseDate;

    public SongFactory(int id, String title, String author, String releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public void buildSong(String SongTypeKey) {
        switch (SongTypeKey) {
            case Keys.ITUNE_SONG_TYPE:

            case Keys.ASSETS_SONG_TYPE:

            default:
        }

    }

    private ItunesSong buildItunesSong(int id, String title, String author, String releaseDate) {
        ItunesSong itunesSong = ItunesSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .build();
        return itunesSong;
    }

    private AssetsSong buildAssetsSong(int id, String title, String author, String releaseDate) {
        AssetsSong assetsSong = AssetsSong.builder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setReleaseDate(releaseDate)
                .build();
        return assetsSong;
    }
}
