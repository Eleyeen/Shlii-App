package com.example.shliapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.shliapp.R;

public class GeneralUtills {
    public static final String MY_PREF = "clothcare";


    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static SharedPreferences.Editor putStringValueInEditor(Context context, String key, String value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
        return editor;
    }


    public static Fragment connectFragment(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("true").commit();
        return fragment;
    }

    public static SharedPreferences.Editor putIntegerValueInEditor(Context context, String key, int value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putInt(key, value).commit();
        return editor;
    }

    public static SharedPreferences.Editor putBooleanValueInEditor(Context context, String key, boolean value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value).commit();
        return editor;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MY_PREF, 0);
    }

    public static SharedPreferences.Editor putValueInEditor(Context context) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        return editor;
    }

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            //    Log.d(TAG, "no internet connection");
            return false;
        } else {
            if (info.isConnected()) {
                //      Log.d(TAG, " internet connection available...");
                return true;
            } else {
                //    Log.d(TAG, " internet connection");
                return true;
            }
        }
    }
}