package com.davidtiagoconceicao.androidmovies.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for movies list recycler view.
 * <p>
 * Created by david on 22/02/17.
 */

final class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final LayoutInflater inflater;

    MoviesAdapter(Context context) {

        inflater = LayoutInflater.from(context);

        movies = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(
                        R.layout.row_movie,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.titleText.setText(movie.title());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void addMovie(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_movie_title)
        TextView titleText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
