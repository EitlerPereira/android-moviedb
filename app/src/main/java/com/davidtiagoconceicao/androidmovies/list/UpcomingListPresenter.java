package com.davidtiagoconceicao.androidmovies.list;

import android.util.Log;

import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.MoviesRemoteRepository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        if (currentPageCount == 0) {
            currentPageCount++;
            loadMoreItems();
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

    private void loadMoreItems() {
        compositeSubscription.add(
                moviesRemoteRepository.getUpcoming(currentPageCount)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Movie>>() {
                            @Override
                            public void onCompleted() {
                                Log.d("upcomingList", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("upcomingList", "onError", e);

                            }

                            @Override
                            public void onNext(List<Movie> movies) {
                                view.addMovies(movies);
                            }
                        }));
    }
}
