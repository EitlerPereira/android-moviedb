package com.davidtiagoconceicao.androidmovies.data.remote;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Endpoint for movies operations.
 * <p>
 * Created by david on 22/02/17.
 */

public interface MoviesEndpoint {

    @GET("/movie/upcoming")
    Observable<List<MovieResponse>> getUpcoming(
            @Query("page") int requestedPage);

}
