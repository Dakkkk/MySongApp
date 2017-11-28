package com.mobileallin.mysongapp.helper;

import android.util.Log;

import com.mobileallin.mysongapp.data.model.AssetsSong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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

                AssetsSong assetsSong = AssetsSong.builder()
                        .setId(i)
                        .setTitle(title)
                        .setAuthor(author)
                        .setReleaseDate(releaseDate)
                        .build();

                assetsSongsList.add(i, assetsSong);

            }
            Log.d("assetsSongsList", "0: " + assetsSongsList.get(0).id() + " 5:" + assetsSongsList.get(5).id() + "8: " + assetsSongsList.get(8).id() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assetsSongsList;
    }
}
