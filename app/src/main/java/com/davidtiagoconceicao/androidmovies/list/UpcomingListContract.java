package com.davidtiagoconceicao.androidmovies.list;

import com.davidtiagoconceicao.androidmovies.BasePresenter;
import com.davidtiagoconceicao.androidmovies.BaseView;

/**
 * Contract for upcoming movies list.
 * <p>
 * Created by david on 22/02/17.
 */

final class UpcomingListContract {

    interface View extends BaseView<UpcomingListContract.Presenter> {
    }

    interface Presenter extends BasePresenter {

    }
}
