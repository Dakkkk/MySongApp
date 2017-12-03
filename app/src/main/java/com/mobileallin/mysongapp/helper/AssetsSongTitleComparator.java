package com.mobileallin.mysongapp.helper;

import com.mobileallin.mysongapp.data.model.AssetsSong;

import java.util.Comparator;


public class AssetsSongTitleComparator implements Comparator<AssetsSong> {

    @Override
    public int compare(AssetsSong assetsSong1, AssetsSong assetsSong2) {
        String title1 = assetsSong1.title().toLowerCase().trim();
        String title2 = assetsSong2.title().toLowerCase().trim();

        if (title1.equals(title2)) {
            return 0;
        }
        return title1.compareTo(title2);
    }
}

