package com.example.shliapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.shliapp.Activities.HomeScreenActivity;
import com.example.shliapp.Activities.LoginActivity;
import com.example.shliapp.Activities.StartBottomActivity;
import com.example.shliapp.R;
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


                SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Boolean yourLock = prefsd.getBoolean("lockedP", false);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Boolean yourLocked = prefs.getBoolean("locked", false);
                if(yourLocked){
                    Intent intent = new Intent(getActivity(), StartBottomActivity
                            .class);
                    startActivity(intent);

                }else if(yourLock){
                    Intent intent = new Intent(getActivity(), StartBottomActivity
                            .class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                    startActivity(intent);

                }

            }

        }.start();

        return parentView;
    }
}
