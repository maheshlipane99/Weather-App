package com.example.weatherapp.DB.viewModel;

import android.app.Application;

import com.example.weatherapp.DB.dao.CityDao;
import com.example.weatherapp.DB.repository.CityRepo;
import com.example.weatherapp.DB.tabels.City;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class CityViewModel extends AndroidViewModel implements CityDao {

    CityRepo mCityRepo;

    public CityViewModel(Application application) {
        super(application);
        mCityRepo = new CityRepo(application);
    }

    @Override
    public LiveData<List<City>> getAllItem() {
        return mCityRepo.getAllItem();
    }

    @Override
    public City findItemById(int cityId) {
        return mCityRepo.findItemById(cityId);
    }

    @Override
    public City findItemByName(String name) {
        return mCityRepo.findItemByName(name);
    }

    @Override
    public int itemCount() {
        return mCityRepo.itemCount();
    }

    @Override
    public void insertItem(final City City) {
        mCityRepo.insertItem(City);
    }

    @Override
    public int deleteItem(final City City) {

        return mCityRepo.deleteItem(City);

    }

    @Override
    public int deleteAllItem() {
        return mCityRepo.deleteAllItem();
    }

}
