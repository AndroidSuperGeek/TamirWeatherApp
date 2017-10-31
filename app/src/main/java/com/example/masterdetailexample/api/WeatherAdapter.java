package com.example.masterdetailexample.api;

import com.example.masterdetailexample.model.WeatherInfo;

import java.util.List;

import io.reactivex.Flowable;
//import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class WeatherAdapter {

    private static WeatherAdapter instance;

    private WeatherAPI weatherAPI;

    private WeatherAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create ())
                .baseUrl(WeatherAPI.BASE_URL)
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    public static WeatherAdapter getInstance() {
        if (instance == null) {
            instance = new WeatherAdapter();
        }
        return instance;
    }

//    public Flowable<List<WeatherInfo>> getCurrentWeather(double lat, double lon) {
//        return weatherAPI.getCitiesWeather (lat, lon, WeatherAPI.UNITS_METRIC, WeatherAPI.API_KEY);
//    }

    public Flowable<WeatherInfo> getCurrentWeatherForCity(final long cityId, int count) {
        Flowable<WeatherInfo> info = weatherAPI.getCityWeather (cityId, count,WeatherAPI.API_KEY);
        return info;
    }

}