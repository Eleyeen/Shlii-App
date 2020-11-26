
package com.example.shliapp.dataModels.locationModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LocationNearStoreResponse {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("Shopping List")
    private List<ShoppingList> mShoppingList;
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("Stores")
    private List<Store> mStores;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<ShoppingList> getShoppingList() {
        return mShoppingList;
    }

    public void setShoppingList(List<ShoppingList> shoppingList) {
        mShoppingList = shoppingList;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

    public List<Store> getStores() {
        return mStores;
    }

    public void setStores(List<Store> stores) {
        mStores = stores;
    }

}
