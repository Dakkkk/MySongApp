package com.mobileallin.mysongapp.helper;

import com.mobileallin.mysongapp.data.model.ItunesSong;

import java.util.Comparator;


public class ItunesSongTitleComparator implements Comparator<ItunesSong> {

    @Override
    public int compare(ItunesSong itunesSong1, ItunesSong itunesSong2) {
        String title1 = itunesSong1.title().toLowerCase().trim();
        String title2 = itunesSong2.title().toLowerCase().trim();

        if (title1 == null) {
            return -1;
        }
        if (title2 == null) {
            return 1;
        }
        if (title1.equals(title2)) {
            return 0;
        }
        return title1.compareTo(title2);
    }
}
