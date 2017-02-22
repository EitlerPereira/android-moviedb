package com.davidtiagoconceicao.androidmovies.data.remote;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data for a movie item.
 * <p>
 * Created by david on 21/02/17.
 */

@AutoValue
public abstract class MovieResponse {

    public abstract String title();

    public abstract String overview();

    @SerializedName("poster_path")
    public abstract String posterPath();

    @SerializedName("backdrop_path")
    public abstract String backdropPath();

    @SerializedName("release_date")
    public abstract String releaseDate();

    @SerializedName("genre_ids")
    public abstract List<Integer> genreIds();

    public static TypeAdapter<MovieResponse> typeAdapter(Gson gson) {
        return new AutoValue_MovieResponse.GsonTypeAdapter(gson);
    }

}
