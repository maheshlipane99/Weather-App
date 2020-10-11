package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.weatherapp.DB.dao.WindDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Wind;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class WindRepo implements WindDao {

    private final String TAG = WindRepo.class.getSimpleName();

    private final WindDao windDao;
    Application application;

    @Inject
    public WindRepo(Application application) {
        this.windDao = AppDatabase.getAppDatabase(application).getWindDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Wind>> getAllItem() {
        return windDao.getAllItem();
    }

    @Override
    public Wind findItemById(int id) {
        return windDao.findItemById(id);
    }

    @Override
    public Wind findItemByCityId(long id) {
        return windDao.findItemByCityId(id);
    }


    @Override
    public int itemCount() {
        return windDao.itemCount();
    }

    @Override
    public void insertItem(final Wind Wind) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                windDao.insertItem(Wind);
            }
        });
    }

    @Override
    public int deleteItem(final Wind Wind) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                windDao.deleteItem(Wind);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return windDao.deleteAllItem();
    }

}
