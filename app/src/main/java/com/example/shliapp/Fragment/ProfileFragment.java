package com.example.shliapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shliapp.Activities.HomeScreenActivity;
import com.example.shliapp.Activities.LoginActivity;
import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    View view;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_profile, container, false);


         initUI();
        return view;

    }
    private void initUI(){
        ButterKnife.bind(this,view);
    btnLogout.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogout:
                LOGOUT();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void LOGOUT(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putBoolean("locked", false).apply();

        SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefsd.edit().putBoolean("lockedP", false).apply();

        getActivity().finishAffinity();

        Intent intent= new Intent(getContext(), HomeScreenActivity.class);
        startActivity(intent);

    }
}
