package com.example.shliapp.Models.ShppingListModel.GetShopingList;


public class ContentItem extends ListItem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String quantity;


    public String getQuantity() {
        return quantity;
    }

    public void setQuatity(String rollnumber) {
        this.quantity = rollnumber;
    }
}