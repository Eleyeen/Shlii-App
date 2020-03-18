package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

                String userFirstName = etFirstName.getText().toString();
                String userLastName = etLastName.getText().toString();
                String userEmail = etEmail.getText().toString();
                String userPassword = etPassword.getText().toString();
                String userConfirmPassword = etConfirmPassword.getText().toString();
                if (validateLogin( userFirstName, userLastName,userEmail,  userPassword ,userConfirmPassword)){
                    doLogin(userFirstName, userLastName,userEmail,  userPassword ,  userConfirmPassword);
                }

                break;
            case    R.id.tvSkip:
                Intent intent = new Intent(SignUpActivity.this,StartBottomActivity.class);
                startActivity(intent);

                break;
        }
    }
    private boolean validateLogin(String userFirstName,String userLastName,String userEmail, String userPassword , String userConfirmPassword) {

        if (userEmail == null || userEmail.trim().length() == 0) {
            Toast.makeText(this, "User Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userPassword == null || userPassword.trim().length() == 0) {
            Toast.makeText(this, "User Password is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userConfirmPassword == null || userConfirmPassword.trim().length() == 0) {
            Toast.makeText(this, "User  Confirm Password is Required", Toast.LENGTH_SHORT).show();
            return false;
        }if (userFirstName == null || userFirstName.trim().length() == 0) {
            Toast.makeText(this, "User First Name is Required", Toast.LENGTH_SHORT).show();
            return false;
        }if (userLastName == null || userLastName.trim().length() == 0) {
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

                        Intent intent = new Intent(SignUpActivity.this, StartBottomActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Empty", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("response","error "+t.getMessage());

                Toast.makeText(SignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });
    }

}
