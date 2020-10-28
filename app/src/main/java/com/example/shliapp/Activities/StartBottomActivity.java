package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.shliapp.Fragment.ProfileFragment;
import com.example.shliapp.Fragment.ShoppingFragment;
import com.example.shliapp.Fragment.StorageFragment;
import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartBottomActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.frame)
    FrameLayout frameLayout;

    @BindView(R.id.ivHome)
    ImageView ivHome;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.ivShopping)
    ImageView ivNotification;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_bottom);
        initUI();

        if (GeneralUtills.getSharedPreferences(this).getBoolean("change_store",false)){
            GeneralUtills.putBooleanValueInEditor(this, "change_store", false);
            ivProfile.setImageResource(R.mipmap.profile);
            ivHome.setImageResource(R.mipmap.home);
            ivNotification.setImageResource(R.mipmap.backgroundunion);

            Fragment fragmentw = new ShoppingFragment();
            setFragment(fragmentw);



        }
    }
    private void initUI() {
        ButterKnife.bind(this);
        ivProfile.setOnClickListener(this);
        ivNotification.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        setFragment(new StorageFragment());
        progressDialog  = new ProgressDialog(StartBottomActivity.this);

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivHome:
                ivProfile.setImageResource(R.mipmap.profile);
                ivNotification.setImageResource(R.mipmap.union);
                ivHome.setImageResource(R.mipmap.backgroundhome);

                Fragment fragment4 = new StorageFragment();
                setFragment(fragment4);
                break;


            case R.id.ivShopping:

                ivProfile.setImageResource(R.mipmap.profile);
                ivHome.setImageResource(R.mipmap.home);
                ivNotification.setImageResource(R.mipmap.backgroundunion);

                Fragment fragmentw = new ShoppingFragment();
                setFragment(fragmentw);

                break;
            case R.id.ivProfile:

                ivProfile.setImageResource(R.mipmap.backgroundprofile);
                ivHome.setImageResource(R.mipmap.home);
                ivNotification.setImageResource(R.mipmap.union);
                Fragment fragment = new ProfileFragment();
                setFragment(fragment);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}

