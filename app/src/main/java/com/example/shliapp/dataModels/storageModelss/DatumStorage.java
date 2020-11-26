package com.example.shliapp.dataModels.storageModelss;

import com.google.gson.annotations.SerializedName;

public class DatumStorage {

        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("id")
        private Long mId;
        @SerializedName("storage_name")
        private String mStorageName;
        @SerializedName("tagLine")
        private String mTagLine;
        @SerializedName("updated_at")
        private String mUpdatedAt;
        @SerializedName("user_id")
        private String mUserId;

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getStorageName() {
            return mStorageName;
        }

        public void setStorageName(String storageName) {
            mStorageName = storageName;
        }

        public String getTagLine() {
            return mTagLine;
        }

        public void setTagLine(String tagLine) {
            mTagLine = tagLine;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            mUserId = userId;
        }

    }


