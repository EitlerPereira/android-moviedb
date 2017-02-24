package com.davidtiagoconceicao.androidmovies.list;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.commons.DateFormatUtil;
import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.Movie;
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

final class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String GENRES_SEPARATOR = " ";
    private final List<Movie> movies;
    private final LayoutInflater inflater;
    private final Picasso picasso;
    private final int accentColor;

    MoviesAdapter(Context context) {

        inflater = LayoutInflater.from(context);

        movies = new ArrayList<>();

        picasso = Picasso.with(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accentColor = context.getResources()
                    .getColor(
                            R.color.colorAccent,
                            context.getTheme());
        } else {
            accentColor = context.getResources()
                    .getColor(R.color.colorAccent);
        }
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

        bindGenre(holder, movie);

        holder.releaseDateText.setText(
                DateFormatUtil.formatDate(movie.releaseDate()));

        bindImage(holder, movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void addMovie(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    void clearList() {
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

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        List<Genre> genres = movie.genres();
        assert genres != null;

        for (Genre genre : genres) {

            String name = genre.name().toUpperCase();
            name = " " + name + " ";

            SpannableString styledString = new SpannableString(name);

            styledString.setSpan(
                    new BackgroundColorSpan(accentColor),
                    0,
                    name.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableStringBuilder.append(styledString);
            spannableStringBuilder.append(" ");
        }

        holder.genresText.setText(spannableStringBuilder);
    }

    private void loadImage(ViewHolder holder, String path) {
        picasso.load(path)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

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
}
