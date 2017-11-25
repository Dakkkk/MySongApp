package com.mobileallin.mysongapp.repositories.impl;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dawid on 2017-11-24.
 */

public class AssetsSongsRepositoryImpl {
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("file_name.json");

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
