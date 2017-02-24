package com.davidtiagoconceicao.androidmovies.data.remote.configuration;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Envelope for configurations response.
 * <p>
 * Created by david on 24/02/17.
 */
@AutoValue
public abstract class ConfigurationsResponseEnvelope {

    public abstract ConfigurationResponse images();

    public static TypeAdapter<ConfigurationsResponseEnvelope> typeAdapter(Gson gson) {
        return new AutoValue_ConfigurationsResponseEnvelope.GsonTypeAdapter(gson);
    }
}
