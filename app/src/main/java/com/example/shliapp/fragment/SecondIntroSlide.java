package com.example.shliapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.example.shliapp.R;

import agency.tango.materialintroscreen.SlideFragment;

public class SecondIntroSlide extends SlideFragment {
    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.second_custom_slide_layout, container, false);

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.colorBackground;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimary;
    }


    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.app_name);
    }
}