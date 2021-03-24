package com.avinash.data.source.remote;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {


    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private static final OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES);

    public static <S> S createService(
            Class<S> serviceClass) {

        logging.level(HttpLoggingInterceptor.Level.BODY);


        httpClient.interceptors().clear();


        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }


    public static void resetAuthToken() {
        httpClient.interceptors().clear();
    }


    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
