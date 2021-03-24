package com.avinash.data.source.remote.endPoint;

import com.avinash.data.source.remote.model.ResultResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherDetailsEndPoint {

    @GET("http://api.openweathermap.org/data/2.5/weather")
    public Observable<ResultResponse> getWeatherDetails(@Query("q") String cityName, @Query("appid") String appId);

}
