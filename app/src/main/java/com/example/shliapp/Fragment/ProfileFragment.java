package com.example.shliapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.shliapp.Activities.ForGotPasswordActivity;
import com.example.shliapp.Activities.GeneralUtills;
import com.example.shliapp.Activities.HomeScreenActivity;
import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    View view;
    @BindView(R.id.tvChange)
    TextView tvChangePass;
    @BindView(R.id.tvpProfileName)
    TextView tvName;

    @BindView(R.id.tvpProfileEmail)
    TextView tvEmail;
    @BindView(R.id.tvProfilePassword)
    TextView tvPassword;
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

    private void initUI() {
        ButterKnife.bind(this, view);
        btnLogout.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);
//        Toast.makeText(getContext(), "" + b, Toast.LENGTH_SHORT).show();
//        tvEmail.setText(strUserEmail);
//        tvName.setText(strUserNameFirst + "" + strUserNameLast);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                LOGOUT();
                break;
            case R.id.tvChange:
                Intent intent = new Intent(getContext(), ForGotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void LOGOUT() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putBoolean("locked", false).apply();

        SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefsd.edit().putBoolean("lockedP", false).apply();

        getActivity().finishAffinity();

        Intent intent = new Intent(getContext(), HomeScreenActivity.class);
        startActivity(intent);

    }
}
