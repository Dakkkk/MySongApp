package com.mobileallin.mysongapp.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dawid on 2017-11-23.
 */

@AutoValue
public abstract class Song implements Parcelable {

    public abstract long id();

    @SerializedName("author")
    public abstract  String author();

    @SerializedName("title")
    public abstract  String title();

    @SerializedName("releaseDate")
    public abstract  String releaseDate();

    public static Builder builder() {
        return new AutoValue_Song.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(long id);

        public abstract Builder setTitle(String value);

        public abstract Builder setAuthor(String author);

        public abstract Builder setReleaseDate(String date);

        public abstract Song build();
    }

}
