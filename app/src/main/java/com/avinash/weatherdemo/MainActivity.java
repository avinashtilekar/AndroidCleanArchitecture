package com.avinash.weatherdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avinash.data.source.localDB.table.WeatherTable;
import com.avinash.domain.model.ResultDTO;
import com.avinash.weatherdemo.util.APIError;
import com.avinash.weatherdemo.util.ApiResponse;
import com.avinash.weatherdemo.util.AppUtility;
import com.avinash.weatherdemo.util.ErrorUtils;
import com.avinash.weatherdemo.util.ToastMessageUtility;
import com.avinash.weatherdemo.viewmodel.base.WeatherDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WeatherDetailsViewModel weatherDetailsViewModel;
    AutoCompleteTextView actCity;
    ImageView ivSearch;
    TextView tvCity, tvWeather, tvTemperature, tvHumidity, tvSource;
    LinearLayout llDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDetailsViewModel = new ViewModelProvider(MainActivity.this).get(WeatherDetailsViewModel.class);
        weatherDetailsViewModel.getWeatherDetailsLiveDataData().observe(this, this::onWeatherDetails);


        actCity = findViewById(R.id.act_city);
        ivSearch = findViewById(R.id.iv_search);
        tvCity = findViewById(R.id.tv_city);
        tvWeather = findViewById(R.id.tv_weather);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvHumidity = findViewById(R.id.tv_humidity);
        llDetails = findViewById(R.id.ll_details);
        llDetails.setVisibility(View.GONE);
        tvSource = findViewById(R.id.tv_source);


        weatherDetailsViewModel.getWeatherTables().observe(MainActivity.this, new Observer<List<WeatherTable>>() {
            @Override
            public void onChanged(List<WeatherTable> weatherTables) {
                if (weatherTables != null && weatherTables.size() > 0) {
                    ArrayList<String> array = new ArrayList<>();
                    for (WeatherTable table : weatherTables) {
                        if (!array.contains(table.getCity())) {
                            array.add(table.getCity());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            TextView text = (TextView) view.findViewById(android.R.id.text1);

                            text.setTextAppearance(MainActivity.this, R.style.LabelMedium_RobotoBold_AppColor);

                            return view;
                        }
                    };

                    actCity.setThreshold(1);
                    actCity.setAdapter(adapter);

                }
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(actCity.getText().toString()) || actCity.getText().toString().length() < 3) {
                    ToastMessageUtility.showErrorToastMessage(MainActivity.this, "please enter atleast three digit of city");
                } else {
                    weatherDetailsViewModel.getWeatherTable(actCity.getText().toString()).observe(MainActivity.this, new Observer<List<WeatherTable>>() {
                        @Override
                        public void onChanged(List<WeatherTable> weatherTables) {
                            if (!TextUtils.isEmpty(actCity.getText().toString())) {
                                llDetails.setVisibility(View.GONE);
                                if (weatherTables != null && weatherTables.size() > 0 && llDetails.getVisibility() == View.GONE) {
                                    HashMap<String, Long> dateDiff = AppUtility.getDateTimeDiffrence(weatherTables.get(0).getDateTime(), AppUtility.getCurrentDateTime());
                                    if (dateDiff.get(AppUtility.DAY) > 0 || dateDiff.get(AppUtility.HOURS) > 0 || dateDiff.get(AppUtility.MINUTE) > AppUtility.MAX_VALIDE_MINUTE_FOR_RECORD) {
                                        //Delete expaired data
                                        weatherDetailsViewModel.delete(weatherTables.get(0).getCity());
                                        //Wait to Delete Redownload Data
                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                weatherDetailsViewModel.getWeatherDetails(actCity.getText().toString(), "094aa776d64c50d5b9e9043edd4ffd00");
                                            }
                                        }, 300);


                                    } else {
                                        llDetails.setVisibility(View.VISIBLE);
                                        tvCity.setText(weatherTables.get(0).getCity());
                                        tvWeather.setText(weatherTables.get(0).getWeather());
                                        tvTemperature.setText(weatherTables.get(0).getTemperature());
                                        tvHumidity.setText(weatherTables.get(0).getHumidity());
                                        tvSource.setText("From Local Source (" + weatherTables.get(0).getDateTime() + ")");
                                        tvSource.setTextAppearance(MainActivity.this, R.style.LabelSmall_RobotoBold_Red);
                                    }
                                } else {
                                    weatherDetailsViewModel.getWeatherDetails(actCity.getText().toString(), "094aa776d64c50d5b9e9043edd4ffd00");
                                }
                            }
                        }
                    });

                }

            }
        });
    }

    private synchronized void onWeatherDetails(ApiResponse mApiResponse) {
        switch (mApiResponse.status) {

            case LOADING:
                llDetails.setVisibility(View.GONE);

                break;

            case SUCCESS:
                WeatherTable weatherTable = new WeatherTable();
                ResultDTO resultDTO = (ResultDTO) mApiResponse.data;
                if (resultDTO != null) {


                    actCity.setText("");
                    llDetails.setVisibility(View.VISIBLE);
                    tvCity.setText(resultDTO.getName());
                    if (resultDTO.getWeatherDTO() != null && resultDTO.getWeatherDTO().size() > 0) {
                        tvWeather.setText(resultDTO.getWeatherDTO().get(0).getDescription());
                    }
                    tvTemperature.setText("Min " + resultDTO.getMainDTO().getTempMin() + "-Max " + resultDTO.getMainDTO().getTempMax());
                    tvHumidity.setText("" + resultDTO.getMainDTO().getHumidity());
                    tvSource.setText("From Remote Source (" + AppUtility.getCurrentDateTime() + ")");
                    tvSource.setTextAppearance(MainActivity.this, R.style.LabelSmall_RobotoBold_AppColor);

                    try {

                        weatherTable.setCity(resultDTO.getName());
                        weatherTable.setHumidity(tvHumidity.getText().toString());
                        weatherTable.setTemperature(tvTemperature.getText().toString());
                        weatherTable.setWeather(tvWeather.getText().toString());
                        weatherTable.setDateTime(AppUtility.getCurrentDateTime());

                        weatherDetailsViewModel.delete(resultDTO.getName());
                        //Wait to Delete Redownload Data
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                weatherDetailsViewModel.insert(weatherTable);
                            }
                        }, 300);

                    } catch (Exception e) {

                    }

                }
                break;

            case ERROR:
                APIError apiError = ErrorUtils.parseThrowable(mApiResponse.error);
                ToastMessageUtility.showErrorToastMessage(MainActivity.this, apiError.getMessage());
                break;

        }
    }
}