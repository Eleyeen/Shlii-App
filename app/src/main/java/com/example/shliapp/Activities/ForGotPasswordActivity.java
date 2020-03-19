package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shliapp.Models.ForgotPasswordModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForGotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.etForgot_Email)
    EditText etEmail;

    @BindView(R.id.btnSendCode)
    Button btnSendCode;

    private boolean valid = false;
    private String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        btnSendCode.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSendCode:
                if (validate()) {
                    apiCallForgotPassword();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCallForgotPassword() {
        ApiInterface services = ApiClienTh.getApiClient().create(ApiInterface.class);
        Call<ForgotPasswordModel> userLogin = services.resetPassword(strEmail);
        userLogin.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {

                if (response.body() == null) {
                    Toast.makeText(ForGotPasswordActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }else if (response.body().getStatus()) {
                    Toast.makeText(ForGotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(ForGotPasswordActivity.this,VerifyActivity.class);
                    intent.putExtra("forgotEmail",strEmail);
                    startActivity(intent);

                } else {
                    Toast.makeText(ForGotPasswordActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                Log.d("response","error "+t.getMessage());
                Toast.makeText(ForGotPasswordActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private boolean validate() {
        valid = true;

        strEmail = etEmail.getText().toString();


        GeneralUtills.putStringValueInEditor(ForGotPasswordActivity.this,"forgot_email",strEmail);

        if (strEmail.isEmpty()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }
        return valid;
    }
}