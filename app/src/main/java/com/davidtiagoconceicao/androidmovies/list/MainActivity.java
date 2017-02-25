package com.davidtiagoconceicao.androidmovies.list;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.configuration.ConfigurationRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.genre.GenresRemoteRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.movie.MoviesRemoteRepository;
import com.davidtiagoconceicao.androidmovies.search.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements UpcomingListContract.View,
        LoadMoreScrollListener.LoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_movies_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.main_movies_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MoviesRecyclerAdapter moviesAdapter;

    private UpcomingListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        moviesAdapter = new MoviesRecyclerAdapter(
                MoviesRecyclerAdapter.REGULAR_MODE,
                this);

        recyclerView.setAdapter(moviesAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        setSupportActionBar(toolbar);

        new UpcomingListPresenter(this,
                new MoviesRemoteRepository(),
                new GenresRemoteRepository(),
                new ConfigurationRepository());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach();
    }

    @Override
    protected void onStop() {
        presenter.onDetach();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_search) {
            SearchActivity.starForContext(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(UpcomingListContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovie(movies);
    }

    @Override
    public void showLoading(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void clearList() {
        moviesAdapter.clearList();
        LinearLayoutManager layoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(
                new LoadMoreScrollListener(layoutManager, this));
    }

    @Override
    public void showErrorLoading() {
        Snackbar.make(coordinatorLayout, R.string.error_loading, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadMore();
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }
}
