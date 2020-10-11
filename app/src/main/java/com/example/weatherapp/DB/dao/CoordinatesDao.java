package com.example.weatherapp.DB.dao;






import com.example.weatherapp.DB.tabels.Coordinates;
import com.example.weatherapp.DB.tabels.Main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CoordinatesDao {

    @Query("SELECT * FROM Coordinates")
    LiveData<List<Coordinates>> getAllItem();

    @Query("SELECT * FROM Coordinates where id=:id")
    Coordinates findItemById(int id);

    @Query("SELECT * FROM Coordinates where cityId=:id")
    Coordinates findItemByCityId(long id);

    @Query("SELECT COUNT(*) from Coordinates")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Coordinates coordinates);

    @Delete
    int deleteItem(Coordinates coordinates);

    @Query("DELETE FROM Coordinates")
    int deleteAllItem();
}
