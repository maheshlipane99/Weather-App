package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.weatherapp.DB.dao.MainDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Main;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class MainRepo implements MainDao {

    private final String TAG = MainRepo.class.getSimpleName();

    private final MainDao mainDao;
    Application application;

    @Inject
    public MainRepo(Application application) {
        this.mainDao = AppDatabase.getAppDatabase(application).getMainDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Main>> getAllItem() {
        return mainDao.getAllItem();
    }

    @Override
    public Main findItemById(int id) {
        return mainDao.findItemById(id);
    }

    @Override
    public Main findItemByCityId(long id) {
        return mainDao.findItemByCityId(id);
    }


    @Override
    public int itemCount() {
        return mainDao.itemCount();
    }

    @Override
    public void insertItem(final Main main) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mainDao.insertItem(main);
            }
        });
    }

    @Override
    public int deleteItem(final Main main) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mainDao.deleteItem(main);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return mainDao.deleteAllItem();
    }

}
