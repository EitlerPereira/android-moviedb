package com.davidtiagoconceicao.androidmovies.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.remote.MoviesRemoteRepository;

public class MainActivity extends AppCompatActivity implements UpcomingListContract.View {

    private UpcomingListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
