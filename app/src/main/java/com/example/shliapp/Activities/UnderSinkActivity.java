package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shliapp.Adapter.UnderSinkAdapterItem;
import com.example.shliapp.Models.DatumUnderSink;
import com.example.shliapp.Models.GetGroceryModel;
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

public class UnderSinkActivity extends AppCompatActivity  implements View.OnClickListener {

    @BindView(R.id.ivBackArrowUnderSink)
    ImageView ivBackArrow;
    @BindView(R.id.ivPlusIconUnderSink)
    ImageView ivPlusIcon;
    @BindView(R.id.tvUnderSink)
    TextView tvTitle;
    @BindView(R.id.rvUnderSink)
    RecyclerView rvUnderSink;
    UnderSinkAdapterItem adapter;
    ArrayList<DatumUnderSink> itemModels;
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_sink);
        initUI();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        ivPlusIcon.setOnClickListener(this);
        rvUnderSink.setLayoutManager(new LinearLayoutManager(this));
        rvUnderSink.setHasFixedSize(true);
        itemModels= new ArrayList<DatumUnderSink>();
        progressDialog  = new ProgressDialog(UnderSinkActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        String strStorage = GeneralUtills.getSharedPreferences(this).getString("storageItem" , "");
        tvTitle.setText(strStorage);
        getItem();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);

        String strUserID= GeneralUtills.getSharedPreferences(UnderSinkActivity.this).getString("userId" , "");
        Call<GetGroceryModel> call = services.getAddGrocery(strUserID);
        call.enqueue(new Callback<GetGroceryModel>() {
            @Override
            public void onResponse(Call<GetGroceryModel> call, Response<GetGroceryModel> response) {
                if (response.isSuccessful()) {

                    itemModels.addAll(response.body().getData());
                    adapter = new UnderSinkAdapterItem(UnderSinkActivity.this,  itemModels);
                    rvUnderSink.setAdapter(adapter);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetGroceryModel> call, Throwable t) {
progressDialog.dismiss();
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBackArrowUnderSink:
                onBackPressed();
                break;
            case R.id.ivPlusIconUnderSink:
                Intent intent = new Intent(UnderSinkActivity.this,AddGroceryActivity.class);
                startActivity(intent);
                break;
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( adapter!= null) {
            adapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (adapter != null) {
            adapter.restoreStates(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(UnderSinkActivity.this,StartBottomActivity.class);
//        startActivity(intent);
    }





}
