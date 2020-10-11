package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.weatherapp.DB.dao.WeatherDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Weather;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class WeatherRepo implements WeatherDao {

    private final String TAG = WeatherRepo.class.getSimpleName();

    private final WeatherDao weatherDao;
    Application application;

    @Inject
    public WeatherRepo(Application application) {
        this.weatherDao = AppDatabase.getAppDatabase(application).getWeatherDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Weather>> getAllItem() {
        return weatherDao.getAllItem();
    }

    @Override
    public LiveData<Weather> findItemById(int id) {
        return weatherDao.findItemById(id);
    }

    @Override
    public List<Weather> findItemByCityId(long id) {
        return weatherDao.findItemByCityId(id);
    }

    @Override
    public int itemCount() {
        return weatherDao.itemCount();
    }


    @Override
    public void insertItem(final Weather weather) {
        Log.i(TAG, "insertItem: " + new Gson().toJson(weather));
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                weatherDao.insertItem(weather);
            }
        });
    }

    @Override
    public int deleteItem(final Weather weather) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                weatherDao.deleteItem(weather);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return weatherDao.deleteAllItem();
    }

}
