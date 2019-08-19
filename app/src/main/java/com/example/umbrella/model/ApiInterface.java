package com.example.umbrella.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //http://api.openweathermap.org/data/2.5/

    @GET("forecast?")
    //http://api.openweathermap.org/data/2.5/forecast?zip=30030,us&apikey=e72a767b2833bb17873b82db36b58634&units=imperial&cnt=8
    Call<Forecast> getForecast(@Query("zip") String zip,@Query("apikey") String apiKey, @Query("units") String units, @Query("cnt") String count);
    @GET("weather?")
    //http://api.openweathermap.org/data/2.5/weather?zip=94040,us
    Call<Current> getCurrentConditions(@Query("zip") String zip, @Query("apikey") String apiKey, @Query("units") String units);

}
