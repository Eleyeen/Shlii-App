package com.example.shliapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shliapp.R;

public class FirstIntroActivity extends AppCompatActivity {
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_intro);
        firstIntroDialog(this);
    }

    public AlertDialog firstIntroDialog(Context context) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.first_intro_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView tvOK = dialogView.findViewById(R.id.tvOK);
        tvOK.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(this, SecondIntroActivity.class));
            finish();
        });


        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }
}