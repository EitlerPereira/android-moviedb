package com.davidtiagoconceicao.androidmovies.data;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.Date;
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

    @Nullable
    public abstract String posterPath();

    @Nullable
    public abstract String backdropPath();

    @Nullable
    public abstract Date releaseDate();

    public abstract List<Long> genreIds();

    @Nullable
    public abstract List<Genre> genres();

    public abstract Movie withDetails(
            List<Genre> genres,
            String posterPath,
            String backdropPath);

    public static Builder builder() {
        return new AutoValue_Movie.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String value);

        public abstract Builder overview(String value);

        public abstract Builder posterPath(String value);

        public abstract Builder backdropPath(String value);

        public abstract Builder releaseDate(Date value);

        public abstract Builder genreIds(List<Long> value);

        public abstract Builder genres(List<Genre> value);

        public abstract Movie build();
    }
}
