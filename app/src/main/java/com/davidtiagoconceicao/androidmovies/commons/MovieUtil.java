package com.davidtiagoconceicao.androidmovies.commons;

import android.util.LongSparseArray;

import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.ImageConfiguration;
import com.davidtiagoconceicao.androidmovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 25/02/17.
 */

public final class MovieUtil {

    public static Movie mapMovieFields(Movie movie, ImageConfiguration imageConfiguration, LongSparseArray<Genre> genres) {
        List<Genre> selectedGenres = new ArrayList<>();
        for (Long id : movie.genreIds()) {
            Genre genre = genres.get(id);
            if (genre != null) {
                selectedGenres.add(genre);
            }
        }

        String backdropPath = movie.backdropPath();
        if (backdropPath != null) {
            backdropPath = imageConfiguration.backdropBaseUrl() + backdropPath;
        }
        String posterPath = movie.posterPath();
        if (posterPath != null) {
            posterPath = imageConfiguration.posterBaseUrl() + posterPath;
        }

        return movie.withDetails(
                selectedGenres,
                posterPath,
                backdropPath);
    }

}
