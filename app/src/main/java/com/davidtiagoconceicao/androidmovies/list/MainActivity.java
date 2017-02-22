package com.davidtiagoconceicao.androidmovies.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.MoviesRemoteRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements UpcomingListContract.View, LoadMoreScrollListener.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private UpcomingListContract.Presenter presenter;

    @BindView(R.id.main_movies_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.main_movies_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(
                moviesAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        new UpcomingListPresenter(this, new MoviesRemoteRepository());
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
    public void onLoadMore() {
        presenter.onLoadMore();
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }
}
