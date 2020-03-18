package com.example.shliapp.Network;

import com.example.shliapp.Models.AddGrocery;
import com.example.shliapp.Models.ChangePasswordModel;
import com.example.shliapp.Models.DeleteModel;
import com.example.shliapp.Models.ForgotPasswordModel;
import com.example.shliapp.Models.GetGroceryModel;
import com.example.shliapp.Models.GetStorageModel;
import com.example.shliapp.Models.ItemRespones;
import com.example.shliapp.Models.LoginResponse;
import com.example.shliapp.Models.StorageModelss.AddStorageModel;
import com.example.shliapp.Models.VerifyResponseModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<AddStorageModel> getAddStoragePost(
            @Field("storage_name") String strStorageName,
            @Field("user_id") String userID);

    @FormUrlEncoded
    @POST("AddGrocery")
    Call<AddGrocery> getAddGroceryPost(
            @Field("item_id") String item_id,
            @Field("user_id") String user_id,
            @Field("quantity") String quantity);



    @GET("ItemsList")
    Call<ItemRespones> getItem();

    @DELETE("deleteGrocerry/{id}")
    Call<DeleteModel> deleteItem(
            @Path("id") String groupId
    );
    @GET("getStorages/{id}")
    Call<GetStorageModel> getStorage(@Path("id") String groupId);

    @GET("getGrocerry/{id}")
    Call<GetGroceryModel> getAddGrocery(@Path("id") String groupId);


}
