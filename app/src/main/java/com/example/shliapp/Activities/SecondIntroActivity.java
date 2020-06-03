package com.example.shliapp.Activities;

import androidx.annotation.FloatRange;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shliapp.Fragment.CustomSlide;
import com.example.shliapp.Fragment.SecondCustomSlide;
import com.example.shliapp.Fragment.ThirdCustomSlide;
import com.example.shliapp.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class SecondIntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second_intro);
        enableLastSlideAlphaExitTransition(true);
        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });
        addSlide(new CustomSlide());

        addSlide(new SecondCustomSlide());
        addSlide(new ThirdCustomSlide());
    }
    @Override
    public void onFinish() {
        super.onFinish();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
