package com.example.restapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScheduleAPI {
    @GET("v3.0/nearest_stations")
    Call<Schedule> nearest_stations(@Query("apikey") String APIkey, @Query("lat") double lat, @Query("lng") double lng, @Query("dis") Integer dis);
}
