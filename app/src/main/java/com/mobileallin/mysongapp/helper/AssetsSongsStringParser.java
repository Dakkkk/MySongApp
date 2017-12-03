package com.mobileallin.mysongapp.helper;

import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AssetsSongsStringParser {

    public ArrayList<AssetsSong> parseStringToAssetsSongList(String songAssetsString) {
        ArrayList<AssetsSong> assetsSongsList = new ArrayList<>();
        JSONArray jsonarray;
        try {
            jsonarray = new JSONArray(songAssetsString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                long id = (long) i;
                String title = jsonobject.getString("Song Clean");
                String author = jsonobject.getString("ARTIST CLEAN");
                String releaseDate = jsonobject.getString("Release Year");
                String first = jsonobject.getString("First?");
                String year = jsonobject.getString("Year?");
                String playCount = jsonobject.getString("PlayCount");
                AssetsSongFactory assetsSongFactory = new AssetsSongFactory(id, title, author,
                        releaseDate, first, year, playCount);
                AssetsSong assetsSong = assetsSongFactory.buildAssetsSong();
                assetsSongsList.add(i, assetsSong);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assetsSongsList;
    }
}
