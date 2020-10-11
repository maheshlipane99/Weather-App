package com.example.weatherapp.DB.dao;

import com.example.weatherapp.DB.tabels.Sys;
import com.example.weatherapp.DB.tabels.Sys;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SysDao {

    @Query("SELECT * FROM Sys")
    LiveData<List<Sys>> getAllItem();

    @Query("SELECT * FROM Sys where id=:id")
    Sys findItemById(int id);

    @Query("SELECT * FROM Sys where cityId=:id")
    Sys findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Sys")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Sys Sys);

    @Delete
    int deleteItem(Sys Sys);

    @Query("DELETE FROM Sys")
    int deleteAllItem();
}
