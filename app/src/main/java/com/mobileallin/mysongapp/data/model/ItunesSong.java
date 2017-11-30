package com.mobileallin.mysongapp.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class ItunesSong implements Parcelable {

    public abstract long id();

    @SerializedName("artistName")
    public abstract String author();

    @SerializedName("trackName")
    public abstract String title();

    @SerializedName("releaseDate")
    public abstract String releaseDate();

    @SerializedName("collectionName")
    public abstract String collectionName();

    @SerializedName("country")
    public abstract String country();

    @SerializedName("primaryGenreName")
    public abstract String genreName();

    @SerializedName("artworkUrl100")
    public abstract String thumbnailUrl();

    public static TypeAdapter<ItunesSong> typeAdapter(Gson gson) {
        return new AutoValue_ItunesSong.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ItunesSong.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(long id);

        public abstract Builder setTitle(String value);

        public abstract Builder setAuthor(String value);

        public abstract Builder setReleaseDate(String value);

        public abstract Builder setCollectionName(String value);

        public abstract Builder setCountry(String value);

        public abstract Builder setGenreName(String value);

        public abstract Builder setThumbnailUrl(String value);

        public abstract ItunesSong build();
    }

}
