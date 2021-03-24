package com.avinash.domain.Usecase;

import com.avinash.domain.model.ResultDTO;
import com.avinash.domain.repository.IWeatherDetailsRepository;

import io.reactivex.Observable;

public class WeatherDetailsUseCase {
    private IWeatherDetailsRepository iWeatherDetailsRepository;

    public WeatherDetailsUseCase(IWeatherDetailsRepository iWeatherDetailsRepository) {
        this.iWeatherDetailsRepository = iWeatherDetailsRepository;
    }

    public Observable<ResultDTO> execute(String cityName, String appId) {

        return iWeatherDetailsRepository.getWeatherDetails(cityName, appId);


    }
}
