package com.example.weatherapp.DB.dao;

import com.example.weatherapp.DB.tabels.Main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MainDao {

    @Query("SELECT * FROM Main")
    LiveData<List<Main>> getAllItem();

    @Query("SELECT * FROM Main where id=:id")
    Main findItemById(int id);

    @Query("SELECT * FROM Main where cityId=:id")
    Main findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Main")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Main main);

    @Delete
    int deleteItem(Main main);

    @Query("DELETE FROM Main")
    int deleteAllItem();
}
