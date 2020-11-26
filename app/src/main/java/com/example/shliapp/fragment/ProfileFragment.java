package com.example.shliapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.shliapp.activities.ForGotPasswordActivity;
import com.example.shliapp.activities.GeneralUtills;
import com.example.shliapp.activities.HomeScreenActivity;
import com.example.shliapp.dataModels.profileModels.Datum;
import com.example.shliapp.dataModels.profileModels.GetProfileResponse;
import com.example.shliapp.network.ApiClienTh;
import com.example.shliapp.network.ApiInterface;
import com.example.shliapp.R;
import com.example.shliapp.utils.AppRepository;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shliapp.network.BaseNetworking.services;

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
    ProgressDialog progressDialog;


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
        itemModels = new ArrayList<Datum>();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

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

        String strUserID = GeneralUtills.getSharedPreferences(getContext()).getString("userId", "");


        Call<GetProfileResponse> call = services.getProfile(strUserID);

        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful()) {

                    itemModels.addAll(response.body().getData());
                    String email = response.body().getData().get(0).getEmail();
                    String firstName = response.body().getData().get(0).getFirstName();
                    String lastName = response.body().getData().get(0).getLastName();
//                    Toast.makeText(getContext(),email+":"+firstName+""+lastName , Toast.LENGTH_SHORT).show();

                    tvEmail.setText(email);
                    tvName.setText(firstName + "" + lastName);

                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void LOGOUT() {
        AppRepository.mPutValue(getActivity()).putBoolean("loggedIn", false).commit();
        AppRepository.mPutValue(getActivity()).putBoolean("isFirstOpen", false).commit();

        getActivity().finishAffinity();

        Intent intent = new Intent(getContext(), HomeScreenActivity.class);
        startActivity(intent);

    }

}
