package com.example.masterdetailexample.api;

import com.example.masterdetailexample.model.WeatherInfo;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String API_KEY = "4ae157471971fbc4b09f4b6b53c601eb";

    String UNITS_METRIC = "metric";
    String UNITS_IMPERAL = "imperial";

    @GET("current#cities")
    Flowable<List<WeatherInfo>> getCitiesWeather(
                                                 @Query("units") String units,
                                                 @Query("appid") String appid);

    @GET("forecast/daily")
    Flowable<WeatherInfo> getCityWeather(@Query("id") long id,
                                         @Query("cnt") int days,
                                         @Query("appid") String appid);
}
// For London and cnt=1
//  http://api.openweathermap.org/data/2.5/forecast/daily?id=2643743&appid=4ae157471971fbc4b09f4b6b53c601eb