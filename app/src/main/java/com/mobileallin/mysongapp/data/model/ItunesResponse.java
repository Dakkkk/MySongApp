package com.mobileallin.mysongapp.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@AutoValue
public abstract class ItunesResponse implements Parcelable {

    @SuppressWarnings("SameReturnValue")
    @SerializedName("resultsCount")
    public abstract int resultsNumber();

    @SerializedName("results")
    public abstract List<ItunesSong> allItuneSongs();

    public static TypeAdapter<ItunesResponse> typeAdapter(Gson gson) {
        return new AutoValue_ItunesResponse.GsonTypeAdapter(gson);
    }
}
