
package com.example.shliapp.Models.groceryModel;


import com.google.gson.annotations.SerializedName;

public class GroceryDataModel {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("item_title")
    private String mItemTitle;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("storage_id")
    private String mStorageId;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private String mUserId;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getStorageId() {
        return mStorageId;
    }

    public void setStorageId(String storageId) {
        mStorageId = storageId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
