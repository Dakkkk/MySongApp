package com.mobileallin.mysongapp.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dawid on 2017-11-23.
 */

@AutoValue
public abstract class Song implements Parcelable {

    public abstract long id();

    @SerializedName("artistName")
    public abstract String author();

    @SerializedName("trackName")
    public abstract String title();

    @SerializedName("releaseDate")
    public abstract String releaseDate();

    public static TypeAdapter<Song> typeAdapter(Gson gson) {
        return new AutoValue_Song.GsonTypeAdapter(gson);
    }

    static Builder builder() {
        return new AutoValue_Song.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        public abstract Builder setId(long id);

        public abstract Builder setTitle(String value);

        public abstract Builder setAuthor(String value);

        public abstract Builder setReleaseDate(String value);

        public abstract Song build();
    }

}
