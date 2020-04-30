
package com.example.shliapp.Models;

import com.google.gson.annotations.SerializedName;

import okhttp3.Interceptor;

public class DatumUnderSink {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("item_title")
    private String mitemTitle;
    @SerializedName("id")
    private String mId;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private String mUserId;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    private Integer counter;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getMitemTitle() {
        return mitemTitle;
    }

    public void setMitemTitle(String mitemTitle) {
        this.mitemTitle = mitemTitle;
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

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
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
