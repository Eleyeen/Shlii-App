package com.example.shliapp.Network;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClienTh {
    ApiClienTh context = this;
    public static final String BASE_URL = "http://www.dahawwalur.org/staging/ShliApp/public/api/";
    static Retrofit retrofit = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Retrofit getApiClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor((Interceptor) chain -> {

            okhttp3.Request original = chain.request();

            // Customize the request

            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "form-data")
                    .method(original.method(), original.body())
                    .build();

            Response response = chain.proceed(request);

            // Customize or return the response
            return response;
        });

        OkHttpClient OkHttpClient = httpClient.build();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient)
                    .build();
        }
        return retrofit;
    }
}

