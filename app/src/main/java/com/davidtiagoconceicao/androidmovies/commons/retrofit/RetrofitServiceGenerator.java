package com.davidtiagoconceicao.androidmovies.commons.retrofit;

import android.support.annotation.NonNull;

import com.davidtiagoconceicao.androidmovies.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Common class for retrofit service generation.
 * <p>
 * Created by david on 21/02/17.
 */

public final class RetrofitServiceGenerator {

    private static OkHttpClient okHttpClient;

    public static <S> S generateService(
            @NonNull Class<S> serviceClass) {

        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(DefaultGsonConverter.getConverter())
                .baseUrl(BuildConfig.API_URL)
                .build()
                .create(serviceClass);
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(
                            new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(new ApiKeyInterceptor())
                    .addInterceptor(new LanguageInterceptor())
                    .build();
        }
        return okHttpClient;
    }
}
