package com.davidtiagoconceicao.androidmovies.data.remote;

import com.davidtiagoconceicao.androidmovies.commons.retrofit.RetrofitServiceGenerator;
import com.davidtiagoconceicao.androidmovies.data.Movie;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Repository for movies operations.
 * <p>
 * Created by david on 22/02/17.
 */

public final class MoviesRemoteRepository {

    public Observable<Movie> getUpcoming(int page) {
        return RetrofitServiceGenerator.generateService(MoviesEndpoint.class)
                .getUpcoming(page)
                .flatMapIterable(new Func1<UpcomingMoviesResponse, Iterable<MovieResponse>>() {
                    @Override
                    public Iterable<MovieResponse> call(UpcomingMoviesResponse envelopeResponse) {
                        return envelopeResponse.results();
                    }
                })
                .map(new Func1<MovieResponse, Movie>() {
                    @Override
                    public Movie call(MovieResponse movieResponse) {
                        return Movie.builder()
                                .title(movieResponse.title())
                                .overview(movieResponse.overview())
                                .posterPath(movieResponse.posterPath())
                                .backdropPath(movieResponse.backdropPath())
                                .releaseDate(movieResponse.releaseDate())
                                .genreIds(movieResponse.genreIds())
                                .build();
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
