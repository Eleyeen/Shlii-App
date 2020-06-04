package com.example.shliapp.Activities;

import androidx.annotation.FloatRange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shliapp.Fragment.FirstIntroSlide;
import com.example.shliapp.Fragment.SecondIntroSlide;
import com.example.shliapp.Fragment.ThirdIntroSlide;

import agency.tango.materialintroscreen.MaterialIntroActivity;
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
        addSlide(new FirstIntroSlide());

        addSlide(new SecondIntroSlide());
        addSlide(new ThirdIntroSlide());
    }
    @Override
    public void onFinish() {
        super.onFinish();
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();
    }
}
