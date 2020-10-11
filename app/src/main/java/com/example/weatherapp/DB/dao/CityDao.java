package com.example.weatherapp.DB.dao;




import com.example.weatherapp.DB.tabels.City;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CityDao {

    @Query("SELECT * FROM City")
    LiveData<List<City>> getAllItem();

    @Query("SELECT * FROM City where id=:id")
    City findItemById(int id);

    @Query("SELECT * FROM City where name=:name")
    City findItemByName(String name);

    @Query("SELECT COUNT(*) from City")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(City city);

    @Delete
    int deleteItem(City city);

    @Query("DELETE FROM City")
    int deleteAllItem();
}
