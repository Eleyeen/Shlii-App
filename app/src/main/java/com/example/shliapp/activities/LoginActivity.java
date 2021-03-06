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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shliapp.dataModels.LoginResponse;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrow)
    ImageView ivBackArrow;
    @BindView(R.id.tvForGotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.etEmailLogin)
    EditText etEmail;
    @BindView(R.id.etPasswordLogin)
    EditText etPassword;
    @BindView(R.id.btnContinueLogin)
    Button btnContinue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        progressDialog  = new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle("Loading...");
//        progressDialog.setMessage("Wait");
//        progressDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvForGotPassword:
                Intent intent12 = new Intent(LoginActivity.this, ForGotPasswordActivity.class);
                startActivity(intent12);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();


                break;
            case R.id.ivBackArrow:
                onBackPressed();
                break;
            case R.id.btnContinueLogin:
                ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();

                String userEmail = etEmail.getText().toString();
                String userPassword = etPassword.getText().toString();

                if (validateLogin()) {
                    doLogin(userEmail, userPassword);
                }

                break;
        }
    }


    private boolean validateLogin() {

        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            progressDialog.dismiss();
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            progressDialog.dismiss();

            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doLogin(String userEmail, String userPassword) {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        final Call<LoginResponse> registration = services.userLogin(userEmail, userPassword);
        registration.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (response.body().getStatus()) {
                        AppRepository.mPutValue(LoginActivity.this).putBoolean("isFirstOpen", true).commit();
                        AppRepository.mPutValue(LoginActivity.this).putBoolean("loggedIn", true).commit();
                        AppRepository.mPutValue(LoginActivity.this).putString("userID",String.valueOf( response.body().getData().getId())).commit();
                        GeneralUtills.putStringValueInEditor(LoginActivity.this, "userId", response.body().getData().getId().toString());

                        Intent intent = new Intent(LoginActivity.this, StartBottomActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {
//                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            Toast.makeText(LoginActivity.this, "Invalid Username Or Password!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("response", "error " + t.getMessage());

                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


        });
    }

}
