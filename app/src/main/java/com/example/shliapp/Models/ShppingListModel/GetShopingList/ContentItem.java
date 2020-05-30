package com.example.shliapp.Models.ShppingListModel.GetShopingList;


public class ContentItem extends ListItem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String rollnumber;


    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }
}