package com.example.weatherapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.DB.tabels.City;
import com.example.weatherapp.DB.tabels.Weather;
import com.example.weatherapp.DB.viewModel.CityViewModel;
import com.example.weatherapp.R;
import com.example.weatherapp.Utils.AppUtil;
import com.example.weatherapp.Utils.Const;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.textMessage)
    public TextView textMessage;
    @BindView(R.id.imageNoCity)
    ImageView imageNoCity;

    @BindView(R.id.layoutButtons)
    public LinearLayout layoutButtons;

    @BindView(R.id.textCity)
    TextView textCity;
    @BindView(R.id.textTemp)
    TextView textTemp;
    @BindView(R.id.textWeatherStatus)
    TextView textWeatherStatus;
    @BindView(R.id.textHumidity)
    TextView textHumidity;
    @BindView(R.id.textWind)
    TextView textWind;
    @BindView(R.id.textTempMaxMin)
    TextView textTempMaxMin;
    @BindView(R.id.imageIcon)
    ImageView imageIcon;
    @BindView(R.id.editSearch)
    EditText editSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    CityViewModel mCityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnSearch.setOnClickListener(this::onClick);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                  onClick(btnSearch);
                }
                return false;
            }
        });
        showNoData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch: {
                if (editSearch.getText().toString().isEmpty() || editSearch.getText().toString().length() < 3) {
                    Toast.makeText(this, "Enter city name", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressBar();
                mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);
                City mCity = mCityViewModel.findItemByName(editSearch.getText().toString());
                Log.i(TAG, "onChanged: " + new Gson().toJson(mCity));
                if (mCity != null) {
                    Weather mWeather = mCity.getWeather().get(0);
                    textCity.setText(mCity.getName()
                            + ", " + mCity.getSys().getCountry());

                    textTemp.setText(mCity.getMain().getTemp()
                            + getString(R.string.temp_unit_label));

                    textWeatherStatus.setText(AppUtil.getWeatherStatus(mWeather.getId()));
                    textHumidity.setText(getString(R.string.humidity_label)
                            + " " + mCity.getMain().getHumidity()
                            + getString(R.string.humidity_unit_label));

                    textWind.setText(getString(R.string.wind_label)
                            + " " + mCity.getWind().getSpeed()
                            + getString(R.string.wind_unit_label));

                    textTempMaxMin.setText( mCity.getMain().getTemp_max()
                            + getString(R.string.temp_unit_label)
                            + getString(R.string.max_temp_label)
                            + " & " + mCity.getMain().getTemp_min()
                            + getString(R.string.temp_unit_label)
                            + getString(R.string.min_temp_label));

                    AppUtil.setWeatherIcon(this, imageIcon, mWeather.getId());
                    showDataView();
                } else {
                    showNoData();
                }
                break;
            }
        }
    }

    public void showDataView() {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.GONE);
        imageNoCity.setVisibility(View.GONE);
        layoutButtons.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        layoutButtons.setVisibility(View.GONE);
        imageNoCity.setVisibility(View.GONE);
    }

    public void showNoData() {
        layoutButtons.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.VISIBLE);
        imageNoCity.setVisibility(View.VISIBLE);
        textMessage.setText(getString(R.string.no_city_found_message));
    }

}
