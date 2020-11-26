
package com.example.shliapp.dataModels;

import java.util.List;

import com.example.shliapp.dataModels.storageModelss.DatumStorage;
import com.google.gson.annotations.SerializedName;

public class GetStorageResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<DatumStorage> mData;
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

    public List<DatumStorage> getData() {
        return mData;
    }

    public void setData(List<DatumStorage> data) {
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
