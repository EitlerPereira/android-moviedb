package com.davidtiagoconceicao.androidmovies.data.remote.movie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Endpoint for movies operations.
 * <p>
 * Created by david on 22/02/17.
 */

interface MoviesEndpoint {

    @GET("movie/upcoming")
    Observable<MoviesQueryResponse> getUpcoming(
            @Query("page") int requestedPage);

    @GET("search/movie")
    Observable<MoviesQueryResponse> getSearchResults(
            @Query("query") String query);

}
