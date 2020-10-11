package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.weatherapp.DB.dao.SysDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.Sys;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class SysRepo implements SysDao {

    private final String TAG = SysRepo.class.getSimpleName();

    private final SysDao sysDao;
    Application application;

    @Inject
    public SysRepo(Application application) {
        this.sysDao = AppDatabase.getAppDatabase(application).getSysDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Sys>> getAllItem() {
        return sysDao.getAllItem();
    }

    @Override
    public Sys findItemById(int id) {
        return sysDao.findItemById(id);
    }

    @Override
    public Sys findItemByCityId(long id) {
        return sysDao.findItemByCityId(id);
    }


    @Override
    public int itemCount() {
        return sysDao.itemCount();
    }

    @Override
    public void insertItem(final Sys Sys) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                sysDao.insertItem(Sys);
            }
        });
    }

    @Override
    public int deleteItem(final Sys Sys) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                sysDao.deleteItem(Sys);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return sysDao.deleteAllItem();
    }

}
