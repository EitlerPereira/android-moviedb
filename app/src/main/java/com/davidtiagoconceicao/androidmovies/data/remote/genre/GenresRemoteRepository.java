package com.davidtiagoconceicao.androidmovies.data.remote.genre;

import com.davidtiagoconceicao.androidmovies.commons.retrofit.RetrofitServiceGenerator;
import com.davidtiagoconceicao.androidmovies.data.Genre;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Repository for remote genres operations.
 * <p>
 * Created by david on 22/02/17.
 */

public final class GenresRemoteRepository {

    public Observable<Genre> getGenres() {
        return RetrofitServiceGenerator.generateService(GenresEndpoint.class)
                .getGenres()
                .map(new Func1<GenresListResponse, List<GenreResponse>>() {
                    @Override
                    public List<GenreResponse> call(GenresListResponse genresListResponse) {
                        return genresListResponse.genres();
                    }
                })
                .flatMapIterable(new Func1<List<GenreResponse>, Iterable<GenreResponse>>() {
                    @Override
                    public Iterable<GenreResponse> call(List<GenreResponse> genreResponses) {
                        return genreResponses;
                    }
                })
                .map(new Func1<GenreResponse, Genre>() {
                    @Override
                    public Genre call(GenreResponse genreResponse) {
                        return Genre.create(genreResponse.id(), genreResponse.name());
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}
