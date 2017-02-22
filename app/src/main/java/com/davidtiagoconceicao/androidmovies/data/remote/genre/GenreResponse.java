package com.davidtiagoconceicao.androidmovies.data.remote.genre;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Response for genre list.
 * <p>
 * Created by david on 22/02/17.
 */

@AutoValue
public abstract class GenreResponse {

    public abstract long id();

    public abstract String name();

    public static TypeAdapter<GenreResponse> typeAdapter(Gson gson) {
        return new AutoValue_GenreResponse.GsonTypeAdapter(gson);
    }
}
