package com.example.weatherapp.DB.dao;

import com.example.weatherapp.DB.tabels.Clouds;
import com.example.weatherapp.DB.tabels.Clouds;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CloudsDao {

    @Query("SELECT * FROM Clouds")
    LiveData<List<Clouds>> getAllItem();

    @Query("SELECT * FROM Clouds where id=:id")
    Clouds findItemById(int id);

    @Query("SELECT * FROM Clouds where cityId=:id")
    Clouds findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Clouds")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Clouds Clouds);

    @Delete
    int deleteItem(Clouds Clouds);

    @Query("DELETE FROM Clouds")
    int deleteAllItem();
}
