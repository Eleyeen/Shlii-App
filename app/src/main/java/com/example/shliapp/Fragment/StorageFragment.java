package com.example.shliapp.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shliapp.Activities.AddStorageActivity;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Adapter.StorageAdapter;
import com.example.shliapp.Models.GetStorageModel;
import com.example.shliapp.Models.StorageModelss.DatumStorage;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;
import com.example.shliapp.utils.GPSTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class StorageFragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    View view;
    @BindView(R.id.ivPlusIconStorage)
    ImageView ivPlusIcon;
    @BindView(R.id.rvStorage)
    RecyclerView rvShoppingList;

    private ArrayList<DatumStorage> itemLists;
    ProgressDialog progressDialog;

    private FusedLocationProviderClient mFusedLocationClient;
    private int PERMISSION_ID = 44;

    private String lat, lng;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_storage, container, false);
        initListeners();
        initUI();
        return view;
    }


    private void initListeners() {
        ButterKnife.bind(this, view);
        getUpdatedLocation();
        Log.d("zma user id", AppRepository.mUserID(getActivity()));
    }

    private void getUpdatedLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_FINE);
        locationCritera.setAltitudeRequired(false);
        locationCritera.setBearingRequired(false);
        locationCritera.setCostAllowed(true);
        locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String providerName = locationManager.getBestProvider(locationCritera, true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        GPSTracker gpsTracker = new GPSTracker(getActivity());
//        Log.d("zma Latitude", "" + gpsTracker.getLatitude());
//        Log.d("zma Longitude", "" + gpsTracker.getLongitude());
        Location location = locationManager.getLastKnownLocation(providerName);
        if (location != null) {
            if (AppRepository.mLat(getActivity()).length() < 1) {
                AppRepository.mPutValue(getActivity()).putString("lat", String.valueOf(location.getLatitude())).commit();
                AppRepository.mPutValue(getActivity()).putString("lng", String.valueOf(location.getLongitude())).commit();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        progressDialog = new ProgressDialog(getContext());
        ivPlusIcon.setOnClickListener(this);
        rvShoppingList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvShoppingList.setHasFixedSize(true);
        itemLists = new ArrayList<DatumStorage>();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();
        checkPermission();
        getStorage();
    }

    private void checkPermission() {


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        1001);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStorage() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<GetStorageModel> call = services.getStorage(AppRepository.mUserID(getActivity()));

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
        switch (v.getId()) {
            case R.id.ivPlusIconStorage:
                Intent intent = new Intent(getContext(), AddStorageActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    GPSTracker gpsTracker = new GPSTracker(getActivity());
                    lat = String.valueOf(gpsTracker.getLatitude());
                    lng = String.valueOf(gpsTracker.getLongitude());
                    AppRepository.mPutValue(getActivity()).putString("lat", lat).commit();
                    AppRepository.mPutValue(getActivity()).putString("lng", lng).commit();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
