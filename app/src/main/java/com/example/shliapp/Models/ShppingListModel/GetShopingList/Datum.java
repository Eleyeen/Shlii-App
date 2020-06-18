
package com.example.shliapp.Models.ShppingListModel.GetShopingList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("id")
    private Integer mId;
    @SerializedName("item_number")
    private String mItemNumber;
    @SerializedName("item_title")
    private String mItemTitle;
    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("row_name")
    private String mRowName;
    @SerializedName("row_number")
    private String mRowNumber;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
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

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
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
