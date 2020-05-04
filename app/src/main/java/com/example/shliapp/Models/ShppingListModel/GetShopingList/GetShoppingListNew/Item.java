
package com.example.shliapp.Models.ShppingListModel.GetShopingList.GetShoppingListNew;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Item {

    @SerializedName("id")
    private String mId;
    @SerializedName("item_number")
    private String mItemNumber;
    @SerializedName("item_title")
    private String mItemTitle;
    @SerializedName("row_name")
    private String mRowName;
    @SerializedName("row_number")
    private String mRowNumber;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getItemNumber() {
        return mItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        mItemNumber = itemNumber;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }

    public String getRowName() {
        return mRowName;
    }

    public void setRowName(String rowName) {
        mRowName = rowName;
    }

    public String getRowNumber() {
        return mRowNumber;
    }

    public void setRowNumber(String rowNumber) {
        mRowNumber = rowNumber;
    }

}
