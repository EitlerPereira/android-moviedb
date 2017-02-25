package com.davidtiagoconceicao.androidmovies.data.remote.configuration;

import android.support.annotation.Nullable;

import com.davidtiagoconceicao.androidmovies.commons.retrofit.RetrofitServiceGenerator;
import com.davidtiagoconceicao.androidmovies.data.ImageConfiguration;

import java.util.List;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Class for obtaining configuration data.
 * <p>
 * Created by david on 23/02/17.
 */

public final class ConfigurationRepository {

    private static final String SIZE_REGEX = "w\\d*";

    public Observable<ImageConfiguration> getImageConfiguration() {
        return RetrofitServiceGenerator.generateService(ConfigurationEndpoint.class)
                .getConfiguration()
                .map(new Func1<ConfigurationsResponseEnvelope, ImageConfiguration>() {
                    @Override
                    public ImageConfiguration call(ConfigurationsResponseEnvelope configurationsResponseEnvelope) {

                        ConfigurationResponse configurationResponse = configurationsResponseEnvelope.images();

                        String baseUrl = configurationResponse.baseUrl();

                        String backdropUrl = baseUrl + getBiggestSize(configurationResponse.backdropSizes());

                        String posterUrl = baseUrl + getBiggestSize(configurationResponse.posterSizes());

                        return
                                ImageConfiguration.builder()
                                        .backdropBaseUrl(backdropUrl)
                                        .posterBaseUrl(posterUrl)
                                        .build();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * Gets the biggest image size, discarding the original size.
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    String getBiggestSize(List<String> descriptionSizes) {
        Pattern pattern = Pattern.compile(SIZE_REGEX);
        String returnValue = null;
        for (String descriptionSize : descriptionSizes) {
            if (pattern.matcher(descriptionSize).matches()) {
                returnValue = descriptionSize;
            }
        }
        return returnValue;
    }
}
