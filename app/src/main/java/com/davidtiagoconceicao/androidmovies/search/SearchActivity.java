package com.davidtiagoconceicao.androidmovies.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.davidtiagoconceicao.androidmovies.list.MoviesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity for movies search.
 * <p>
 * Created by david on 25/02/17.
 */

public final class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_query_edit)
    EditText queryEdit;

    @BindView(R.id.search_movies_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.search_progress)
    ProgressBar progressBar;

    private SearchContract.Presenter presenter;
    private MoviesAdapter moviesAdapter;

    public static void starForContext(Context context) {
        context.startActivity(
                new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);

        queryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.search(editable.toString());
            }
        });

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
}
