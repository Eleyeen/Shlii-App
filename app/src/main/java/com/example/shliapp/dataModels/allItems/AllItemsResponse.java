
package com.example.shliapp.dataModels.allItems;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllItemsResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<AllItemsDataModel> mData;
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

    public List<AllItemsDataModel> getData() {
        return mData;
    }

    public void setData(List<AllItemsDataModel> data) {
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
