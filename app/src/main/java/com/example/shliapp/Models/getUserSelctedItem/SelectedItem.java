
package com.example.shliapp.Models.getUserSelctedItem;


import com.google.gson.annotations.SerializedName;

public class SelectedItem {

    @SerializedName("item_title")
    private String mItemTitle;
    @SerializedName("quantity")
    private String mQuantity;

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

}
