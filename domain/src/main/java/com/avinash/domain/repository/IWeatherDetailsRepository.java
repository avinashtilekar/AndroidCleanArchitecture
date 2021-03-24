package com.avinash.domain.repository;

import com.avinash.domain.model.ResultDTO;

import io.reactivex.Observable;
public interface IWeatherDetailsRepository {
    public Observable<ResultDTO> getWeatherDetails(String cityName, String appId);
}
