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
import com.example.shliapp.Activities.UnderSinkActivity;
import com.example.shliapp.Adapter.UnderSinkAdapterItem;
import com.example.shliapp.Models.DatumUnderSink;
import com.example.shliapp.Models.GetGroceryModel;
import com.example.shliapp.Models.ProfileModels.Datum;
import com.example.shliapp.Models.ProfileModels.GetProfileModel;
import com.example.shliapp.Network.ApiClienTh;
import com.example.shliapp.Network.ApiInterface;
import com.example.shliapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.Network.BaseNetworking.services;

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
    ArrayList<Datum> itemModels;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        initUI();
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        ButterKnife.bind(this, view);
        btnLogout.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);
        itemModels= new ArrayList<Datum>();

        getProfile();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProfile() {
        services = ApiClienTh.getApiClient().create(ApiInterface.class);

        String strUserID= GeneralUtills.getSharedPreferences(getContext()).getString("userId" , "");


        Call<GetProfileModel> call = services.getProfile(strUserID);

        call.enqueue(new Callback<GetProfileModel>() {
            @Override
            public void onResponse(Call<GetProfileModel> call, Response<GetProfileModel> response) {
                if (response.isSuccessful()) {

                    itemModels.addAll(response.body().getData());
                   String email= response.body().getData().get(0).getEmail();
                   String firstName= response.body().getData().get(0).getFirstName();
                   String lastName= response.body().getData().get(0).getLastName();
                    Toast.makeText(getContext(),email+":"+firstName+""+lastName , Toast.LENGTH_SHORT).show();

                   tvEmail.setText(email);
                   tvName.setText(firstName +""+lastName);


                }

            }

            @Override
            public void onFailure(Call<GetProfileModel> call, Throwable t) {

            }
        });

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
