package com.example.shliapp.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Adapter.ChosseStoreAdapter;
import com.example.shliapp.Models.GetStoresModels.GetStoresModel;
import com.example.shliapp.Models.LocationModels.LocationNearStoreModels;
import com.example.shliapp.Models.LocationModels.Store;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ChooseStoreActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrowChooseStore)
    ImageView ivBackArrow;

//    @BindView(R.id./ivSearchChooseStore)
//    ImageView ivSearchIcon;
    @BindView(R.id.rvChoseStore)
    RecyclerView rvChoseStore;
    ArrayList<Store> listModels = new ArrayList<>();
    ChosseStoreAdapter adapter;
//    int PERMISSION_ID = 44;
    ProgressDialog progressDialog;
//    FusedLocationProviderClient mFusedLocationClient;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store);
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        rvChoseStore.setLayoutManager(new LinearLayoutManager(ChooseStoreActivity.this));
        rvChoseStore.setHasFixedSize(true);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        progressDialog  = new ProgressDialog(ChooseStoreActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

//        getStores();
//        getLastLocation();
        getUpdatedLocation();
        addLocation();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackArrowChooseStore:
               onBackPressed();

        }
    }

    private void getUpdatedLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_FINE);
        locationCritera.setAltitudeRequired(false);
        locationCritera.setBearingRequired(false);
        locationCritera.setCostAllowed(true);
        locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String providerName = locationManager.getBestProvider(locationCritera, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(providerName);

        Log.i("zma Latitude", "" + location.getLatitude());
        Log.i("zma Longitude", "" + location.getLongitude());
        AppRepository.mPutValue(this).putString("lat", String.valueOf(location.getLatitude())).commit();
        AppRepository.mPutValue(this).putString("lng", String.valueOf(location.getLongitude())).commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStores() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<GetStoresModel> call = services.getStores();

        call.enqueue(new Callback<GetStoresModel>() {
            @Override
            public void onResponse(Call<GetStoresModel> call, Response<GetStoresModel> response) {
                if (response.isSuccessful()) {
//                    listModels.addAll(response.body().getData());
//                    adapter = new ChosseStoreAdapter(ChooseStoreActivity.this, listModels);
//                    rvChoseStore.setAdapter(adapter);
//                    alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetStoresModel> call, Throwable t) {

            }
        });

    }

    private void addLocation() {

        String strUserID = GeneralUtills.getSharedPreferences(ChooseStoreActivity.this).getString("userId", "");


        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<LocationNearStoreModels> addLocation = services.AddLocation(strUserID, AppRepository.mLat(this), AppRepository.mLng(this));
        addLocation.enqueue(new Callback<LocationNearStoreModels>() {
            @Override
            public void onResponse(Call<LocationNearStoreModels> call, Response<LocationNearStoreModels> response) {

                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getStores());
                    String str = response.body().getStores().get(0).getStoreName();
                    Toast.makeText(ChooseStoreActivity.this, ""+str, Toast.LENGTH_SHORT).show();
                    GeneralUtills.putStringValueInEditor(ChooseStoreActivity.this, "itemTitle", str);

                    adapter = new ChosseStoreAdapter(ChooseStoreActivity.this, listModels);
                    rvChoseStore.setAdapter(adapter);
//                    alertDialog.dismiss();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<LocationNearStoreModels> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
            progressDialog.dismiss();
            }
        });

    }
//
//    @SuppressLint("MissingPermission")
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
//            Toast.makeText(ChooseStoreActivity.this, "mLatitude" + mLatitude, Toast.LENGTH_SHORT).show();
//            Toast.makeText(ChooseStoreActivity.this, "mLongitude" + mLongitude, Toast.LENGTH_SHORT).show();
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
//

}
