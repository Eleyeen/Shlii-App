
package com.example.shliapp.dataModels.groceryModel;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroceryResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<GroceryDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public List<GroceryDataModel> getData() {
        return mData;
    }

    public void setData(List<GroceryDataModel> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
