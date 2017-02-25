package com.davidtiagoconceicao.androidmovies.commons.retrofit;

import com.davidtiagoconceicao.androidmovies.BuildConfig;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adds the language param to every request.
 * <p>
 * Created by david on 22/02/17.
 */

final class LanguageInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("language", Locale.getDefault().getLanguage())
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
