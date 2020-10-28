
package com.example.shliapp.Models.getUserSelctedItem;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class UserSectedDataModel {

    @SerializedName("items")
    private List<SelectedItem> mItems;
    @SerializedName("row_number")
    private String mRowNumber;

    public List<SelectedItem> getItems() {
        return mItems;
    }

    public void setItems(List<SelectedItem> items) {
        mItems = items;
    }

    public String getRowNumber() {
        return mRowNumber;
    }

    public void setRowNumber(String rowNumber) {
        mRowNumber = rowNumber;
    }

}
