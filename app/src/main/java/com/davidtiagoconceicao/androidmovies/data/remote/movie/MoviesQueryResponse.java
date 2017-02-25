package com.davidtiagoconceicao.androidmovies.data.remote.movie;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Response evenlope for API.
 * <p>
 * Created by david on 22/02/17.
 */

@AutoValue
public abstract class MoviesQueryResponse {

    public abstract List<MovieResponse> results();

    public static TypeAdapter<MoviesQueryResponse> typeAdapter(Gson gson) {
        return new AutoValue_MoviesQueryResponse.GsonTypeAdapter(gson);
    }
}
