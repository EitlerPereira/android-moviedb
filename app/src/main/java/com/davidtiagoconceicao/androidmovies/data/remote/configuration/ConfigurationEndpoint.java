package com.davidtiagoconceicao.androidmovies.data.remote.configuration;

import com.davidtiagoconceicao.androidmovies.data.remote.movie.UpcomingMoviesResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Endpoint for configuration operations.
 * <p>
 * Created by david on 24/02/17.
 */

interface ConfigurationEndpoint {
    @GET("configuration")
    Observable<ConfigurationsResponseEnvelope> getConfiguration();
}
