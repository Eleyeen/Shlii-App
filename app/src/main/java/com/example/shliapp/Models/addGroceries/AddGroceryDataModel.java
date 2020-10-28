
package com.example.shliapp.Models.addGroceries;

import com.google.gson.annotations.SerializedName;

public class AddGroceryDataModel {

    @SerializedName("id")
    private String mId;
    @SerializedName("item_title")
    private String mItemTitle;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("user_id")
    private String mUserId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
