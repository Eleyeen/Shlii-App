package com.example.shliapp.utils;
import android.content.Context;
import android.content.SharedPreferences;
public class AppRepository {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String mConfig = "com.techease.ultimateSavings";
    public static SharedPreferences mGetValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    public static SharedPreferences.Editor mPutValue(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return editor = sharedPreferences.edit();
    }
    public static String mAPIToken(Context context) {
        return mGetValue(context).getString("auth_token", "");
    }
    public static String mLat(Context context) {
        return mGetValue(context).getString("lat", "");
    }
    public static String mLng(Context context) {
        return mGetValue(context).getString("lng", "");
    }
    public static String mUserID(Context context) {
        return mGetValue(context).getString("userID", "");
    }
    public static String mDeviceToken(Context context) {
        return mGetValue(context).getString("device_token", "");
    }
    public static String mStorageId(Context context){
        return mGetValue(context).getString("storageId", "");
    }
    public static String mServiceTimePeriod(Context context) {
        return mGetValue(context).getString("serviceTime", "");
    }
    public static String mServiceLocation(Context context) {
        return mGetValue(context).getString("serviceLocation", "");
    }
    public static String mCreditCardNumber(Context context) {
        return mGetValue(context).getString("cardNumber", "");
    }
    public static String mChangedAddress(Context context) {
        return mGetValue(context).getString("changedHomeAddress", "");
    }
    public static String mStartDate(Context context) {
        return mGetValue(context).getString("startDate", "");
    }
    public static String mEndDate(Context context) {
        return mGetValue(context).getString("endDate", "");
    }
    public static String mAvailableTime(Context context) {
        return mGetValue(context).getString("availableTime", "");
    }
    public static String mSelectedServices(Context context) {
        return String.valueOf(mGetValue(context).getStringSet("selectedServicesArray", null));
    }
    public static int mClickedServiceID(Context context) {
        return mGetValue(context).getInt("clickedServiceID", 0);
    }
    public static boolean isLoggedIn(Context context) {
        return mGetValue(context).getBoolean("loggedIn", false);
    }
    public static boolean isProUser(Context context) {
        return mGetValue(context).getBoolean("isPro", false);
    }
    public static boolean isFirstOpen(Context context) {
        return mGetValue(context).getBoolean("isFirstOpen", false);
    }
}