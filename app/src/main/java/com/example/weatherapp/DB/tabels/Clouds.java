package com.example.weatherapp.DB.tabels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Clouds {

    @PrimaryKey
    long id;
    long cityId;
    int all;
    long createdOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }
}
