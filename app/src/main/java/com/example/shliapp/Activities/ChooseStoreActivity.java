package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shliapp.Adapter.AutoCompleteIngredientsAdapter;
import com.example.shliapp.Adapter.ChosseStoreAdapter;
import com.example.shliapp.Models.GetStoresModels.Datum;
import com.example.shliapp.Models.GetStoresModels.GetStoresModel;
import com.example.shliapp.Models.ItemRespones;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

public class ChooseStoreActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrowChooseStore)
    ImageView ivBackArrow;

    @BindView(R.id.ivSearchChooseStore)
    ImageView ivSearchIcon;
    @BindView(R.id.rvChoseStore)
    RecyclerView rvChoseStore;
    ArrayList<Datum> listModels =new ArrayList<>();
    ChosseStoreAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store);
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        ButterKnife.bind(this);

        rvChoseStore.setLayoutManager(new LinearLayoutManager(ChooseStoreActivity.this));
        rvChoseStore.setHasFixedSize(true);

        getStores();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStores() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<GetStoresModel> call = services.getStores();

        call.enqueue(new Callback<GetStoresModel>() {
            @Override
            public void onResponse(Call<GetStoresModel> call, Response<GetStoresModel> response) {
                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getData());
                    adapter = new ChosseStoreAdapter(ChooseStoreActivity.this, listModels);
                    rvChoseStore.setAdapter(adapter);
//                    alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetStoresModel> call, Throwable t) {

            }
        });

    }

}
