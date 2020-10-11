package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.weatherapp.DB.dao.CloudsDao;
import com.example.weatherapp.DB.dao.CloudsDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Clouds;
import com.example.weatherapp.DB.tabels.Clouds;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class CloudsRepo implements CloudsDao {

    private final String TAG = CloudsRepo.class.getSimpleName();

    private final CloudsDao cloudsDao;
    Application application;

    @Inject
    public CloudsRepo(Application application) {
        this.cloudsDao = AppDatabase.getAppDatabase(application).getCloudsDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Clouds>> getAllItem() {
        return cloudsDao.getAllItem();
    }

    @Override
    public Clouds findItemById(int id) {
        return cloudsDao.findItemById(id);
    }

    @Override
    public Clouds findItemByCityId(long id) {
        return cloudsDao.findItemByCityId(id);
    }


    @Override
    public int itemCount() {
        return cloudsDao.itemCount();
    }

    @Override
    public void insertItem(final Clouds Clouds) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cloudsDao.insertItem(Clouds);
            }
        });
    }

    @Override
    public int deleteItem(final Clouds Clouds) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cloudsDao.deleteItem(Clouds);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return cloudsDao.deleteAllItem();
    }

}
