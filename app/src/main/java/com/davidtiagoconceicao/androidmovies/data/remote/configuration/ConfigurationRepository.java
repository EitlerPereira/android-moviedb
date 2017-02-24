package com.davidtiagoconceicao.androidmovies.data.remote.configuration;

import com.davidtiagoconceicao.androidmovies.commons.retrofit.RetrofitServiceGenerator;
import com.davidtiagoconceicao.androidmovies.data.ImageConfiguration;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Class for obtaining configuration data.
 * <p>
 * Created by david on 23/02/17.
 */

public final class ConfigurationRepository {

    public Observable<ImageConfiguration> getImageConfiguration() {
        return RetrofitServiceGenerator.generateService(ConfigurationEndpoint.class)
                .getConfiguration()
                .map(new Func1<ConfigurationsResponseEnvelope, ImageConfiguration>() {
                    @Override
                    public ImageConfiguration call(ConfigurationsResponseEnvelope configurationsResponseEnvelope) {

                        ConfigurationResponse configurationResponse = configurationsResponseEnvelope.images();

                        String baseUrl = configurationResponse.baseUrl();

                        List<String> backdropSizes = configurationResponse.backdropSizes();
                        String backdropUrl = baseUrl + backdropSizes.get(backdropSizes.size() - 1);

                        List<String> posterSizes = configurationResponse.posterSizes();
                        String posterUrl = baseUrl + posterSizes.get(posterSizes.size() - 1);

                        return
                                ImageConfiguration.builder()
                                        .backdropBaseUrl(backdropUrl)
                                        .posterBaseUrl(posterUrl)
                                        .build();
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
