package com.avinash.data.source.localDB.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.avinash.data.source.localDB.AppDataBase;
import com.avinash.data.source.localDB.DAO.WeatherTableDAO;

import com.avinash.data.source.localDB.table.WeatherTable;

import java.util.List;

public class DataBaseRepository {
    WeatherTableDAO weatherTableDAO;
    private LiveData<List<WeatherTable>> allWeatherTable;

    public DataBaseRepository(Application application) { //application is subclass of context
        AppDataBase database = AppDataBase.getInstance(application);
        weatherTableDAO = database.weatherTableDAO();
        allWeatherTable = weatherTableDAO.getAll();
    }

    public void insert(WeatherTable weatherTable) {
        new InsertWeatherTableAsyncTask(weatherTableDAO).execute(weatherTable);
    }

    public void update(WeatherTable WeatherTable) {
        new UpdateWeatherTableAsyncTask(weatherTableDAO).execute(WeatherTable);
    }

    public void delete(WeatherTable WeatherTable) {
        new DeleteWeatherTableAsyncTask(weatherTableDAO).execute(WeatherTable);
    }

    public void deleteAllWeatherTables() {
        new DeleteAllWeatherTablesAsyncTask(weatherTableDAO).execute();
    }
    public void deleteAllWeatherTables(String city) {
        new DeleteAllWeatherTablesOfCityAsyncTask(weatherTableDAO,city).execute();
    }

    public LiveData<List<WeatherTable>> getWeatherTable(String city) {
        return weatherTableDAO.getWeatherTable(city);
    }

    public LiveData<List<WeatherTable>> getAllWeatherTables() {
        return allWeatherTable;
    }


    private static class InsertWeatherTableAsyncTask extends AsyncTask<WeatherTable, Void, Void> { //static : doesnt have reference to the
        // repo itself otherwise it could cause memory leak!
        private WeatherTableDAO weatherTableDAO;

        private InsertWeatherTableAsyncTask(WeatherTableDAO weatherTableDAO) {
            this.weatherTableDAO = weatherTableDAO;
        }

        @Override
        protected Void doInBackground(WeatherTable... WeatherTables) { // ...  is similar to array
            weatherTableDAO.Insert(WeatherTables[0]); //single WeatherTable
            return null;
        }
    }

    private static class UpdateWeatherTableAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private WeatherTableDAO weatherTableDAO;

        private UpdateWeatherTableAsyncTask(WeatherTableDAO weatherTableDAO) { //constructor as the class is static
            this.weatherTableDAO = weatherTableDAO;
        }

        @Override
        protected Void doInBackground(WeatherTable... WeatherTables) {
            weatherTableDAO.Update(WeatherTables[0]);
            return null;
        }
    }

    private static class DeleteWeatherTableAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private WeatherTableDAO weatherTableDAO;

        private DeleteWeatherTableAsyncTask(WeatherTableDAO weatherTableDAO) {
            this.weatherTableDAO = weatherTableDAO;
        }

        @Override
        protected Void doInBackground(WeatherTable... WeatherTables) {
            weatherTableDAO.Delete(WeatherTables[0]);
            return null;
        }
    }

    private static class DeleteAllWeatherTablesAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeatherTableDAO weatherTableDAO;

        private DeleteAllWeatherTablesAsyncTask(WeatherTableDAO weatherTableDAO) {
            this.weatherTableDAO = weatherTableDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherTableDAO.DeleteAll();
            return null;
        }
    }

    private static class DeleteAllWeatherTablesOfCityAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeatherTableDAO weatherTableDAO;
        private String city;
        private DeleteAllWeatherTablesOfCityAsyncTask(WeatherTableDAO weatherTableDAO,String city) {
            this.weatherTableDAO = weatherTableDAO;
            this.city=city;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherTableDAO.DeleteAll(city);
            return null;
        }
    }




}
