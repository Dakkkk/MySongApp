package com.mobileallin.mysongapp.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class AssetsSong implements Parcelable {

    public abstract long id();

    @SerializedName("Song Clean")
    public abstract String title();

    @SerializedName("ARTIST CLEAN")
    public abstract String author();

    @SerializedName("Release Year")
    public abstract String releaseDate();

    @SerializedName("First?")
    public abstract String first();

    @SerializedName("Year?")
    public abstract String year();

    @SerializedName("PlayCount")
    public abstract String playCount();

    public static TypeAdapter<AssetsSong> typeAdapter(Gson gson) {
        return new AutoValue_AssetsSong.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_AssetsSong.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(long id);

        public abstract Builder setTitle(String value);

        public abstract Builder setAuthor(String value);

        public abstract Builder setReleaseDate(String value);

        public abstract Builder setFirst(String value);

        public abstract Builder setYear(String value);

        public abstract Builder setPlayCount(String value);

        public abstract AssetsSong build();
    }
}
