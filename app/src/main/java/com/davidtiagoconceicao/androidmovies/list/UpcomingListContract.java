package com.davidtiagoconceicao.androidmovies.list;

import com.davidtiagoconceicao.androidmovies.BasePresenter;
import com.davidtiagoconceicao.androidmovies.BaseView;
import com.davidtiagoconceicao.androidmovies.data.Movie;

/**
 * Contract for upcoming movies list.
 * <p>
 * Created by david on 22/02/17.
 */

final class UpcomingListContract {

    interface View extends BaseView<UpcomingListContract.Presenter> {
        void addMovie(Movie movie);
    }

    interface Presenter extends BasePresenter {

    }
}
