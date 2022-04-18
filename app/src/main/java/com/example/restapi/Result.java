package com.example.restapi;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Result extends AppCompatActivity {
    double lat_value=50.440046;
    private final int REQUEST_PERMISSION_PHONE_STATE=1;
    double lng_value=40.4882367;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rasp.yandex.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ScheduleAPI service = retrofit.create(ScheduleAPI.class);
        ArrayList<ArrayList<String>> stations = new ArrayList<>();
        Call<Schedule> call = service.nearest_stations("49d27fe8-fd1b-476f-90d2-bff6ac3b65a7", lat_value, lng_value,50);
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                Schedule schedule = response.body();
                ListView listView = findViewById(R.id.listview);
                Log.d("RETROFIT", String.valueOf(schedule.getStations().size()));
                for (int i = 0; i < schedule.getStations().size(); i++) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(schedule.getStations().get(i).getTitle());
                    tmp.add(String.valueOf(schedule.getStations().get(i).getDistance()));
                    stations.add(tmp);
                }

                Log.d("RETROFIT", schedule.getStations().get(0).getTitle());
                listView.setAdapter(new StationsAdapet(Result.this, stations));
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Log.e("Network error", "Ретрофит не смог достучатся до нашего api - "+t.getMessage());
            }
        });
    }

}