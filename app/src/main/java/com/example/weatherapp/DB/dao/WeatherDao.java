package com.example.weatherapp.DB.dao;


import com.example.weatherapp.DB.tabels.Weather;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM Weather")
    LiveData<List<Weather>> getAllItem();

    @Query("SELECT * FROM Weather where id=:id")
    LiveData<Weather> findItemById(int id);

    @Query("SELECT * FROM Weather where cityId=:id")
    List<Weather> findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Weather")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Weather weather);

    @Delete
    int deleteItem(Weather weather);

    @Query("DELETE FROM Weather")
    int deleteAllItem();


}
