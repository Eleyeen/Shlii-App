package com.example.shliapp.Network;

import com.example.shliapp.Models.AddGrocery;
import com.example.shliapp.Models.ChangePasswordModel;
import com.example.shliapp.Models.DeleteModel;
import com.example.shliapp.Models.DeleteShoppingList.DeleteShopList;
import com.example.shliapp.Models.ForgotPasswordModel;
import com.example.shliapp.Models.GetGroceryModel;
import com.example.shliapp.Models.GetStorageModel;
import com.example.shliapp.Models.GetStoresModels.GetStoresModel;
import com.example.shliapp.Models.ItemRespones;
import com.example.shliapp.Models.LocationModels.LocationNearStoreModels;
import com.example.shliapp.Models.LoginResponse;
import com.example.shliapp.Models.ProfileModels.GetProfileModel;
import com.example.shliapp.Models.ShppingListModel.AddShopingList.AddShopingListModel;
import com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingList;
import com.example.shliapp.Models.StorageModelss.AddStorageModel;
import com.example.shliapp.Models.VerifyResponseModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signup")
    Call<LoginResponse> createUser(

            @Field("first_name") String useFirstName,
            @Field("last_name") String userLastName,
            @Field("email") String userEmail,
            @Field("password") String userPassword,
            @Field("retype_password") String userConfirmPassword);


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("reset")
    Call<ForgotPasswordModel> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("checkCode")
    Call<VerifyResponseModel> userVerification(
            @Field("code") String code,
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("ChangePassword")
    Call<ChangePasswordModel> changePassword(
            @Field("email") String email,
            @Field("newPassword") String code);

    @FormUrlEncoded
    @POST("AddStorage")
    Call<AddStorageModel> AddStoragePost(
            @Field("storage_name") String strStorageName,
            @Field("user_id") String userID);

    @FormUrlEncoded
    @POST("AddGrocery")
    Call<AddGrocery> AddGroceryPost(
            @Field("item_id") String item_id,
            @Field("user_id") String user_id,
            @Field("quantity") String quantity);
    @FormUrlEncoded
    @POST("AddShopping")
    Call<AddShopingListModel> AddShopListPost(
            @Field("user_id") String user_id,
            @Field("item_title") String item_title,
            @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("location")
    Call<LocationNearStoreModels> AddLocation(
            @Field("user_id") String user_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);



    @GET("ItemsList")
    Call<ItemRespones> getItem();

    @DELETE("deleteGrocerry/{id}")
    Call<DeleteModel> deleteItem(
            @Path("id") String groupId
    );

    @DELETE("deleteShoppingList/{id}")
    Call<DeleteShopList> deleteShopingList(
            @Path("id") String groupId
    );

    @GET("getStorages/{id}")
    Call<GetStorageModel> getStorage(@Path("id") String groupId);

    @GET("getGrocerry/{id}")
    Call<GetGroceryModel> getAddGrocery(@Path("id") String groupId);

    @GET("getProfile/{id}")
    Call<GetProfileModel> getProfile(@Path("id") String groupId);

    @GET("shopping/{id}")
    Call<GetShoppingList> getShoppingList(@Path("id") String groupId);


    @GET("getStores")
    Call<GetStoresModel> getStores();



}
