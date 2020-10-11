package com.example.weatherapp.network;

import com.example.weatherapp.BaseResponse;
import com.example.weatherapp.DB.tabels.City;
import com.example.weatherapp.Utils.Const;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFeedsInterface {

    @GET("Shop/GetByAreaId/{AreaId}")
    Call<BaseResponse> getArea(@Path("AreaId") int mAreaId);


    @GET("weather?")
    Call<City> getCurrentWeather(@Query("q") String cityName, @Query("appid") String ApiKey);

   }