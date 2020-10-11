package com.example.weatherapp.DB.dao;

import com.example.weatherapp.DB.tabels.Wind;
import com.example.weatherapp.DB.tabels.Wind;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WindDao {

    @Query("SELECT * FROM Wind")
    LiveData<List<Wind>> getAllItem();

    @Query("SELECT * FROM Wind where id=:id")
    Wind findItemById(int id);

    @Query("SELECT * FROM Wind where cityId=:id")
    Wind findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Wind")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Wind Wind);

    @Delete
    int deleteItem(Wind Wind);

    @Query("DELETE FROM Wind")
    int deleteAllItem();
}
