package com.avinash.weatherdemo.viewmodel.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avinash.data.source.localDB.repository.DataBaseRepository;
import com.avinash.data.source.localDB.table.WeatherTable;
import com.avinash.data.source.remote.repository.WeatherDetailsRepository;
import com.avinash.domain.Usecase.WeatherDetailsUseCase;
import com.avinash.weatherdemo.util.ApiResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherDetailsViewModel extends BaseViewModel {
    private WeatherDetailsUseCase weatherDetailsUseCase;
    private DataBaseRepository dataBaseRepository;
    private MutableLiveData<ApiResponse> mWeatherDetailsLiveData = new MutableLiveData<>();
    private LiveData<List<WeatherTable>> listLiveData;
    private LiveData<List<WeatherTable>> selectedDataLiveData;

    public WeatherDetailsViewModel(@NonNull Application application) {
        super(application);
        WeatherDetailsRepository repository = new WeatherDetailsRepository();
        weatherDetailsUseCase = new WeatherDetailsUseCase(repository);
        dataBaseRepository = new DataBaseRepository(application);
        listLiveData = dataBaseRepository.getAllWeatherTables();
    }

    //region getWeatherDetails

    public void getWeatherDetails(String cityName, String appId) {

        disposables.add(weatherDetailsUseCase.execute(cityName, appId).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> mWeatherDetailsLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> mWeatherDetailsLiveData.setValue(ApiResponse.success(result)),
                        throwable -> mWeatherDetailsLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public MutableLiveData<ApiResponse> getWeatherDetailsLiveDataData() {
        return mWeatherDetailsLiveData;
    }

    //endregion getWeatherDetails

    //region DB Function
    public void insert(WeatherTable weatherTable) {
        dataBaseRepository.insert(weatherTable);
    }

    public void update(WeatherTable weatherTable) {
        dataBaseRepository.update(weatherTable);
    }

    public void delete(WeatherTable weatherTable) {
        dataBaseRepository.delete(weatherTable);
    }

    public void delete() {
        dataBaseRepository.deleteAllWeatherTables();
    }

    public void delete(String city) {
        dataBaseRepository.deleteAllWeatherTables(city);
    }

    public LiveData<List<WeatherTable>> getWeatherTable(String city) {
        return dataBaseRepository.getWeatherTable(city);
    }

    public LiveData<List<WeatherTable>> getWeatherTables() {
        return listLiveData;
    }
}
