package com.avinash.data.source.remote;

import com.avinash.data.source.remote.endPoint.WeatherDetailsEndPoint;
import com.avinash.data.source.remote.model.ResultResponse;

import io.reactivex.Observable;

public class WeatherDetailsRemoteDataSource {

    WeatherDetailsEndPoint weatherDetailsEndPoint;

    public WeatherDetailsRemoteDataSource() {
        weatherDetailsEndPoint = ApiServiceGenerator.createService(WeatherDetailsEndPoint.class);
    }

    public Observable<ResultResponse> getWeatherDetails(String cityName, String appId) {
        return weatherDetailsEndPoint.getWeatherDetails(cityName, appId);
    }


}
