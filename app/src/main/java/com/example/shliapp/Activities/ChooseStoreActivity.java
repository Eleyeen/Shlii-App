package com.example.shliapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shliapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseStoreActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBackArrowChooseStore)
    ImageView ivBackArrow;

    @BindView(R.id.ivSearchChooseStore)
    ImageView ivSearchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store);
        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
        }
    }

}
