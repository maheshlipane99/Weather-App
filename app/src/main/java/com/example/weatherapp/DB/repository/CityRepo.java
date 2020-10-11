package com.example.weatherapp.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.weatherapp.BaseResponse;
import com.example.weatherapp.DB.dao.CityDao;
import com.example.weatherapp.DB.dao.CloudsDao;
import com.example.weatherapp.DB.dao.MainDao;
import com.example.weatherapp.DB.dao.SysDao;
import com.example.weatherapp.DB.dao.WeatherDao;
import com.example.weatherapp.DB.dao.CoordinatesDao;
import com.example.weatherapp.DB.dao.WindDao;
import com.example.weatherapp.DB.database.AppDatabase;
import com.example.weatherapp.DB.tabels.City;
import com.example.weatherapp.DB.tabels.Clouds;
import com.example.weatherapp.DB.tabels.Main;
import com.example.weatherapp.DB.tabels.Sys;
import com.example.weatherapp.DB.tabels.Weather;
import com.example.weatherapp.DB.tabels.Wind;
import com.example.weatherapp.DB.tabels.Coordinates;
import com.example.weatherapp.Utils.Const;
import com.example.weatherapp.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;


@Singleton
public class CityRepo implements CityDao {

    private final String TAG = CityRepo.class.getSimpleName();

    private final CityDao cityDao;
    private final WeatherDao weatherDao;
    private final MainDao mainDao;
    private final CoordinatesDao coordinatesDao;
    private final SysDao sysDao;
    private final WindDao windDao;
    private final CloudsDao cloudsDao;
    Application application;

    @Inject
    public CityRepo(Application application) {
        this.cityDao = AppDatabase.getAppDatabase(application).getCategoryDao();
        this.weatherDao = AppDatabase.getAppDatabase(application).getWeatherDao();
        this.coordinatesDao = AppDatabase.getAppDatabase(application).getCoordinatesDao();
        this.mainDao = AppDatabase.getAppDatabase(application).getMainDao();
        this.sysDao = AppDatabase.getAppDatabase(application).getSysDao();
        this.windDao = AppDatabase.getAppDatabase(application).getWindDao();
        this.cloudsDao = AppDatabase.getAppDatabase(application).getCloudsDao();
        this.application = application;
    }


    @Override
    public LiveData<List<City>> getAllItem() {
        return cityDao.getAllItem();
    }

    @Override
    public City findItemById(int id) {
        City mCity = cityDao.findItemById(id);
        if (mCity!=null){
            mCity.setClouds(cloudsDao.findItemByCityId(id));
            mCity.setCoord(coordinatesDao.findItemByCityId(id));
            mCity.setMain(mainDao.findItemByCityId(id));
            mCity.setSys(sysDao.findItemByCityId(id));
            mCity.setWeather(weatherDao.findItemByCityId(id));
        }

        return mCity;
    }

    @Override
    public City findItemByName(String name) {
        City mCity = cityDao.findItemByName(name);
        if (mCity != null) {
            mCity.setClouds(cloudsDao.findItemByCityId(mCity.getId()));
            mCity.setCoord(coordinatesDao.findItemByCityId(mCity.getId()));
            mCity.setMain(mainDao.findItemByCityId(mCity.getId()));
            mCity.setSys(sysDao.findItemByCityId(mCity.getId()));
            mCity.setWind(windDao.findItemByCityId(mCity.getId()));
            mCity.setWeather(weatherDao.findItemByCityId(mCity.getId()));
        } else {
            getDataFromServer(name);
        }
        return mCity;
    }

    @Override
    public int itemCount() {
        return cityDao.itemCount();
    }

    @Override
    public void insertItem(final City city) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                city.setCreatedOn(new Date().getTime());
                cityDao.insertItem(city);
                for (int i = 0; i < city.getWeather().size(); i++) {
                    Weather mWeather = city.getWeather().get(i);
                    mWeather.setCreatedOn(new Date().getTime());
                    mWeather.setCityId(city.getId());
                    weatherDao.insertItem(mWeather);
                }
                Coordinates mCoordinates = city.getCoord();
                mCoordinates.setId(new Date().getTime());
                mCoordinates.setCreatedOn(new Date().getTime());
                mCoordinates.setCityId(city.getId());
                coordinatesDao.insertItem(mCoordinates);

                Main mMain = city.getMain();
                mMain.setId(new Date().getTime());
                mMain.setCreatedOn(new Date().getTime());
                mMain.setCityId(city.getId());
                mainDao.insertItem(mMain);

                Wind mWind = city.getWind();
                mWind.setId(new Date().getTime());
                mWind.setCreatedOn(new Date().getTime());
                mWind.setCityId(city.getId());
                windDao.insertItem(mWind);

                Clouds mClouds = city.getClouds();
                mClouds.setId(new Date().getTime());
                mClouds.setCreatedOn(new Date().getTime());
                mClouds.setCityId(city.getId());
                cloudsDao.insertItem(mClouds);

                Sys mSys = city.getSys();
                mSys.setCityId(city.getId());
                mSys.setCreatedOn(new Date().getTime());
                sysDao.insertItem(mSys);
            }
        });
    }

    @Override
    public int deleteItem(final City city) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cityDao.deleteItem(city);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return cityDao.deleteAllItem();
    }

    private void getDataFromServer(String cityName) {

        Call<City> call = RestClient.getApiService().getCurrentWeather(cityName, Const.API_KEY);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, retrofit2.Response<City> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = new Gson().toJson(response.body());
                        Log.i(TAG, "onResponse: " + json);
                        City mCity = new Gson().fromJson(json, City.class);
                        insertItem(mCity);

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

                if (t instanceof ConnectException) {
                    Log.e(TAG, "Error ConnectException");
                } else if (t instanceof SocketTimeoutException) {
                    Log.e(TAG, "Error SocketTimeoutException");
                    getDataFromServer(cityName);
                } else if (t instanceof JsonSyntaxException) {
                    Log.e(TAG, "Error JsonSyntaxException");
                } else {
                    Log.e(TAG, "Error :", t);
                }
            }
        });
    }

}
