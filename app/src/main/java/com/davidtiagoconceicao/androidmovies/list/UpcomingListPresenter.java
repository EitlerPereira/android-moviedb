package com.davidtiagoconceicao.androidmovies.list;

import android.util.Log;
import android.util.LongSparseArray;

import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.genre.GenresRemoteRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.movie.MoviesRemoteRepository;

import java.util.ArrayList;
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

    private int currentPageCount;

    private LongSparseArray<Genre> genres;

    UpcomingListPresenter(
            UpcomingListContract.View view,
            MoviesRemoteRepository moviesRemoteRepository) {

        this.view = view;
        this.moviesRemoteRepository = moviesRemoteRepository;
        this.compositeSubscription = new CompositeSubscription();

        this.view.setPresenter(this);
    }

    @Override
    public void onAttach() {

        if (genres == null) {
            view.showLoading(true);

            genres = new LongSparseArray<>();

            compositeSubscription.add(
                    GenresRemoteRepository.getGenres()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Genre>() {

                                @Override
                                public void onCompleted() {
                                    refresh();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //TODO show error
                                }

                                @Override
                                public void onNext(Genre genre) {
                                    genres.put(genre.id(), genre);
                                }
                            }));

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

    private void loadMoreItems() {
        view.showLoading(true);
        compositeSubscription.add(
                moviesRemoteRepository.getUpcoming(currentPageCount)
                        .map(new Func1<Movie, Movie>() {
                            @Override
                            public Movie call(Movie movie) {
                                List<Genre> selectedGenres = new ArrayList<>();
                                for (Long id : movie.genreIds()) {
                                    Genre genre = genres.get(id);
                                    if (genre != null) {
                                        selectedGenres.add(genre);
                                    }
                                }
                                return movie.withGenres(selectedGenres);
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
                                //TODO show error
                                Log.e("upcomingList", "onError", e);
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
}
