package com.example.restapi;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    Button bLocation;
    Button search;
    EditText lat;
    EditText lng;
    double lat_value;
    double lng_value;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bLocation = findViewById(R.id.button);
        search = findViewById(R.id.button2);
        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        bLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            12);

                    Log.e("PERMISSIONS", "Нету разрешенений");
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                intent.putExtra("lat", lat.getText());
                intent.putExtra("lng", lng.getText());
                startActivity(intent);
            }
        });



//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.rasp.yandex.net/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ScheduleAPI service = retrofit.create(ScheduleAPI.class);
//        Call<Schedule> call = service.nearest_stations("49d27fe8-fd1b-476f-90d2-bff6ac3b65a7", lat_value, lng_value,100);


    }
    //49d27fe8-fd1b-476f-90d2-bff6ac3b65a7
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    lat_value = location.getLatitude();
                    lng_value = location.getLongitude();

                }
                lat.setText(String.valueOf(lat_value));
                lng.setText(String.valueOf(lng_value));
            }
        });
    }

}