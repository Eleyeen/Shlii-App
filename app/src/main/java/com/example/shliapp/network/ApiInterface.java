package com.example.shliapp.network;

import com.example.shliapp.dataModels.ChangePasswordResponse;
import com.example.shliapp.dataModels.DeleteResponse;
import com.example.shliapp.dataModels.ForgotPasswordResponse;
import com.example.shliapp.dataModels.GetStorageResponse;
import com.example.shliapp.dataModels.GetStoresModels.GetStoresResponse;
import com.example.shliapp.dataModels.allAds.AllAdsResponse;
import com.example.shliapp.dataModels.getUserSelctedItem.UserSelctedResponse;
import com.example.shliapp.dataModels.itemModel.ItemResonse;
import com.example.shliapp.dataModels.locationModels.LocationNearStoreResponse;
import com.example.shliapp.dataModels.LoginResponse;
import com.example.shliapp.dataModels.profileModels.GetProfileResponse;
import com.example.shliapp.dataModels.shppingListModel.AddShopingList.AddShoppingListResponse;
import com.example.shliapp.dataModels.storageModelss.AddStorageResponse;
import com.example.shliapp.dataModels.VerifyResponse;
import com.example.shliapp.dataModels.addGroceries.AddGroceryResponse;
import com.example.shliapp.dataModels.allItems.AllItemsResponse;
import com.example.shliapp.dataModels.deleteNewShoppingList.DeleteNewShoppingResponse;
import com.example.shliapp.dataModels.deleteShoppingList.DeleteShoppingListResponse;
import com.example.shliapp.dataModels.deleteStorageModel.DeleteStorageResponse;
import com.example.shliapp.dataModels.getShoppingList.GetShoppingResponse;
import com.example.shliapp.dataModels.groceryModel.GroceryResponse;

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


    @FormUrlEncoded
    @POST("deleteGrocerry")
    Call<DeleteNewShoppingResponse> deleteNewShoppingItem(
            @Field("user_id") String userId,
            @Field("item_id") String itemId);

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


    @GET("getAdds")
    Call<AllAdsResponse> getAllAds();


}
