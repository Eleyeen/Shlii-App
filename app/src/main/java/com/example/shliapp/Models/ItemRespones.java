
package com.example.shliapp.Models;

import java.util.List;

import com.example.shliapp.Models.GetStoresModels.GetStoreDataModel;
import com.google.gson.annotations.SerializedName;

public class ItemRespones {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<GetStoreDataModel> mData;
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

    public List<GetStoreDataModel> getData() {
        return mData;
    }

    public void setData(List<GetStoreDataModel> data) {
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
