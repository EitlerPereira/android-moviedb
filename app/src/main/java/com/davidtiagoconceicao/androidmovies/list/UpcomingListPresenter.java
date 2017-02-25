package com.davidtiagoconceicao.androidmovies.list;

import android.util.Log;
import android.util.LongSparseArray;

import com.davidtiagoconceicao.androidmovies.commons.MovieUtil;
import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.ImageConfiguration;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.configuration.ConfigurationRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.genre.GenresRemoteRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.movie.MoviesRemoteRepository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter for upcoming movies list.
 * <p>
 * Created by david on 22/02/17.
 */

final class UpcomingListPresenter implements UpcomingListContract.Presenter {

    private final CompositeSubscription compositeSubscription;
    private final UpcomingListContract.View view;
    private final MoviesRemoteRepository moviesRemoteRepository;
    private final GenresRemoteRepository genresRemoteRepository;
    private final ConfigurationRepository configurationRepository;

    private int currentPageCount;

    private LongSparseArray<Genre> genres;
    private ImageConfiguration imageConfiguration;

    UpcomingListPresenter(
            UpcomingListContract.View view,
            MoviesRemoteRepository moviesRemoteRepository,
            GenresRemoteRepository genresRemoteRepository,
            ConfigurationRepository configurationRepository) {

        this.view = view;
        this.moviesRemoteRepository = moviesRemoteRepository;
        this.genresRemoteRepository = genresRemoteRepository;
        this.configurationRepository = configurationRepository;
        this.compositeSubscription = new CompositeSubscription();

        this.view.setPresenter(this);
    }

    @Override
    public void onAttach() {

        if (genres == null) {
            view.showLoading(true);

            genres = new LongSparseArray<>();

            loadGenres();

        } else if (currentPageCount == 0) {
            refresh();
        }
    }

    @Override
    public void onDetach() {
        compositeSubscription.clear();
    }

    @Override
    public void onLoadMore() {
        currentPageCount++;
        loadMoreItems();
    }

    @Override
    public void refresh() {
        currentPageCount = 1;
        loadMoreItems();
    }

    //Called by inner classes, default avoid accessors
    @SuppressWarnings("WeakerAccess")
    void handleException(Throwable e) {
        Log.e(getClass().getSimpleName(), "Exception on stream", e);
        view.showErrorLoading();
        view.showLoading(false);
    }

    //Called by inner classes, default avoid accessors
    @SuppressWarnings("WeakerAccess")
    void loadMoreItems() {
        view.showLoading(true);
        compositeSubscription.add(
                moviesRemoteRepository.getUpcoming(currentPageCount)
                        .map(new Func1<Movie, Movie>() {
                            @Override
                            public Movie call(Movie movie) {
                                return MovieUtil.mapMovieFields(movie, imageConfiguration, genres);
                            }
                        })
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Movie>>() {
                            @Override
                            public void onCompleted() {
                                view.showLoading(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleException(e);
                            }

                            @Override
                            public void onNext(List<Movie> movies) {
                                if (currentPageCount == 1) {
                                    view.clearList();
                                }
                                view.addMovies(movies);
                            }
                        }));
    }

    //Called by inner classes
    @SuppressWarnings("WeakerAccess")
    void loadGenres() {
        compositeSubscription.add(
                genresRemoteRepository.getGenres()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Genre>() {

                            @Override
                            public void onCompleted() {
                                if (imageConfiguration == null) {
                                    loadImageConfiguration();
                                } else {
                                    refresh();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleException(e);
                            }

                            @Override
                            public void onNext(Genre genre) {
                                genres.put(genre.id(), genre);
                            }
                        }));
    }

    //Called by inner classes, default avoid accessors
    @SuppressWarnings("WeakerAccess")
    void loadImageConfiguration() {
        compositeSubscription.add(
                configurationRepository.getImageConfiguration()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ImageConfiguration>() {
                            @Override
                            public void onCompleted() {
                                refresh();
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleException(e);
                            }

                            @Override
                            public void onNext(ImageConfiguration imageConfigurationResponse) {
                                imageConfiguration = imageConfigurationResponse;
                            }
                        }));
    }

}
