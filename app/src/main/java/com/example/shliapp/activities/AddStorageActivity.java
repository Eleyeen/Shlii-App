package com.example.shliapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shliapp.dataModels.storageModelss.AddStorageResponse;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStorageActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrowAddStorage)
    ImageView ivBackArrow;

    @BindView(R.id.etTagLine)
    EditText etTagLine;
    @BindView(R.id.etStorageName)
    EditText etStorageName;
    @BindView(R.id.btnAddStorage)
    Button btnAddStorage;
    ProgressDialog progressDialog;
    private boolean valid = false;
    private String strStorageName, strTagLinee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storage);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        btnAddStorage.setOnClickListener(this);
        ivBackArrow.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackArrowAddStorage:
                onBackPressed();
                break;

            case R.id.btnAddStorage:
                if (validate()) {
                    apiAddStorage();
                    progressDialog = new ProgressDialog(AddStorageActivity.this);
                    progressDialog.setTitle("Loading...");
                    progressDialog.setMessage("Wait");
                    progressDialog.show();

                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiAddStorage() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<AddStorageResponse> addStorage = services.AddStoragePost(strStorageName,
                AppRepository.mUserID(this));
        addStorage.enqueue(new Callback<AddStorageResponse>() {
            @Override
            public void onResponse(Call<AddStorageResponse> call, Response<AddStorageResponse> response) {

                if (response.body() == null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(AddStorageActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                } else if (response.body().getStatus()) {
                    Toast.makeText(AddStorageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddStorageActivity.this, StartBottomActivity.class));
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(AddStorageActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddStorageResponse> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    private boolean validate() {
        valid = true;

        strStorageName = etStorageName.getText().toString();
//        strTagLinee = GeneralUtills.getSharedPreferences(AddStorageActivity.this).getString("userId", "");


        if (strStorageName.isEmpty()) {
            etStorageName.setError("enter a Storage  ");
//            etTagLine.setError("enter a Tagline");
            valid = false;
        } else {
            etStorageName.setError(null);
            etTagLine.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(AddStorageActivity.this,StartBottomActivity.class);
//        startActivity(intent);
    }
}
