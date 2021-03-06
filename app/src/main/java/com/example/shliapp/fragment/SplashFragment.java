package com.example.shliapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.shliapp.activities.FirstIntroActivity;
import com.example.shliapp.activities.HomeScreenActivity;
import com.example.shliapp.activities.StartBottomActivity;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

public class SplashFragment extends Fragment {

    View parentView;

    public static boolean splashBoolean = true;
    private ProgressBar pbSplashScreen;
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(() -> {
            if (AppRepository.isLoggedIn(getActivity())) {
                Intent intent = new Intent(getActivity(), StartBottomActivity.class);
                startActivity(intent);

            } else if (!AppRepository.isFirstOpen(getActivity())) {
                startActivity(new Intent(getActivity(), FirstIntroActivity.class));
            } else {
                Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                startActivity(intent);

            }
            getActivity().finish();

        }, 1000);


        return parentView;
    }
}
