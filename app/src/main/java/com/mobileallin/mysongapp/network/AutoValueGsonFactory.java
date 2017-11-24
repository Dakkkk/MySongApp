package com.mobileallin.mysongapp.network;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Dawid on 2017-10-13.
 */

@GsonTypeAdapterFactory
public abstract class AutoValueGsonFactory implements TypeAdapterFactory {

/*    public static TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueGsonFactory();
    }*/

}
