package com.example.shliapp.Fragment;

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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shliapp.Activities.AddStorageActivity;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.StartBottomActivity;
import com.example.shliapp.Adapter.StorageAdapter;
import com.example.shliapp.Models.StorageModelss.DatumStorage;
import com.example.shliapp.Models.GetStorageModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class StorageFragment extends Fragment implements View.OnClickListener {

    View view;
    @BindView(R.id.ivPlusIconStorage)
    ImageView ivPlusIcon;
    @BindView(R.id.rvStorage)
    RecyclerView rvShoppingList;

    private ArrayList<DatumStorage> itemLists;
    ProgressDialog progressDialog;

    private FusedLocationProviderClient mFusedLocationClient;
    private int PERMISSION_ID = 44;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_storage, container, false);
   initListeners();
        initUI();
        return view;
    }

    private void initListeners() {
        ButterKnife.bind(this, view);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        progressDialog  = new ProgressDialog(getContext());
        ivPlusIcon.setOnClickListener(this);
        rvShoppingList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvShoppingList.setHasFixedSize(true);
        itemLists = new ArrayList<DatumStorage>() ;
        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        getStorage();
        getLastLocation();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStorage() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<GetStorageModel> call = services.getStorage(GeneralUtills.getSharedPreferences(getContext()).getString("userId" , ""));

        call.enqueue(new Callback<GetStorageModel>() {
            @Override
            public void onResponse(Call<GetStorageModel> call, Response<GetStorageModel> response) {
                if (response.isSuccessful()) {
                    itemLists.addAll(response.body().getData());

                    StorageAdapter adapter = new StorageAdapter(getContext(), itemLists);
                    rvShoppingList.setAdapter(adapter);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetStorageModel> call, Throwable t) {
progressDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivPlusIconStorage:
                Intent intent = new Intent(getContext(), AddStorageActivity.class);
                startActivity(intent);
                break;
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
//                                latTextView.setText(location.getLatitude()+"");
//                                lonTextView.setText(location.getLongitude()+"");

                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                SharedPreferences spLatitude = getContext().getSharedPreferences("Latitude", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spLatitude.edit();
                                editor.putString("getLatitude", String.valueOf(location.getLatitude()));
                                editor.apply();
                                SharedPreferences spLongitude = getContext().getSharedPreferences("Longitude", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = spLongitude.edit();
                                editor1.putString("getLongitude", String.valueOf(location.getLongitude()));
                                editor1.apply();


//                                Toast.makeText(getContext(), "latitude :" + latitude, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "longitude :" + longitude, Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getContext(), StartBottomActivity.class);
                                startActivity(i);

                            }
                        }
                );
            } else {
                Toast.makeText(getContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            double mLatitude = mLastLocation.getLatitude();
            double mLongitude = mLastLocation.getLongitude();
//            Toast.makeText(getContext(), "mLatitude" + mLatitude, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "mLongitude" + mLongitude, Toast.LENGTH_SHORT).show();

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }


}
