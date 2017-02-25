package com.davidtiagoconceicao.androidmovies.data.remote.movie;

import com.davidtiagoconceicao.androidmovies.commons.DateFormatUtil;
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
                .flatMapIterable(new Func1<MoviesQueryResponse, Iterable<MovieResponse>>() {
                    @Override
                    public Iterable<MovieResponse> call(MoviesQueryResponse envelopeResponse) {
                        return envelopeResponse.results();
                    }
                })
                .map(new Func1<MovieResponse, Movie>() {
                    @Override
                    public Movie call(MovieResponse movieResponse) {
                        return mapMovie(movieResponse);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<Movie> searchMovie(String query) {
        return RetrofitServiceGenerator.generateService(MoviesEndpoint.class)
                .getSearchResults(query)
                .flatMapIterable(new Func1<MoviesQueryResponse, Iterable<MovieResponse>>() {
                    @Override
                    public Iterable<MovieResponse> call(MoviesQueryResponse envelopeResponse) {
                        return envelopeResponse.results();
                    }
                })
                .map(new Func1<MovieResponse, Movie>() {
                    @Override
                    public Movie call(MovieResponse movieResponse) {
                        return mapMovie(movieResponse);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    //Called by inner classes
    @SuppressWarnings("WeakerAccess")
    Movie mapMovie(MovieResponse movieResponse) {
        return Movie.builder()
                .title(movieResponse.title())
                .overview(movieResponse.overview())
                .posterPath(movieResponse.posterPath())
                .backdropPath(movieResponse.backdropPath())
                .releaseDate(
                        DateFormatUtil.parseDate(movieResponse.releaseDate()))
                .genreIds(movieResponse.genreIds())
                .build();
    }
}
