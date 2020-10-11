package com.example.weatherapp.DB.database;

import android.content.Context;

import com.example.weatherapp.DB.converter.ArrayListConverter;
import com.example.weatherapp.DB.converter.DateConverter;
import com.example.weatherapp.DB.dao.CityDao;
import com.example.weatherapp.DB.dao.CloudsDao;
import com.example.weatherapp.DB.dao.CoordinatesDao;
import com.example.weatherapp.DB.dao.SysDao;
import com.example.weatherapp.DB.dao.WeatherDao;
import com.example.weatherapp.DB.dao.MainDao;
import com.example.weatherapp.DB.dao.WindDao;
import com.example.weatherapp.DB.tabels.City;
import com.example.weatherapp.DB.tabels.Clouds;
import com.example.weatherapp.DB.tabels.Main;
import com.example.weatherapp.DB.tabels.Sys;
import com.example.weatherapp.DB.tabels.Weather;
import com.example.weatherapp.DB.tabels.Coordinates;
import com.example.weatherapp.DB.tabels.Wind;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {
        City.class,
        Weather.class,
        Coordinates.class,
        Main.class,
        Clouds.class,
        Sys.class,
        Wind.class},
        version = 1,
        exportSchema = false)
@TypeConverters({DateConverter.class, ArrayListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static final String DATABASE_NAME = "weather_app";


    public abstract CityDao getCategoryDao();

    public abstract WeatherDao getWeatherDao();

    public abstract MainDao getMainDao();

    public abstract CoordinatesDao getCoordinatesDao();

    public abstract CloudsDao getCloudsDao();

    public abstract SysDao getSysDao();

    public abstract WindDao getWindDao();


    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getAppDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppDatabase database = AppDatabase.getAppDatabase(appContext);
                        database.setDatabaseCreated();
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}

