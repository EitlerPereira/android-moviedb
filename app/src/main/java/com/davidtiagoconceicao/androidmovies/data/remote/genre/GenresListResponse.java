package com.davidtiagoconceicao.androidmovies.data.remote.genre;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Response for genre list.
 * <p>
 * Created by david on 22/02/17.
 */

@AutoValue
public abstract class GenresListResponse {

    public abstract List<GenreResponse> genres();

    public static TypeAdapter<GenresListResponse> typeAdapter(Gson gson) {
        return new AutoValue_GenresListResponse.GsonTypeAdapter(gson);
    }
}
