package com.example.weatherapp;

import com.example.weatherapp.DB.tabels.City;
import com.example.weatherapp.DB.tabels.Wind;

import java.util.List;

public class BaseResponse {

    List<City> categories;
    List<Wind> winds;

    public List<City> getCategories() {
        return categories;
    }

    public void setCategories(List<City> categories) {
        this.categories = categories;
    }

    public List<Wind> getWinds() {
        return winds;
    }

    public void setWinds(List<Wind> winds) {
        this.winds = winds;
    }
}
