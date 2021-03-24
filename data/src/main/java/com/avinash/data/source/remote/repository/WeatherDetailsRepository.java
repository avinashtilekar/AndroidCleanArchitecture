package com.avinash.data.source.remote.repository;

import com.avinash.data.mapper.ResultResponseMapper;
import com.avinash.data.source.remote.WeatherDetailsRemoteDataSource;
import com.avinash.domain.model.ResultDTO;
import com.avinash.domain.repository.IWeatherDetailsRepository;

import io.reactivex.Observable;

public class WeatherDetailsRepository implements IWeatherDetailsRepository {
    private WeatherDetailsRemoteDataSource dataSource;
    private ResultResponseMapper resultResponseMapper;

    public WeatherDetailsRepository() {
        dataSource = new WeatherDetailsRemoteDataSource();
        resultResponseMapper = new ResultResponseMapper();
    }

    @Override
    public Observable<ResultDTO> getWeatherDetails(String cityName, String appId) {
        return dataSource.getWeatherDetails(cityName, appId).map(resultResponseMapper::mapFromEntity);
    }
}
