package com.davidtiagoconceicao.androidmovies.commons.retrofit;

import com.davidtiagoconceicao.androidmovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adds the API key param to every request.
 * <p>
 * Created by david on 22/02/17.
 */

final class ApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
