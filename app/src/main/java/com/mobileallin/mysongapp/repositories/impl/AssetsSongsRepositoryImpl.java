package com.mobileallin.mysongapp.repositories.impl;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dawid on 2017-11-24.
 */

public class AssetsSongsRepositoryImpl {
   /* public String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("file_name.json");

            Log.d("LoadJson is", is.toString());

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            Log.d("LoadJson", json.toString());


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }*/

   /* public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<AssetsSong> loadJSONFromAsset(Context context) {
        ArrayList<AssetsSong> assetsSongsList = new ArrayList<>();
        String json = null;
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
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                AssetsSong assetsSong = new AssetsSong() {
                    @Override
                    public long id() {
                        return 0;
                    }

                    @Override
                    public String title() {
                        return jo_inside.getString("Song Clean");
                    }

                    @Override
                    public String author() throws JSONException {
                        return jo_inside.getString("ARTIST CLEAN");
                    }

                    @Override
                    public String releaseDate() {
                        return null;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {

                    }
                };

                //Add your values in your `ArrayList` as below:
                assetsSongsList.add(assetsSong);
                Log.d("AssetSongs", assetsSongsList.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assetsSongsList;
    }*/

   /*public void getAssets(Context context) {

       Gson gson = new Gson();

       try {

           BufferedReader br = new BufferedReader(
                   new FileReader("C:\\Users\\Dawid\\Desktop\\Praca\\Tooploox\\MySongApp\\app\\src\\main\\assets\\songs_list.json"));

           //convert the json string back to object
           AssetsSong obj = gson.fromJson(br, AssetsSong.class);

           System.out.println("AssetsObj" + obj);

       } catch (IOException e) {
           e.printStackTrace();
       }
   }*/

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

