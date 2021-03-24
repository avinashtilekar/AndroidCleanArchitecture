package com.avinash.data.source.localDB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.avinash.data.source.localDB.table.WeatherTable;

import java.util.List;

@Dao
public interface WeatherTableDAO {
    @Insert
    void Insert(WeatherTable note);

    @Update
        //(onConflict = OnConflictStrategy.REPLACE)
    void Update(WeatherTable note);

    @Delete
    void Delete(WeatherTable note);

    @Query("DELETE FROM weather_table")
    void DeleteAll();

    @Query("DELETE FROM weather_table where city like :city")
    void DeleteAll(String city);

    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherTable>> getAll();  //updates and returns

    @Query("SELECT * FROM weather_table where city like :city")
    LiveData<List<WeatherTable>> getWeatherTable(String city);

}
