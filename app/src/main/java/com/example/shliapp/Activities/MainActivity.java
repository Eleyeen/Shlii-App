package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.example.shliapp.Fragment.SplashFragment;
import com.example.shliapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();

    }
    @Override
    protected void onPause () {
        super.onPause();

        SplashFragment.splashBoolean = false;

    }

    @Override
    protected void onResume () {
        super.onResume();
        SplashFragment.splashBoolean = true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed () {
        finishAffinity();
    }

//        @SuppressLint("MissingPermission")
//    private void getLastLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                        task -> {
//                            Location location = task.getResult();
//                            if (location == null) {
//                                requestNewLocationData();
//                            } else {
////                                latTextView.setText(location.getLatitude()+"");
////                                lonTextView.setText(location.getLongitude()+"");
//
//                                double latitude = location.getLatitude();
//                                double longitude = location.getLongitude();
//
//                                SharedPreferences spLatitude = this.getSharedPreferences("Latitude", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = spLatitude.edit();
//                                editor.putString("getLatitude", String.valueOf(location.getLatitude()));
//                                editor.apply();
//                                SharedPreferences spLongitude = this.getSharedPreferences("Longitude", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor1 = spLongitude.edit();
//                                editor1.putString("getLongitude", String.valueOf(location.getLongitude()));
//                                editor1.apply();
//
//
//                                Toast.makeText(this, "latitude :" + latitude, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(this, "longitude :" + longitude, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                );
//            } else {
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        } else {
//            requestPermissions();
//        }
//    }
//
//
//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(0);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                Looper.myLooper()
//        );
//
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//
//            double mLatitude = mLastLocation.getLatitude();
//            double mLongitude = mLastLocation.getLongitude();
//            Toast.makeText(MainActivity.this, "mLatitude" + mLatitude, Toast.LENGTH_SHORT).show();
//            Toast.makeText(MainActivity.this, "mLongitude" + mLongitude, Toast.LENGTH_SHORT).show();
//
//        }
//    };
//
//    private boolean checkPermissions() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        return false;
//    }
//
//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(
//                this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
//                PERMISSION_ID
//        );
//    }
//
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//                LocationManager.NETWORK_PROVIDER
//        );
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
//
//    }

}
