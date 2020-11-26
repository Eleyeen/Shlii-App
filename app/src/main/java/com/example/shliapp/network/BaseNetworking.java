package com.example.shliapp.network;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class BaseNetworking {
    public static ApiInterface services;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ApiInterface ApiInterface() {

        services = ApiClienTh.getApiClient().create(ApiInterface.class);
        return services;
    }
   /* @RequiresApi(api = Build.VERSION_CODES.N)
    public static ApiInterface ApiInterface(String token) {
        services = ApiClienTh.getApiClient(token).create(ApiInterface.class);
        return services;
    }*/

}
