package com.mobileallin.mysongapp.helper;

import android.os.Parcel;
import android.util.Log;

import com.mobileallin.mysongapp.data.model.AssetsSong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dawid on 2017-11-25.
 */

public class AssertsSongsStringParser {

    public ArrayList<AssetsSong> parseStringToAssetsSongList(String songAssetsString) {
        Log.d("songAssetsString", songAssetsString + "boom");

        ArrayList<AssetsSong> assetsSongsList = new ArrayList<AssetsSong>();

        JSONArray jsonarray;
        try {
            jsonarray = new JSONArray(songAssetsString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String title = jsonobject.getString("Song Clean");
                String author = jsonobject.getString("ARTIST CLEAN");
                String releaseDate = jsonobject.getString("Release Year");
                Log.d("songAssetsParsed", title + " " + author);

                int finalI = i;
                AssetsSong assetsSong = new AssetsSong() {
                    @Override
                    public long id() {
                        return finalI;
                    }

                    @Override
                    public String title() {
                        return title;
                    }

                    @Override
                    public String author() {
                        return author;
                    }

                    @Override
                    public String releaseDate() {
                        return releaseDate;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {

                    }
                };
                assetsSongsList.add(finalI, assetsSong);
                Log.d("assetsSongsList", assetsSongsList.get(0).author());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assetsSongsList;
    }
}
