
package com.example.shliapp.dataModels.allAds;

import com.google.gson.annotations.SerializedName;


public class AllAdsDataModel {

    @SerializedName("ads_name")
    private String mAdsName;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("category_name")
    private String mCategoryName;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getAdsName() {
        return mAdsName;
    }

    public void setAdsName(String adsName) {
        mAdsName = adsName;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
