package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.weatherapp.DB.dao.CoordinatesDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Coordinates;
import com.example.weatherapp.DB.tabels.Main;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class CoordinatesRepo implements CoordinatesDao {

    private final String TAG = CoordinatesRepo.class.getSimpleName();

    private final CoordinatesDao coordinatesDao;
    Application application;

    @Inject
    public CoordinatesRepo(Application application) {
        this.coordinatesDao = AppDatabase.getAppDatabase(application).getCoordinatesDao();
        this.application = application;
    }


    @Override
        public LiveData<List<Coordinates>> getAllItem() {
        return coordinatesDao.getAllItem();
    }

    @Override
    public Coordinates findItemById(int id) {
        return coordinatesDao.findItemById(id);
    }

    @Override
    public Coordinates findItemByCityId(long id) {
        return coordinatesDao.findItemByCityId(id);
    }


    @Override
    public int itemCount() {
        return coordinatesDao.itemCount();
    }

    @Override
    public void insertItem(final Coordinates coordinates) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                coordinatesDao.insertItem(coordinates);
            }
        });
    }

    @Override
    public int deleteItem(final Coordinates coordinates) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                coordinatesDao.deleteItem(coordinates);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return coordinatesDao.deleteAllItem();
    }

}
