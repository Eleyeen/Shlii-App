package com.example.shliapp.Network;

import com.example.shliapp.Models.ChangePasswordResponse;
import com.example.shliapp.Models.DeleteResponse;
import com.example.shliapp.Models.ForgotPasswordResponse;
import com.example.shliapp.Models.GetStorageResponse;
import com.example.shliapp.Models.GetStoresModels.GetStoresResponse;
import com.example.shliapp.Models.ItemModel.ItemResonse;
import com.example.shliapp.Models.LocationModels.LocationNearStoreResponse;
import com.example.shliapp.Models.LoginResponse;
import com.example.shliapp.Models.ProfileModels.GetProfileResponse;
import com.example.shliapp.Models.ShppingListModel.AddShopingList.AddShoppingListResponse;
import com.example.shliapp.Models.StorageModelss.AddStorageResponse;
import com.example.shliapp.Models.VerifyResponse;
import com.example.shliapp.Models.addGroceries.AddGroceryResponse;
import com.example.shliapp.Models.allItems.AllItemsResponse;
import com.example.shliapp.Models.deleteShoppingList.DeleteShoppingListResponse;
import com.example.shliapp.Models.deleteStorageModel.DeleteStorageResponse;
import com.example.shliapp.Models.getShoppingList.GetShoppingResponse;
import com.example.shliapp.Models.getUserSelctedItem.UserSelctedResponse;
import com.example.shliapp.Models.groceryModel.GroceryResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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
    Call<ForgotPasswordResponse> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("checkCode")
    Call<VerifyResponse> userVerification(
            @Field("code") String code,
            @Field("email") String email);


    @FormUrlEncoded
    @POST("ChangePassword")
    Call<ChangePasswordResponse> changePassword(
            @Field("email") String email,
            @Field("newPassword") String code);

    @FormUrlEncoded
    @POST("AddStorage")
    Call<AddStorageResponse> AddStoragePost(
            @Field("storage_name") String strStorageName,
            @Field("user_id") String userID);

    @FormUrlEncoded
    @POST("AddGrocery")
    Call<AddGroceryResponse> AddGroceryPost(
            @Field("item_id") String item_id,
            @Field("user_id") String user_id,
            @Field("quantity") String quantity,
            @Field("storage_id") String storageID);


    @FormUrlEncoded
    @POST("makeShoppingList")
    Call<AddShoppingListResponse> addShoppingList(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("location")
    Call<LocationNearStoreResponse> AddLocation(
            @Field("user_id") String user_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);


    @GET("ItemsList")
    Call<ItemResonse> getItem();

    @DELETE("deleteGrocerry/{id}")
    Call<DeleteResponse> deleteItem(
            @Path("id") String groupId);


    @DELETE("deleteShoppingList/{id}")
    Call<DeleteShoppingListResponse> deleteShoppingItem(
            @Path("id") String groupId);

    @DELETE("deleteShoppingList/{id}")
    Call<DeleteShoppingListResponse> deleteShopingList(
            @Path("id") String groupId);

    @GET("getStorages/{id}")
    Call<GetStorageResponse> getStorage(@Path("id") String groupId);

    @GET("getGrocerry?")
    Call<GroceryResponse> getGrocery(@Query("user_id") String groupId,
                                     @Query("storage_id") String storageID);

    @GET("getProfile/{id}")
    Call<GetProfileResponse> getProfile(@Path("id") String groupId);

    @GET("getList")
    Call<GetShoppingResponse> getShoppingList();

    @GET("getStores")
    Call<GetStoresResponse> getStores();


    @FormUrlEncoded
    @POST("userSeletedItems")
    Call<UserSelctedResponse> getUserSelectedItems(
            @Field("user_id") String userID,
            @Field("store_id") String storeID);

    @DELETE("deleteStorage/{id}")
    Call<DeleteStorageResponse> deleteStorage(@Path("id") String id);


    @GET("allItems")
    Call<AllItemsResponse> getAllItems();


}
