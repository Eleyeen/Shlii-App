
package com.example.shliapp.Models.ShppingListModel.GetShopingList;

import java.util.List;

import com.example.shliapp.Models.ShppingListModel.GetShopingList.Datum;
import com.google.gson.annotations.SerializedName;

public class GetShoppingList {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<Datum> mData;
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

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
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
