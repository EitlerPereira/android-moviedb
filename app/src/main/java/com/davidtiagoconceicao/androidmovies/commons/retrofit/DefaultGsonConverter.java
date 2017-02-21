package com.davidtiagoconceicao.androidmovies.commons.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory for integration of GSON and Retrofit.
 * <p>
 * Created by david on 21/02/17.
 */

 final class DefaultGsonConverter {

    private static GsonConverterFactory gsonConverter;

    @NonNull
    static GsonConverterFactory getConverter() {
        if (gsonConverter == null) {
            gsonConverter = GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(AutoValueAdapterFactory.create())
                            .create());
        }
        return gsonConverter;
    }

}