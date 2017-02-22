package com.davidtiagoconceicao.androidmovies.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data for a movie item.
 * <p>
 * Created by david on 21/02/17.
 */

@AutoValue
public abstract class Movie implements Parcelable {

    public abstract String title();

    public abstract String overview();

    public abstract String posterPath();

    public abstract String backdropPath();

    public abstract String releaseDate();

    public abstract List<Integer> genreIds();

    public static Builder builder() {
        return new AutoValue_Movie.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String value);

        public abstract Builder overview(String value);

        public abstract Builder posterPath(String value);

        public abstract Builder backdropPath(String value);

        public abstract Builder releaseDate(String value);

        public abstract Builder genreIds(List<Integer> value);

        public abstract Movie build();
    }
}
