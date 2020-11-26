
package com.example.shliapp.dataModels.allItems;

import com.google.gson.annotations.SerializedName;

public class AllItemsDataModel {

    @SerializedName("id")
    private String mId;
    @SerializedName("item_title")
    private String mItemTitle;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }

}
