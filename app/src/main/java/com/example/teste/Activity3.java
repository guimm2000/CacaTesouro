package com.example.teste;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Activity3 extends AppCompatActivity {
    public double latitude = Activity2.getLatitude();
    public double longitude = Activity2.getLongitude();
    public float raio = Activity2.getRaio();
    private FusedLocationProviderClient fusedLocationClient;
    public Location origin;
    public Location dest;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        texto = (TextView) findViewById(R.id.Dist);
        dest = new Location("provider");
        dest.setLatitude(latitude);
        dest.setLongitude(longitude);
        getLastLocation();
        mHandler.post(timerTask);
        //texto.setText(String.valueOf(latitude));
    }

    public Handler mHandler = new Handler();
    public Runnable timerTask = new Runnable() {
        @Override
        public void run() {
            getLastLocation();
            System.out.println(origin);
            if(origin != null) {
                float dist = origin.distanceTo(dest);
                texto.setText(String.valueOf(dist) + " metros");
                if(checarRaio(raio, dist)) {
                    mHandler.postDelayed(timerTask, 1000000);
                    mHandler.removeCallbacks(timerTask);
                }
                mHandler.postDelayed(timerTask, 1000);
            }
        }
    };

    public void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(100, null).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        origin = location;
                    }
                }
            });
        }
    }

    public boolean checarRaio(float raio, float dist) {
        if(raio >= dist) {
            System.out.println("Ganhou");
            AlertDialog alt = new AlertDialog.Builder(this).create();
            alt.setTitle("Ganhou");
            alt.setMessage("Parabéns, você ganhou!");
            alt.setButton(AlertDialog.BUTTON_POSITIVE, "Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finishAffinity();
                    System.exit(0);
                }
            });

            alt.setButton(AlertDialog.BUTTON_NEGATIVE, "Jogar Novamente", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(Activity3.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            alt.show();
            return true;
        }
        return false;
    }



    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(timerTask);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler.post(timerTask);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(timerTask);
    }
}