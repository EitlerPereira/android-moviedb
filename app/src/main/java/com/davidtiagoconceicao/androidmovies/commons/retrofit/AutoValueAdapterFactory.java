package com.davidtiagoconceicao.androidmovies.commons.retrofit;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Factory for GSON and AutoValue integration.
 *
 * Created by david on 21/02/17.
 */
@GsonTypeAdapterFactory
abstract class AutoValueAdapterFactory implements TypeAdapterFactory {

    static TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueAdapterFactory();
    }
}