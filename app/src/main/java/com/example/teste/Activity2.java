package com.example.teste;


import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Activity2 extends AppCompatActivity {

    public Location teste;
    public static double latitude;
    public static double longitude;
    public static float raio;

    public EditText latitudeInput;
    public EditText longitudeInput;
    public EditText raioInput;

    private FusedLocationProviderClient fusedLocationClient;

    public static double getLatitude(){
        return latitude;
    }

    public static double getLongitude(){
        return longitude;
    }

    public static float getRaio(){
        return raio;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        latitudeInput = (EditText) findViewById(R.id.LatitudeText);
        longitudeInput = (EditText) findViewById(R.id.LongitudeText);
        raioInput = (EditText) findViewById(R.id.RaioText);

    }


    public void clicaAi(View view) {
        //getLastLocation();
        try {
            latitude = Double.valueOf(latitudeInput.getText().toString());
            longitude = Double.valueOf(longitudeInput.getText().toString());
            raio = Float.valueOf(raioInput.getText().toString());
        }
        catch (Exception e) {
            Toast.makeText(this, "Valores Incorretos", Toast.LENGTH_SHORT).show();
        }
        openActivity3();
    }

    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    public void askPermission() {
        ActivityCompat.requestPermissions(Activity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
    }
}