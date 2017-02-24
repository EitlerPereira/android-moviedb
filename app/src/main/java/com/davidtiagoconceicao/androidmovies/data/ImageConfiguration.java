package com.davidtiagoconceicao.androidmovies.data;

import com.google.auto.value.AutoValue;

/**
 * Configuration for image loading.
 * <p>
 * Created by david on 24/02/17.
 */
@AutoValue
public abstract class ImageConfiguration {

    public abstract String posterBaseUrl();

    public abstract String backdropBaseUrl();

    public static Builder builder() {
        return new AutoValue_ImageConfiguration.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder posterBaseUrl(String value);

        public abstract Builder backdropBaseUrl(String value);

        public abstract ImageConfiguration build();
    }
}
