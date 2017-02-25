package com.davidtiagoconceicao.androidmovies.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.commons.DateFormatUtil;
import com.davidtiagoconceicao.androidmovies.commons.GenreUtil;
import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.davidtiagoconceicao.androidmovies.details.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for movies list recycler view.
 * <p>
 * Created by david on 22/02/17.
 */

public final class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    public static final int REGULAR_MODE = 100;
    public static final int COMPACT_MODE = 200;

    private final int layoutMode;

    private final List<Movie> movies;
    private final LayoutInflater inflater;
    private final Picasso picasso;

    public MoviesRecyclerAdapter(int layoutMode, Context context) {
        this.layoutMode = layoutMode;

        inflater = LayoutInflater.from(context);

        movies = new ArrayList<>();

        picasso = Picasso.with(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutMode == REGULAR_MODE) {
            return new ViewHolder(
                    inflater.inflate(
                            R.layout.row_movie,
                            parent,
                            false));
        } else if (layoutMode == COMPACT_MODE) {
            return new ViewHolder(
                    inflater.inflate(
                            R.layout.row_compact_movie,
                            parent,
                            false));
        }

        throw new IllegalArgumentException("Invalid layout mode: " + layoutMode);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        holder.titleText.setText(movie.title());

        bindGenre(holder, movie);

        holder.releaseDateText.setText(
                DateFormatUtil.formatDate(movie.releaseDate()));

        holder.contentLayout.setOnClickListener(
                new MovieClickListener(movie, inflater.getContext()));

        bindImage(holder, movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovie(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.movies.clear();
        notifyDataSetChanged();
    }

    private void bindImage(ViewHolder holder, Movie movie) {
        String posterPath = movie.posterPath();
        if (posterPath != null) {

            loadImage(holder, posterPath);

        } else {

            String backdropPath = movie.backdropPath();
            if (backdropPath != null) {
                loadImage(holder, backdropPath);
            }

        }
    }

    private void bindGenre(ViewHolder holder, Movie movie) {

        List<Genre> genres = movie.genres();
        assert genres != null;

        holder.genresText.setText(
                GenreUtil.createGenresSpannable(
                        inflater.getContext(),
                        genres));
    }

    private void loadImage(ViewHolder holder, String path) {
        if (layoutMode == REGULAR_MODE) {
            picasso.load(path)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            picasso.load(path)
                    .fit()
                    .centerInside()
                    .into(holder.imageView);
        }
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_content_layout)
        View contentLayout;

        @BindView(R.id.row_movie_title)
        TextView titleText;

        @BindView(R.id.row_movie_image)
        ImageView imageView;

        @BindView(R.id.row_movie_genres_text)
        TextView genresText;

        @BindView(R.id.row_movie_release_date)
        TextView releaseDateText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static final class MovieClickListener implements View.OnClickListener {
        private final Movie movie;
        private final Context context;

        MovieClickListener(Movie movie, Context context) {
            this.movie = movie;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            DetailsActivity.startForMovie(movie, context);
        }
    }
}
