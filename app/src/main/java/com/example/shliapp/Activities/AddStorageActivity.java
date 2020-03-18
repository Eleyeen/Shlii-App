package com.example.shliapp.Activities;

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

import com.example.shliapp.Models.StorageModelss.AddStorageModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

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

    private boolean valid = false;
    private String strStorageName, strTagLine,strTagLinee;

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
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiAddStorage() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<AddStorageModel> addStorage = services.getAddStoragePost(strStorageName, strTagLinee);
        addStorage.enqueue(new Callback<AddStorageModel>() {
            @Override
            public void onResponse(Call<AddStorageModel> call, Response<AddStorageModel> response) {

                if (response.body() == null) {
                    Toast.makeText(AddStorageActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                } else if (response.body().getStatus()) {
                    Toast.makeText(AddStorageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddStorageActivity.this, StartBottomActivity.class));


                } else {
                    Toast.makeText(AddStorageActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddStorageModel> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                Toast.makeText(AddStorageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private boolean validate() {
        valid = true;

        strStorageName = etStorageName.getText().toString();
        strTagLine = etTagLine.getText().toString();
         strTagLinee= GeneralUtills.getSharedPreferences(AddStorageActivity.this).getString("userId" , "");




        if (strStorageName.isEmpty() && strTagLine.isEmpty()) {
            etStorageName.setError("enter a Storage  ");
            etTagLine.setError("enter a Tagline");
            valid = false;
        } else if (strStorageName.isEmpty()) {
            etStorageName.setError("enter a Storage  ");
            valid = false;
        } else if (strTagLine.isEmpty()) {
            etTagLine.setError("enter a TagLine  ");
            valid = false;
        } else {
            etStorageName.setError(null);
            etTagLine.setError(null);
        }
        return valid;
    }
}
