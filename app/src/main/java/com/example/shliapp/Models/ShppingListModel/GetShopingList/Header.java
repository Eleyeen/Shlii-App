package com.example.shliapp.Models.ShppingListModel.GetShopingList;


public class Header extends ListItem {
    private String header;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
}