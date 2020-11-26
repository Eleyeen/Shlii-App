package com.example.shliapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.adapter.ChosseStoreAdapter;
import com.example.shliapp.dataModels.locationModels.LocationNearStoreResponse;
import com.example.shliapp.dataModels.locationModels.Store;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.tvNoStores)
    TextView tvNoStores;
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
        progressDialog = new ProgressDialog(ChooseStoreActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();


        getUpdatedLocation();//get location if previous attempt was null
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
        if (location != null) {


            AppRepository.mPutValue(this).putString("lat", String.valueOf(location.getLatitude())).commit();
            AppRepository.mPutValue(this).putString("lng", String.valueOf(location.getLongitude())).commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addLocation() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<LocationNearStoreResponse> addLocation = services.AddLocation(AppRepository.mUserID(this), AppRepository.mLat(this), AppRepository.mLng(this));
        addLocation.enqueue(new Callback<LocationNearStoreResponse>() {
            @Override
            public void onResponse(Call<LocationNearStoreResponse> call, Response<LocationNearStoreResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStores().size() > 0) {
                        listModels.addAll(response.body().getStores());
                        adapter = new ChosseStoreAdapter(ChooseStoreActivity.this, listModels);
                        rvChoseStore.setAdapter(adapter);
                        tvNoStores.setVisibility(View.GONE);
                    } else {
                        tvNoStores.setVisibility(View.VISIBLE);
                    }
//                    alertDialog.dismiss();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<LocationNearStoreResponse> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }


}
