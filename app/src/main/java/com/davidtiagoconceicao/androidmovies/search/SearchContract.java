package com.davidtiagoconceicao.androidmovies.search;

import com.davidtiagoconceicao.androidmovies.BasePresenter;
import com.davidtiagoconceicao.androidmovies.BaseView;
import com.davidtiagoconceicao.androidmovies.data.Movie;

import java.util.List;

/**
 * Contract for upcoming movies list.
 * <p>
 * Created by david on 22/02/17.
 */

final class SearchContract {

    interface View extends BaseView<SearchContract.Presenter> {

        void showResult(List<Movie> movie);

        void showLoading(boolean show);

        void showErrorLoading();
    }

    interface Presenter extends BasePresenter {

        void search(String query);
    }
}
