package com.mobileallin.mysongapp.repositories.impl;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;


public class AssetsSongsRepositoryImpl {

    public String loadJSONFromAsset(Context context) {
        String json;
        try {

            InputStream is = context.getAssets().open("songs_list.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}

