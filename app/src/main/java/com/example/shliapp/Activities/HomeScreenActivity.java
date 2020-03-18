package com.example.shliapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.cvSignIn)
    CardView cvSignIn;

    @BindView(R.id.cvSignUp)
    CardView cvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        cvSignIn.setOnClickListener(this);
        cvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cvSignIn:
                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.cvSignUp:
                Intent intent1 = new Intent(HomeScreenActivity.this, SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
