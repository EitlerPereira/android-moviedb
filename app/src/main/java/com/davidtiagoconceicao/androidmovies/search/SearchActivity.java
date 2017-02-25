package com.davidtiagoconceicao.androidmovies.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.data.remote.configuration.ConfigurationRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.genre.GenresRemoteRepository;
import com.davidtiagoconceicao.androidmovies.data.remote.movie.MoviesRemoteRepository;
import com.davidtiagoconceicao.androidmovies.list.MoviesRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity for movies search.
 * <p>
 * Created by david on 25/02/17.
 */

public final class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.search_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_query_edit)
    EditText queryEdit;

    @BindView(R.id.search_movies_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.search_progress)
    ProgressBar progressBar;

    private SearchContract.Presenter presenter;
    private MoviesRecyclerAdapter moviesAdapter;

    public static void starForContext(Context context) {
        context.startActivity(
                new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(
                new NavigationClickListener(this));

        moviesAdapter = new MoviesRecyclerAdapter(
                MoviesRecyclerAdapter.COMPACT_MODE,
                this);

        recyclerView.setAdapter(moviesAdapter);

        queryEdit.addTextChangedListener(
                new EditTextWatcher(this));

        new SearchPresenter(
                this,
                new MoviesRemoteRepository(),
                new GenresRemoteRepository(),
                new ConfigurationRepository());
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void showResult(List<Movie> movie) {
        moviesAdapter.clearList();
        moviesAdapter.addMovie(movie);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorLoading() {
        Snackbar.make(coordinatorLayout, R.string.error_loading, Snackbar.LENGTH_LONG)
                .show();
    }

    private static final class NavigationClickListener implements View.OnClickListener {
        private final SearchActivity searchActivity;

        NavigationClickListener(SearchActivity searchActivity) {

            this.searchActivity = searchActivity;
        }

        @Override
        public void onClick(View v) {
            searchActivity.onBackPressed();
        }
    }

    private static final class EditTextWatcher implements TextWatcher {

        private final SearchActivity searchActivity;

        EditTextWatcher(SearchActivity searchActivity) {

            this.searchActivity = searchActivity;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            searchActivity.presenter.search(editable.toString());
        }
    }
}
