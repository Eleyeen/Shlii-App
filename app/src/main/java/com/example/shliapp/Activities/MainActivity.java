package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.shliapp.Fragment.SplashFragment;
import com.example.shliapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();

    }
    @Override
    protected void onPause () {
        super.onPause();

        SplashFragment.splashBoolean = false;

    }

    @Override
    protected void onResume () {
        super.onResume();
        SplashFragment.splashBoolean = true;

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed () {
        finishAffinity();
    }

}
