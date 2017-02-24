package com.davidtiagoconceicao.androidmovies.data.remote.configuration;

import com.davidtiagoconceicao.androidmovies.data.remote.movie.MovieResponse;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response of a configuration request.
 * <p>
 * Created by david on 24/02/17.
 */

@AutoValue
public abstract class ConfigurationResponse {

    @SerializedName("secure_base_url")
    public abstract String baseUrl();

    @SerializedName("backdrop_sizes")
    public abstract List<String> backdropSizes();

    @SerializedName("poster_sizes")
    public abstract List<String> posterSizes();

    public static TypeAdapter<ConfigurationResponse> typeAdapter(Gson gson) {
        return new AutoValue_ConfigurationResponse.GsonTypeAdapter(gson);
    }
}
