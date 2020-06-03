package com.example.shliapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.shliapp.Activities.FirstIntroActivity;
import com.example.shliapp.Activities.HomeScreenActivity;
import com.example.shliapp.Activities.StartBottomActivity;
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
        pbSplashScreen = parentView.findViewById(R.id.pb_splash);


        new CountDownTimer(500, 100) {

            public void onTick(long millisUntilFinished) {


                i = i + 1;
                pbSplashScreen.setProgress(i);

            }

            public void onFinish() {

                if (AppRepository.isLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), StartBottomActivity.class);
                    startActivity(intent);

                } else if (!AppRepository.isFirstOpen(getActivity())) {
                    startActivity(new Intent(getActivity(), FirstIntroActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                    startActivity(intent);

                }

            }

        }.start();

        return parentView;
    }
}
