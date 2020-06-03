package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shliapp.Models.LoginResponse;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener {

    @BindView(R.id.ivBackArrowSignUp)
    ImageView ivBackArrow;
    @BindView(R.id.etEmailSignUp)
    EditText etEmail;
    @BindView(R.id.etPasswordSignUp)
    EditText etPassword;
    @BindView(R.id.etFirstNameSignUp)
    EditText etFirstName;
    @BindView(R.id.etLastNameSignUp)
    EditText etLastName;

    @BindView(R.id.tvSkip)
    TextView  tvSkip;

    @BindView(R.id.etConfirmPasswordLogin)
    EditText etConfirmPassword;

    @BindView(R.id.btnContinueSignUp)
    Button btnContinue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        ivBackArrow.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        progressDialog  = new ProgressDialog(SignUpActivity.this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.ivBackArrowSignUp:
onBackPressed();
                break;
            case R.id.btnContinueSignUp:
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();

                String userFirstName = etFirstName.getText().toString();
                String userLastName = etLastName.getText().toString();
                String userEmail = etEmail.getText().toString();
                String userPassword = etPassword.getText().toString();
                String userConfirmPassword = etConfirmPassword.getText().toString();
//                SharedPreferences email = this.getSharedPreferences("email", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editoremail = email.edit();
//                editoremail.putString("email", userEmail);
//                editoremail.apply();
//                SharedPreferences userName = this.getSharedPreferences("name", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editorName =userName.edit();
//                editorName.putString("name", userFirstName+""+userLastName);
//                editorName.apply();
//                SharedPreferences pass = this.getSharedPreferences("password", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editorPass = pass.edit();
//                editorPass.putString("Password", userEmail);
//                editorPass.apply();
                if (validateLogin( userFirstName, userLastName,userEmail,  userPassword ,userConfirmPassword)){
                    doLogin(userFirstName, userLastName,userEmail,  userPassword ,  userConfirmPassword);
                }

                break;
            case    R.id.tvSkip:
                Intent intent = new Intent(SignUpActivity.this,StartBottomActivity.class);
                startActivity(intent);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();

                break;
        }
    }
    private boolean validateLogin(String userFirstName,String userLastName,String userEmail, String userPassword , String userConfirmPassword) {
        boolean valid = true;


        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etEmail.setError("enter a valid email address");
            progressDialog.dismiss();
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (userPassword.isEmpty() || userPassword.length() < 4 || userPassword.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            progressDialog.dismiss();

            valid = false;
        } else {
            etPassword.setError(null);
        }

        if (userConfirmPassword.isEmpty() || userConfirmPassword.length() < 4 || userConfirmPassword.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            progressDialog.dismiss();

            valid = false;
        } else {
            etPassword.setError(null);
        }



        if (userFirstName == null || userFirstName.trim().length() == 0) {
            Toast.makeText(this, "User First Name is Required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return false;
        }
        if (userLastName == null || userLastName.trim().length() == 0) {
            progressDialog.dismiss();
            Toast.makeText(this, "User Last Name is Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doLogin(String userFirstName,String userLastName,String userEmail, String userPassword , String userConfirmPassword) {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        final Call<LoginResponse> registration = services.createUser(userFirstName, userLastName,userEmail,  userPassword ,  userConfirmPassword);
        registration.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getMessage().equals("Acount Created Successfully")) {

                        SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                        Boolean statusLockedP= prefsd.edit().putBoolean("lockedP", true).commit();
                        prefsd.edit().putBoolean("lockedP", true).apply();

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("response","error "+t.getMessage());

                Toast.makeText(SignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


        });
    }

}
