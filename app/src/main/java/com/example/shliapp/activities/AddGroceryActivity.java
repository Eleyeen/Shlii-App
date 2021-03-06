package com.example.shliapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shliapp.adapter.AutoCompleteIngredientsAdapter;

import com.example.shliapp.dataModels.addGroceries.AddGroceryResponse;
import com.example.shliapp.dataModels.allItems.AllItemsDataModel;
import com.example.shliapp.dataModels.allItems.AllItemsResponse;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.interfaces.GroceryItemID;
import com.example.shliapp.utils.AppRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.network.BaseNetworking.services;

public class AddGroceryActivity extends AppCompatActivity implements View.OnClickListener, GroceryItemID {

    @BindView(R.id.ivBackArrowAddGrocery)
    ImageView ivBackArrow;

    @BindView(R.id.spItemAddGrocery)
    AutoCompleteTextView dynamicSpinner;
//    @BindView(R.id.etQtyAddGrocery)
//    EditText etQtyAddGrocery;

    @BindView(R.id.btnAddGrocery)
    Button btnAddGrocery;
    private boolean valid = false;
    String strQtyAddGrocery = "1";
    private String strItemAddGrocery = "", strUserID, strQuality;
    AutoCompleteIngredientsAdapter adapter;
    ArrayList<AllItemsDataModel> listModels = new ArrayList<>();
    ProgressDialog progressDialog;

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
    private void initUI() {
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        btnAddGrocery.setOnClickListener(this);
        getItem();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackArrowAddGrocery:
                onBackPressed();
                break;

            case R.id.btnAddGrocery:
                progressDialog = new ProgressDialog(AddGroceryActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();
                if (validate()) {
                    apiAddGrocery();

                }else {
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle("No Grocery Selected");
                    dialog.setMessage("Please select a grocery item from the suggestions. ");
                    dialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);


        Call<AllItemsResponse> call = services.getAllItems();

        call.enqueue(new Callback<AllItemsResponse>() {
            @Override
            public void onResponse(Call<AllItemsResponse> call, Response<AllItemsResponse> response) {
                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getData());
                    adapter = new AutoCompleteIngredientsAdapter(AddGroceryActivity.this, listModels, AddGroceryActivity.this);
                    dynamicSpinner.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<AllItemsResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiAddGrocery() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<AddGroceryResponse> addGrocery = services.AddGroceryPost(strItemAddGrocery, AppRepository.mUserID(this), strQtyAddGrocery, AppRepository.mStorageId(this));
        addGrocery.enqueue(new Callback<AddGroceryResponse>() {
            @Override
            public void onResponse(Call<AddGroceryResponse> call, Response<AddGroceryResponse> response) {
                progressDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(AddGroceryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (response.body().getStatus()) {
                    Toast.makeText(AddGroceryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(AddGroceryActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddGroceryResponse> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private boolean validate() {
        valid = true;
//        strItemAddGrocery = dynamicSpinner.getText().toString();
        if (strItemAddGrocery.isEmpty()) {
            valid = false;
        }
        if (strQtyAddGrocery.isEmpty()) {
            Toast.makeText(this, "null strQty", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        4604
        return valid;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void groceryItem(String id) {
        strItemAddGrocery = id;
        Log.d("zma selected item id", id);
    }
}
