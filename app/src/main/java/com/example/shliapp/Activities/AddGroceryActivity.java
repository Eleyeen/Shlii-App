package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shliapp.Adapter.AutoCompleteIngredientsAdapter;
import com.example.shliapp.Models.AddGrocery;
import com.example.shliapp.Models.Datum;
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

public class AddGroceryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrowAddGrocery)
    ImageView ivBackArrow;

    @BindView(R.id.spItemAddGrocery)
    AutoCompleteTextView dynamicSpinner;
    @BindView(R.id.etQtyAddGrocery)
    EditText etQtyAddGrocery;

    @BindView(R.id.btnAddGrocery)
    Button btnAddGrocery;
    private boolean valid = false;
    private String strItemAddGrocery, strQtyAddGrocery ,strUserID,strQuality;
    AutoCompleteIngredientsAdapter adapter;
    ArrayList<Datum> listModels =new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            initUI();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI(){
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        btnAddGrocery.setOnClickListener(this);
        getItem();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBackArrowAddGrocery:
                onBackPressed();
                break;

            case R.id.btnAddGrocery:

                if (validate()) {
                    apiAddGrocery();
                }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<ItemRespones> call = services.getItem();

        call.enqueue(new Callback<ItemRespones>() {
            @Override
            public void onResponse(Call<ItemRespones> call, Response<ItemRespones> response) {
                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getData());
                    adapter = new AutoCompleteIngredientsAdapter(AddGroceryActivity.this, listModels);
                    dynamicSpinner.setAdapter(adapter);
//                    alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ItemRespones> call, Throwable t) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiAddGrocery() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<AddGrocery> addGrocery = services.AddGroceryPost(strItemAddGrocery,strUserID, strQtyAddGrocery);
        addGrocery.enqueue(new Callback<AddGrocery>() {
            @Override
            public void onResponse(Call<AddGrocery> call, Response<AddGrocery> response) {

                if (response.body() == null) {
                    Toast.makeText(AddGroceryActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                } else if (response.body().getStatus()) {
                    Toast.makeText(AddGroceryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddGroceryActivity.this, UnderSinkActivity.class));


                } else {
                    Toast.makeText(AddGroceryActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddGrocery> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                Toast.makeText(AddGroceryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validate() {
        valid = true;
        SharedPreferences sharedPreferences = getSharedPreferences("addGroceryItem", Context.MODE_PRIVATE);
        String ids = sharedPreferences.getString("itemID" ,"");
        Toast.makeText(this, "text :::"+ids, Toast.LENGTH_LONG).show();

        strItemAddGrocery = ids;
        strQtyAddGrocery = etQtyAddGrocery.getText().toString();
        strUserID= GeneralUtills.getSharedPreferences(AddGroceryActivity.this).getString("userId" , "");





        if (strItemAddGrocery.isEmpty() && strQtyAddGrocery.isEmpty()) {
//            spItemAddGrocery.setError("enter a Storage  ");
            etQtyAddGrocery.setError("enter a Tagline");
            valid = false;
        } else if (strItemAddGrocery.isEmpty()) {
//            spItemAddGrocery.setError("enter a Storage  ");
            valid = false;
        } else if (strQtyAddGrocery.isEmpty()) {
            etQtyAddGrocery.setError("enter a TagLine  ");
            valid = false;
        } else {
//            spItemAddGrocery.setError(null);
            etQtyAddGrocery.setError(null);
        }
        return valid;
    }

}
